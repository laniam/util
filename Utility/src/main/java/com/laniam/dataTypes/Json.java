/**
 * 
 */
package com.laniam.dataTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.laniam.util.HelperMethods;
import com.laniam.util.Validate;

/**
 * @author ardhani
 * 
 */
public class Json {

	private String jsonString;
	private Map<String, Object> jsonMap;

	public Json(String jsonString) throws JsonParseException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		if (Validate.isValidJson(jsonString)) {
			this.jsonString = jsonString;
			this.jsonMap = objectMapper.readValue(jsonString, Map.class);
		}
	}

	public <T> Json(String key, Object value) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put(key, value);
		this.jsonMap = tempMap;
		this.jsonString = HelperMethods.jsonMapToString(tempMap);

	}

	public Json(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
		this.jsonString = HelperMethods.jsonMapToString(jsonMap);
	}

	public Json(List<String> keys, List<Object> values) {
		jsonMap = HelperMethods.formJsonMap(keys, values);
		jsonString = HelperMethods.jsonMapToString(jsonMap);
	}

	public boolean isValidJson() {
		return Validate.isValidJson(jsonString);
	}

	public <T> T get(String key) {
		String[] keys = key.split(".");
		List<String> keysList = Arrays.asList(keys);
		Map<String, ?> jsonMap_ = jsonMap;
		T returnValue = null;
		for (String key_ : keysList) {
			if (jsonMap_.containsKey(key_)) {
				Object temp = jsonMap_.get(key_);
				if (Validate.isLastIndexOfList(keysList, key_)) {
					returnValue = (T) temp;
				} else if (temp instanceof Map) {
					jsonMap_ = (Map<String, ?>) temp;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return returnValue;
	}

	public <T> void set(String key, T value) {
		//TODO
	}

	public <T> void add(String key, T value) {
		//TODO
	}

	public void remove(String key) {
		//TODO
	}


	public Map<String, ?> toMap() {
		return jsonMap;
	}

	@Override
	public String toString() {
		return jsonString;
	}
}