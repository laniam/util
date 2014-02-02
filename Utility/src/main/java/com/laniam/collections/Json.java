/**
 * 
 */
package com.laniam.collections;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.laniam.util.HelperMethods;
import com.laniam.util.ValidationFactory;

/**
 * @author ardhani
 * 
 */
public class Json {

	private String jsonString;
	private Map<String, Object> jsonMap;

	@SuppressWarnings("unchecked")
	public Json(String jsonString) throws JsonParseException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		if (isValid(jsonString)) {
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
		return ValidationFactory.isValidJson(jsonString);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		String[] keys = key.split(".");
		List<String> keysList = Arrays.asList(keys);
		Map<String, ?> jsonMap_ = jsonMap;
		T returnValue = null;
		for (String key_ : keysList) {
			if (jsonMap_.containsKey(key_)) {
				Object temp = jsonMap_.get(key_);
				if (ValidationFactory.isLastIndexOfList(keysList, key_)) {
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

	@SuppressWarnings("unchecked")
	public <T> Object set(String key, T value) {
		List<String> keysList = parseKeys(key);
		Map<String, Object> jsonMap_ = null;
		for (String key_ : keysList) {
			if (ValidationFactory.isLastIndexOfList(keysList, key_)) {
				jsonMap_.put(key_, value);
				return value;
			} else if (jsonMap_.containsKey(key_)) {
				Object temp = jsonMap_.get(key_);
				if (temp instanceof Map) {
					jsonMap_ = (Map<String, Object>) temp;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> void add(String key, T value) {
		List<String> keysList = parseKeys(key);
		Map<String, Object> jsonMap_ = null;
		if (keysList.size() == 1) {
			jsonMap.put(key, value);
		} else {
			String previousKey = "";
			for (String key_ : keysList) {
				if (ValidationFactory.isLastIndexOfList(keysList, key_)) {
					jsonMap_.put(key_, value);
				} else if (jsonMap_.containsKey(key_)) {
					Object temp = jsonMap_.get(key_);
					if (temp instanceof Map) {
						jsonMap_ = (Map<String, Object>) temp;
					} else {
						break;
					}
				} else {
					if (!previousKey.isEmpty()) {
						jsonMap_ = (Map<String, Object>) jsonMap
								.get(previousKey);
					}
					jsonMap_.put(key_, new HashMap<String, Object>());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Object remove(String key) {
		List<String> keysList = parseKeys(key);
		Object returnValue = null;
		Map<String, Object> jsonMap_ = jsonMap;
		for (String key_ : keysList) {
			if (jsonMap_.containsKey(key_)) {
				Object temp = jsonMap_.get(key_);
				if (ValidationFactory.isLastIndexOfList(keysList, key_)) {
					returnValue = jsonMap_.remove(key_);
				} else if (temp instanceof Map) {
					jsonMap_ = (Map<String, Object>) temp;
				} else {
					break;
				}
			}
		}
		return returnValue;
	}

	public Map<String, ?> toMap() {
		return jsonMap;
	}

	@Override
	public String toString() {
		return jsonString;
	}

	private List<String> parseKeys(String key) {
		String[] keys = key.split(".");
		return Arrays.asList(keys);
	}

	public static final boolean isValid(String jsonString) {
		boolean validJson = false;
		try {
			new ObjectMapper().getJsonFactory().createJsonParser(jsonString);
			validJson = true;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return validJson;
	}

}