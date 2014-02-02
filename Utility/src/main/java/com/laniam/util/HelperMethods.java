/**
 * 
 */
package com.laniam.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ardhani
 * 
 */
public class HelperMethods {

	@SuppressWarnings("unchecked")
	public static <T, E> String formJsonString(T keys, E values) {
		String returnJsonString = Constants.EMPTYSTRING;
		List<String> keysList = null;
		List<E> valuesList = null;
		if (keys instanceof List) {
			keysList = (List<String>) keys;
			valuesList = (List<E>) values;
		} else if (keys instanceof String) {
			keysList = Arrays.asList(((String) keys).split("."));
			valuesList = (List<E>) Arrays.asList(((String) values).split("."));
			;
		}
		if (keysList.size() == valuesList.size()) {
			returnJsonString += Constants.OPENFLOWERBRACES;
			for (int i = 0; i < keysList.size(); i++) {
				returnJsonString += keysList.get(i) + Constants.COLIN
						+ valuesList.get(i);
			}
			returnJsonString += Constants.OPENFLOWERBRACES;
		}
		return returnJsonString;
	}

	public static String jsonMapToString(Map<String, Object> jsonMap) {
		try {
			return new ObjectMapper().writeValueAsString(jsonMap);
		} catch (IOException e) {
			return Constants.EMPTYSTRING;
		}
	}

	public static Map<String, Object> formJsonMap(List<String> keys,
			List<Object> values) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (keys.size() == values.size()) {
			for (int i = 0; i < keys.size(); i++) {
				jsonMap.put(keys.get(i), values.get(i));
			}
		} else if (values.size() == 1) {
			for (int i = 0; i < keys.size(); i++) {
				jsonMap.put(keys.get(i), values.get(0));
			}
		}
		return jsonMap;
	}
}