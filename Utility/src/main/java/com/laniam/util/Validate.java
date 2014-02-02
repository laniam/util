/**
 * 
 */
package com.laniam.util;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ardhani
 * 
 */
public class Validate {

	public static final boolean isValidJson(String jsonString) {
		boolean validJson = false;
		try {
			JsonParser jsonParser = new ObjectMapper().getJsonFactory()
					.createJsonParser(jsonString);
			validJson = true;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
