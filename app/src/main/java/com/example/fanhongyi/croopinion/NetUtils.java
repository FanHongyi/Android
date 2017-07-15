package com.example.fanhongyi.croopinion;

/**
 * Created by FANHONGYI on 2017/7/14.
 */
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetUtils {
    //http://api.openweathermap.org/data/2.5/forecast/daily?q=Changsha&mode=json&unit=metric&cnt=7&APPID=04308f28de794253444ecc10eca4f1ac
    private static final String BASE_URL = "";
    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;


    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "units";
    /* The days parameter allows us to designate how many days of weather data we want */
    private static final String DAYS_PARAM = "cnt";
    private static final String APPID_PARAM = "APPID";

    public static URL buildUrl() {
        Uri weatherQueryUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .appendQueryParameter(APPID_PARAM, "04308f28de794253444ecc10eca4f1ac")
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void processData(String data) {
        if(data != null && data.startsWith("\ufeff"))
        {
            data =  data.substring(1);
        }
        try {
            JSONObject json = new JSONObject(data);

            JSONArray keywordsArray=json.getJSONArray("keywords");
            for(int i = 0; i < 10; i++){
                MainActivity.newTopic[i]=keywordsArray.getString(i);
            }
            JSONArray tendencyArray=json.getJSONArray("tendency");
            for(int i = 0; i < 3; i++){
                MainActivity.reportTendency[i]=(float)tendencyArray.getDouble(i);
            }
            JSONArray frequencyArray=json.getJSONArray("frequency");
            for(int i = 0; i < 7; i++){
                MainActivity.reportFrequency[i]=(float)frequencyArray.getDouble(i);
            }
        } catch(JSONException e)
        {
            Log.wtf("json", e);
        }
    }

    public static URL buildKeywordUrl(String keyword) {
        Uri weatherQueryUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .appendQueryParameter(APPID_PARAM, "04308f28de794253444ecc10eca4f1ac")
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getResponseFromKeywordHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void processKeywordData(String data) {
        if(data != null && data.startsWith("\ufeff"))
        {
            data =  data.substring(1);
        }
        try {
            JSONObject json = new JSONObject(data);
            JSONArray frequencyArray=json.getJSONArray("frequency");
            for(int i = 0; i < 7; i++){
                KeywordFragment.keywordFrequency[i]=(float)frequencyArray.getDouble(i);
            }
        } catch(JSONException e)
        {
            Log.wtf("json", e);
        }
    }
}

