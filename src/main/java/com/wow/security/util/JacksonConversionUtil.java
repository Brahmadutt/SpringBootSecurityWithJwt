package com.wow.security.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonConversionUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T convert(Object source, Class<T> target) {
		return mapper.convertValue(source, target);
	}

	public static <T> T readValue(String jsonString, Class<T> target) {
		try {
			// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(jsonString, target);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String writeValueAsString(Object source) {
		try {
			return mapper.writeValueAsString(source);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static byte[] writeValueAsBytes(Object source) {
		try {
			return mapper.writeValueAsBytes(source);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static JsonNode toJsonNode(String value) {
		try {
			return mapper.readTree(value);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T clone(T value) {
		return readValue(writeValueAsString(value), (Class<T>) value.getClass());
	}
}
