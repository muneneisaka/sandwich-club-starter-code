package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)  {

        //create the variables to hold the output from parsed json
        String mainName = "";
        String alsoKnownAs = "";
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        String ingredients = "";

        /*
            I took notes on how to implement the JSONObject from looking at this StackOverflow thread
            https://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
            4/28/18
         */

        try {
            JSONObject jb = new JSONObject(json);
            description=jb.getString("description");
            placeOfOrigin=jb.getString("placeOfOrigin");
            if(placeOfOrigin.equals("")) placeOfOrigin = "unknown";
            image=jb.getString("image");

            JSONObject jbname=new JSONObject(jb.getString("name")); //this holds both mainName and alsoKnownAs
            mainName=jbname.getString("mainName");//get the mainName portion of name

            JSONArray jsonAlsoKnownAs = jbname.getJSONArray("alsoKnownAs");//get the jsonArray that is the aliases
            for(int i = 0;i<jsonAlsoKnownAs.length(); i++){
                alsoKnownAs+=jsonAlsoKnownAs.getString(i)+", ";
            }

            JSONArray jsonArray = jb.getJSONArray("ingredients");//get the jsonArray that is the ingredients
            for (int i = 0; i<jsonArray.length();i++) {
                ingredients+= jsonArray.getString(i)+", ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return instance of Sandwich with the output
        return new Sandwich(mainName,Collections.singletonList(alsoKnownAs),placeOfOrigin, description,image,Collections.singletonList(ingredients));
    }
}
