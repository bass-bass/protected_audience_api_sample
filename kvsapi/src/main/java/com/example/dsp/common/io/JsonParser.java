package com.example.dsp.common.io;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class JsonParser {
	private static final Gson gson;
	private static final Gson gsonExpose;
	private static final Gson gson_HtmlUnSafe;
	private static final Gson gsonExpose_HtmlUnSafe;

	public JsonParser() {
	}

	public static <T> T toObject(String json, Class<T> c) {
		return gson.fromJson(new StringReader(json), c);
	}

	public static <T> T toObjectWithoutHTMLSafe(String json, Class<T> c) {
		return gson_HtmlUnSafe.fromJson(new StringReader(json), c);
	}

	public static <T> T toObjectWithExpose(String json, Class<T> c) {
		return gsonExpose.fromJson(new StringReader(json), c);
	}

	public static <T> T toObjectWithExposeWithoutHTMLSafe(String json, Class<T> c) {
		return gsonExpose_HtmlUnSafe.fromJson(new StringReader(json), c);
	}

	public static <T> T toObject(String json, Type t) {
		return gson.fromJson(json, t);
	}

	public static <T> T toObjectWithoutHTMLSafe(String json, Type t) {
		return gson_HtmlUnSafe.fromJson(json, t);
	}

	public static <T> T toObjectWithExpose(String json, Type t) {
		return gsonExpose.fromJson(json, t);
	}

	public static <T> T toObjectWithExposeWithoutHTMLSafe(String json, Type t) {
		return gsonExpose_HtmlUnSafe.fromJson(json, t);
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static String toJsonWithoutHTMLSafe(Object obj) {
		return gson_HtmlUnSafe.toJson(obj);
	}

	public static String toJsonWithExpose(Object obj) {
		return gsonExpose.toJson(obj);
	}

	public static String toJsonWithExposeWithoutHTMLSafe(Object obj) {
		return gsonExpose_HtmlUnSafe.toJson(obj);
	}

	static {
		JsonDeserializer<Int2IntMap> intMapDeserializer = new JsonDeserializer<Int2IntMap>() {
			public Int2IntMap deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
				JsonObject obj = json.getAsJsonObject();
				if (obj.isJsonNull()) {
					return null;
				} else {
					Set<Map.Entry<String, JsonElement>> jsonEntrySet = obj.entrySet();
					Int2IntMap map = new Int2IntOpenHashMap();
					Iterator<Map.Entry<String, JsonElement>> ite = jsonEntrySet.iterator();
					while(ite.hasNext()) {
						Map.Entry<String, JsonElement> entry = ite.next();
						String key = (String)entry.getKey();
						String value = ((JsonElement)entry.getValue()).getAsString();
						map.put(Integer.parseInt(key), Integer.parseInt(value));
					}

					return map;
				}
			}
		};
		GsonBuilder safeGson = new GsonBuilder();
		safeGson.registerTypeAdapter(Int2IntMap.class, intMapDeserializer);
		gson = safeGson.create();
		gsonExpose = safeGson.excludeFieldsWithoutExposeAnnotation().create();
		GsonBuilder unSafeGson = new GsonBuilder();
		unSafeGson.registerTypeAdapter(Int2IntMap.class, intMapDeserializer);
		gson_HtmlUnSafe = unSafeGson.disableHtmlEscaping().create();
		gsonExpose_HtmlUnSafe = unSafeGson.excludeFieldsWithoutExposeAnnotation().create();
	}
}

