package com.example.mycocktailrecipe.util;

/**
 * project name：MyCocktailRecipe
 * className：
 * author：shuoyang
 * Date：2019-08-05 04:13
 */

import android.text.TextUtils;

import com.example.mycocktailrecipe.db.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleTypeResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allTypes = new JSONArray(response);
                for (int i = 0; i < allTypes.length(); i++) {
                    JSONObject typeObject = allTypes.getJSONObject(i);
                    Type type = new Type();
                    type.setType(typeObject.getString("type"));
                    type.setId(typeObject.getInt("id"));
                    type.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }









}