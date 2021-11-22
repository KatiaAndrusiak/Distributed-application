package com.example.myapplication.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class DataManagement {

    public final static String URL = "http://jabeda-env.eba-iqdahc9d.us-east-2.elasticbeanstalk.com";
    public final static String PROBLEMS_PATH = "/problems";
    public static List<String> categories;
    public static  HashMap<String, List<String>> problemsByCategories;
    private DataManagement() {
    }

    public static JSONArray getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONArray(jsonString);
    }


    public static  List<String>  getCategoriesFromJsonObject(JSONArray arr) throws JSONException {
        categories = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            categories.add(((JSONObject)arr.get(i)).getJSONObject("category").getString("name"));
        }

        categories = categories
                .stream()
                .distinct().collect(Collectors.toList());

        problemsByCategories = new HashMap<>(categories.size());
        categories.forEach(
                c -> {
                    List<String> problems = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        try {
                            JSONObject obj = ((JSONObject)arr.get(i));
                            if(c.equals(obj.getJSONObject("category").getString("name"))){
                                problems.add(obj.getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    problemsByCategories.put(c, problems);
                }
        );
        return categories;
    }

//    public static HashMap<String, List<String>> parseJsonToProblemsByCategory(JSONArray arr, List<String> categories){
//       problemsByCategories = new HashMap<>(categories.size());
//        categories.forEach(
//                c -> {
//                    List<String> problems = new ArrayList<>();
//                    for (int i = 0; i < arr.length(); i++) {
//                        try {
//                            JSONObject obj = ((JSONObject)arr.get(i));
//                            if(c.equals(obj.getJSONObject("category").getString("name"))){
//                                problems.add(obj.getString("name"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    problemsByCategories.put(c, problems);
//                }
//        );
//        return  problemsByCategories;
//    }


}
