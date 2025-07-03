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

    /**
     * Get parsed ArrayList of String from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of String
     */
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    /**
     * Get String value from SharedPreferences at 'key'. If key not found, return ""
     *
     * @param key SharedPreferences key
     * @return String value at 'key' or "" (empty String) if key not found
     */
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * Get parsed ArrayList of ItemsModel from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of ItemsModel
     */
    public ArrayList<ItemsModel> getListObject(String key) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<ItemsModel> playerList = new ArrayList<ItemsModel>();

        for (String jObjString : objStrings) {
            ItemsModel player = gson.fromJson(jObjString, ItemsModel.class);
            playerList.add(player);
        }
        return playerList;
    }

    /**
     * Put ArrayList of String into SharedPreferences with 'key' and save
     *
     * @param key        SharedPreferences key
     * @param stringList ArrayList of String to be added
     */
    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    /**
     * Put ArrayList of ItemsModel into SharedPreferences with 'key' and save
     *
     * @param key        SharedPreferences key
     * @param playerList ArrayList of ItemsModel to be added
     */
    public void putListObject(String key, ArrayList<ItemsModel> playerList) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (ItemsModel player : playerList) {
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }

    /**
     * Save a custom object (e.g., UsersModel)
     */
    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        preferences.edit().putString(key, jsonString).apply();
    }

    /**
     * Retrieve a custom object (e.g., UsersModel)
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String jsonString = preferences.getString(key, null);
        if (jsonString == null) return null;
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     *
     * @param key the pref key to check
     */
    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }
}