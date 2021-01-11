package com.wm.core.config;


import com.google.common.primitives.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CachingHttpRequestWrapper extends HttpServletRequestWrapper {


    private byte[] requestBody = new byte[0];
    private boolean bufferFilled = false;
    private Map<String, String[]> parameterMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(CachingHttpRequestWrapper.class);

    /**
     - Constructs a request object wrapping the given request.
     *
     - @param request The request to wrap
     - @throws IllegalArgumentException if the request is null
     */
    public CachingHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    public byte[] getRequestBody() throws IOException {
        if (bufferFilled) {
            return Arrays.copyOf(requestBody, requestBody.length);
        }

        InputStream inputStream = super.getInputStream();

        byte[] buffer = new byte[102400]; // 100kb buffer

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            requestBody = Bytes.concat(this.requestBody, Arrays.copyOfRange(buffer, 0, bytesRead)); // <1>
        }

        bufferFilled = true;

        //parse the content into parameters map
        if(MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(getContentType()))
            parseParameters(new String(requestBody));

        return requestBody;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(getRequestBody()); // <1>
    }

    @Override
    public String getParameter(String name) {
        if(name == null) return null;
        if(parameterMap != null && parameterMap.get(name) != null && parameterMap.get(name).length > 0) return parameterMap.get(name)[0];
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        if(name == null) return super.getParameterValues(name);
        if(parameterMap != null && !parameterMap.isEmpty()) return parameterMap.get(name);
        return super.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return (parameterMap != null && !parameterMap.isEmpty()) ? parameterMap : super.getParameterMap();
    }


    private void parseParameters(String body) {

        if(logger.isDebugEnabled())
            logger.debug("parsing x-www-urlencoded parameters: {}", body);

        //split using the ampersand (&) separator
        Arrays.stream(body.split("&")).forEach(part -> {
            //split the key=value
            String[] parts = part.split("=");
            if(parts.length == 2) { //it's a valid key=value

                //determine if there's any value previously attached to the key
                int oldLength = (parameterMap.get(parts[0]) != null) ? parameterMap.get(parts[0]).length : 0;

                //construct a newValues array that accommodates the old one as well
                String[] newValues = new String[oldLength + 1];

                try {
                    //set the new value as the first entry
                    newValues[0] = URLDecoder.decode(parts[1], "UTF-8");

                    //if there're any old values copy them into the newValues array. We've made space for them
                    if(parameterMap.get(parts[0]) != null)
                        System.arraycopy(parameterMap.get(parts[0]), 0, newValues, 1, parameterMap.get(parts[0]).length);

                    //finally save the newValues which is oldValues + the new entry
                    parameterMap.put(parts[0], newValues);

                } catch (Exception e) {
                    logger.error("Error while decoding Parameter map value: " + e.getMessage(), e);
                }
            }
        });
    }

    private static class CustomServletInputStream extends ServletInputStream {

        private ByteArrayInputStream buffer;

        public CustomServletInputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }

}
