package com.wow.security.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.savedrequest.SavedRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class HttpServletUtil {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";


	public static <T> T readRequestData(HttpServletRequest request, Class<T> target) throws JsonParseException, JsonMappingException, IOException {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		T data = JacksonConversionUtil.readValue(sb.toString(), target);
		return data;
	}

    public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isAjax(SavedRequest request) {
        return request.getHeaderValues(X_REQUESTED_WITH).contains(XML_HTTP_REQUEST);
    }

    public static boolean isContentTypeJson(SavedRequest request) {
        return request.getHeaderValues(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }
}
