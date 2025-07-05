package com.example.mobile_midterm.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.example.mobile_midterm.Domain.ItemsModel;
import com.example.mobile_midterm.Domain.UsersModel;

import java.util.ArrayList;
import java.util.Arrays;

public class TinyDB {

    private SharedPreferences preferences;

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    // ✅ Newly added method
    public void putString(String key, String value) {
        checkForNullKey(key);
        preferences.edit().putString(key, value).apply();
    }

    public ArrayList<ItemsModel> getListObject(String key) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = getListString(key);
        ArrayList<ItemsModel> itemList = new ArrayList<>();

        for (String jObjString : objStrings) {
            ItemsModel item = gson.fromJson(jObjString, ItemsModel.class);
            itemList.add(item);
        }
        return itemList;
    }

    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[0]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public void putListObject(String key, ArrayList<ItemsModel> itemList) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<>();
        for (ItemsModel item : itemList) {
            objStrings.add(gson.toJson(item));
        }
        putListString(key, objStrings);
    }

    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        preferences.edit().putString(key, jsonString).apply();
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String jsonString = preferences.getString(key, null);
        if (jsonString == null) return null;
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException("SharedPreferences key cannot be null");
        }
    }
}
