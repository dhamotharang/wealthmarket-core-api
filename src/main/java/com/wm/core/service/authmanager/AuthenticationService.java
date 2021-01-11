/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wm.core.service.authmanager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wm.core.helper.JWTHelper;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author gol
 */
@Service
public class AuthenticationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class.getName());
    @Autowired
    JWTHelper jwtHelper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Generate jwt
     *
     * @param user
     * @return
     */
    public BaseResponse generateToken(User user) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseResponse.setDescription("Error generating token, please try again");
        try {
            String token = jwtHelper.createToken(user);
            baseResponse.setStatusCode(HttpStatus.OK.value());
            baseResponse.setDescription("Successful - New generated token");
            Map object = new HashMap();
            object.put("token", token);
            baseResponse.setData(object);
            logger.info(baseResponse.getDescription()+ ": {}", token);
        } catch (Exception ex) {
            logger.error(baseResponse.getDescription(), ex.getMessage());
        }

        return baseResponse;
    }

    /**
     * validate user login
     *
     * @param username_email
     * @param password
     * @return
     */
    public BaseResponse userLogin(String username_email, String password) {
        BaseResponse baseResponse = new BaseResponse();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String description = "An error has occured ,please try again";
        try {
            Optional<User> isUser = userRepository.findByEmailOrUsername(username_email, username_email.toLowerCase());
            statusCode = HttpStatus.BAD_REQUEST.value();
            if (!isUser.isPresent()) {
                baseResponse.setStatusCode(statusCode);
                baseResponse.setDescription("User does not exist");
                logger.error(baseResponse.getDescription());
                return baseResponse;
            }
            User user = isUser.get();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                baseResponse.setStatusCode(statusCode);
                baseResponse.setDescription("Invalid password");
                logger.error(baseResponse.getDescription(), isUser.get().toString());
                return baseResponse;
            }
            if (user.getBlocked_status() == 1) {
                baseResponse.setStatusCode(statusCode);
                baseResponse.setDescription("Your account has been blocked. Please, contact support@thewealthmarket.com");
                logger.error(baseResponse.getDescription(), isUser.toString());
                return baseResponse;
            }
            baseResponse.setStatusCode(HttpServletResponse.SC_OK);
            baseResponse.setDescription("User found");
            logger.info(baseResponse.getDescription() + ": {}", user.toString());
            return generateToken(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        baseResponse.setStatusCode(statusCode);
        baseResponse.setDescription(description);
        return baseResponse;
    }

    /**
     * validate jwt token
     *
     * @param token
     * @return
     */
    public BaseResponse validateToken(String token) {
        DecodedJWT decodedJwt = jwtHelper.validateToken(token);
        BaseResponse baseResponse = new BaseResponse();
        if (decodedJwt != null) {
            DecodedJWT jwt = JWT.decode(token);
            String userId = jwt.getId();
            baseResponse.setStatusCode(HttpStatus.OK.value());
            baseResponse.setDescription("Successful - Token validated");
            baseResponse.setData(userId);
            logger.info(baseResponse.getDescription()+ ": {}", token);
            // other check e.g authorization
        } else {
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setDescription("Invalid access token");
            logger.error(baseResponse.getDescription());
        }

        return baseResponse;
    }


    public BaseResponse refreshToken(String token) {
        BaseResponse baseResponse = new BaseResponse();

        return baseResponse;
    }
}
