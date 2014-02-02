/**
 * 
 */
package com.laniam.util;

import java.io.IOException;

import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ardhani
 * 
 */
public class ValidationFactory {

	public static final boolean isValidJson(String jsonString) {
		boolean validJson = false;
		try {
			new ObjectMapper().getJsonFactory()
					.createJsonParser(jsonString);
			validJson = true;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return validJson;
	}

	public static <T> boolean isLastIndexOfList(List<T> list, T key) {
		if (list.indexOf(key) == list.size() - 1)
			return true;
		return false;
	}

}
