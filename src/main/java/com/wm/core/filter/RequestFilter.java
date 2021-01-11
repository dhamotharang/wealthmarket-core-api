package com.wm.core.filter;

import com.wm.core.config.CachingHttpRequestWrapper;
import com.wm.core.config.RequestFilterConfig;
import com.wm.core.helper.FilterHelper;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.authmanager.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RequestFilter extends OncePerRequestFilter {

	private static final String AUTHENTICATION_SCHEME = "Bearer ";
	private final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
	private final RequestFilterConfig requestFilterConfig;
	private final AuthenticationService authenticationService;
	private List<Pattern> whiteList = new ArrayList<>();
	static final String ORIGIN = "Origin";

	public RequestFilter(RequestFilterConfig requestSignatureFilterConfig,
						 AuthenticationService authenticationService) {
		this.requestFilterConfig = requestSignatureFilterConfig;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.debug("Running Request Filter");

		//get the request origin to add necessary headers to enable cors
			request.getHeader(ORIGIN);
			String origin = request.getHeader(ORIGIN);
			response.addHeader("Access-Control-Allow-Origin", origin);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

		//check if request is options (preflight) request, and send ok status
		if (request.getMethod().equalsIgnoreCase("OPTIONS") ) {
			response.setStatus(HttpStatus.OK.value());
			return;
		}

		// get the authorization header from the request
		String authorization = request.getHeader("authorization");

		if (StringUtils.isEmpty(authorization)) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing required headers.");
			return;
		}

		CachingHttpRequestWrapper cachingRequestWrapper = new CachingHttpRequestWrapper(request);

		//remove "Bearer " from the authorization header to have only the token left
		authorization = authorization.substring(AUTHENTICATION_SCHEME.length()).trim();

		// validate and decode jwtoken
		BaseResponse authRp = authenticationService.validateToken(authorization);
		if (authRp.getStatusCode() != HttpStatus.OK.value()) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid authorization token");
			return;
		}

//		get the userId string from the decoded token
		String userIdString = (String) authRp.getData();

		//check if the string is empty and return a response
		if (userIdString.isEmpty()) {
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "couldnt process token");
			return;
		}

		long userId = Long.parseLong(userIdString);

//		// set the userId in the request so we can get it from the request
		request.setAttribute("userId", userId);

		// otherwise everything went well, let's proceed with the request
		filterChain.doFilter(cachingRequestWrapper, response);

	}

	@Override
	protected void initFilterBean() throws ServletException {
		logger.info("Instantiating RequestSignature Filter");
		whiteList = FilterHelper.compileWhiteListPattern(requestFilterConfig.getWhitelist());
		logger.debug("Request Filter config: \n" + requestFilterConfig);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// filter disabled
		if (requestFilterConfig.getDisabled()) {
			return true;
		}
		String uri = request.getRequestURI();
		return whiteList.parallelStream().anyMatch(wl -> wl.matcher(uri).matches());
	}
}
