package com.xc.lib.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

public class XGsonUtil {

	private static JsonParser jsonParser = new JsonParser();

	/**
	 * 将Json String解析为JsonObject
	 * 
	 * @param json
	 *            要解析的json字符串
	 * @throws Exception
	 * @return JsonObject 解析后的JsonObject对象
	 */
	public static JsonObject getJsonObject(String json) throws Exception {
		if (json == null || json.length() == 0) {
			return new JsonObject();
		} else {
			JsonObject object = jsonParser.parse(json).getAsJsonObject();
			return object;
		}
	}

	/**
	 * 将Json String解析为JsonArray
	 * 
	 * @param json
	 *            要解析的json字符串
	 * @throws Exception
	 * @return JsonArray 解析后的JsonArray对象
	 */
	public static JsonArray getJsonArray(String json) throws Exception {
		if (json == null || json.length() == 0) {
			return new JsonArray();
		} else {
			return jsonParser.parse(json).getAsJsonArray();
		}
	}

	/**
	 * 根据json字符串，获取pojo对象
	 * 
	 * @param useExpose
	 *            为true时， 不解析实体中没有用@Expose注解的属性；为false时，解析所有属性
	 * @param json
	 * @param cls
	 * @return
	 * @return T
	 */
	public static <T> T getObjectFromJson(boolean useExpose, String json, Class<T> cls) throws Exception {
		Gson gson = null;
		if (useExpose) {
			// 不解析实体中没有用@Expose注解的属性
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		} else {
			// 全部解析
			gson = new Gson();
		}
		return gson.fromJson(json, cls);
	}

	/**
	 * 
	 * @param useExpose
	 *            为true时， 不解析实体中没有用@Expose注解的属性；为false时，解析所有非transient属性
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> getListFromJson(boolean useExpose, String json, Class<T> cls) throws Exception {

		List<T> list = new ArrayList<T>();

		JsonArray array = getJsonArray(json);
		for (int i = 0; i < array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject();
			list.add(getObjectFromJson(useExpose, object.toString(), cls));
		}

		return list;
	}

	/**
	 * 为true时， 不解析实体中没有用@Expose注解的属性；为false时，解析所有非transient属性
	 * 
	 * @param useExpose
	 * @param array
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> getListFromJson(boolean useExpose, JsonArray array, Class<T> cls) throws Exception {

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject();
			list.add(getObjectFromJson(useExpose, object.toString(), cls));
		}

		return list;
	}

	/**
	 * 根据pojo对象，获取json字符串
	 * 
	 * @param useExpose
	 *            为true时， 不解析实体中没有用@Expose注解的属性；为false时，解析所有非transient属性
	 * @param t
	 * @return String
	 */
	public static <T> String getJsonFromObject(boolean useExpose, T t) {
		Gson gson = null;
		if (useExpose) {
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		} else {
			gson = new Gson();
		}
		return gson.toJson(t);
	}

	/**
	 * 根据pojo对象，获取json字符串
	 * 
	 * @param useExpose
	 *            为true时， 不解析实体中没有用@Expose注解的属性；为false时，解析所有非transient属性
	 * @param t
	 * @return String
	 */
	public static <T> String getJsonFromList(boolean useExpose, Collection<T> list) {
		Gson gson = null;
		if (useExpose) {
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		} else {
			gson = new Gson();
		}
		return gson.toJson(list);
	}

	public static <K, T> String getJsonFromObject(boolean useExpose, JsonSerializer<K> serializer, T t) {
		GsonBuilder builder = new GsonBuilder();
		if (serializer != null) {
			builder.registerTypeAdapter(serializer.getClass(), serializer);
		}

		if (useExpose) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}

		return builder.create().toJson(t);
	}


	/**
	 * JSONArray转list
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public List<JSONObject> getListByJSONArray(JSONArray array) throws JSONException {
		List<JSONObject> list = new ArrayList<JSONObject>();
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				list.add(array.getJSONObject(i));
			}
		}

		return list;
	}
	
	public List<JsonObject> getListByJSONArray(JsonArray array) throws Exception{
		List<JsonObject> list = new ArrayList<JsonObject>();
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				list.add(array.get(i).getAsJsonObject());
			}
		}

		return list;
	}
}
