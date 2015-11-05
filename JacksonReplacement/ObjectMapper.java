package com.darylbeattie.movies.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ObjectMapper {

    /**
     * This takes a JSON string and creates (and populates) an object of the
     * given class with the data from that JSON string. It mimics the method
     * signature of the jackson JSON API, so that we don't have to import the
     * jackson library into this application.
     *
     * @param jsonString The JSON string to parse.
     * @param objClass The class of object we want to create.
     * @return The instantiation of that class, populated with data from the
     * JSON object.
     * @throws IOException If there was any kind of issue.
     */
    public <T> T readValue(String jsonString, Class<T> objClass) throws IOException {
        try {
            return readValue(new JSONObject(jsonString), objClass);
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T, R> T readValue(JSONObject json, Class<T> objClass) throws IOException {
        try {
            //TODO Iterate through json object values and call the JsonAnySetter method on the unknown ones.
            T obj = objClass.newInstance();
            for (Field f : objClass.getFields()) {
                Annotation a = f.getAnnotation(JsonProperty.class);
                if (List.class.equals(f.getType()) && (json.optJSONArray(((JsonProperty) a).value()) != null)) { // It's a list.
                    JSONArray jsonArray = json.optJSONArray(((JsonProperty) a).value());
                    ParameterizedType listType = (ParameterizedType) f.getGenericType();
                    Class<?> subObj = (Class<?>) listType.getActualTypeArguments()[0];
                    List<R> subObjList = ((Class<List<R>>) f.getType()).newInstance();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        subObjList.add((R) readValue(jsonArray.getJSONObject(i), subObj));
                    }
                    f.set(obj, subObjList);
                } else if (a != null) {
                    f.set(obj, json.opt(((JsonProperty) a).value()));
                }
            }
            return obj;
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

}
