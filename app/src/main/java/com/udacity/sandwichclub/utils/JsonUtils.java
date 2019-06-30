package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAINNAME = "mainName";
    private static final String ALSOKNOWNAS = "alsoKnownAs";
    private static final String PLACEOFORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String INGREDIENTS = "ingredients";
    private static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {

        String sDescription = "";
        String sName = "";
        String sOrigin = "";
        String sImage = "";
        List<String> sAlsoKnownAs = new ArrayList<>();
        List<String> sIngredients = new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject nameObject=jsonObject.getJSONObject(NAME);
            sName=nameObject.optString(MAINNAME);

            JSONArray akaArray=nameObject.getJSONArray(ALSOKNOWNAS);
            sAlsoKnownAs=processJSONArray(akaArray);

            sOrigin=jsonObject.getString(PLACEOFORIGIN);
            sImage=jsonObject.getString(IMAGE);
            sDescription=jsonObject.getString(DESCRIPTION);

            JSONArray ingArray=jsonObject.getJSONArray(INGREDIENTS);
            sIngredients=processJSONArray(ingArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich(sName,sAlsoKnownAs,sOrigin,sDescription,sImage,sIngredients);
    }

    private static List<String> processJSONArray(JSONArray jsonArray) throws JSONException {
        List<String> list=new ArrayList<>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){

            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
