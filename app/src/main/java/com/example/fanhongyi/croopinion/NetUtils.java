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
    private static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
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
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
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
        try {
            JSONObject json = new JSONObject(data);
            JSONObject cityJson = json.getJSONObject("city");
            String cityName = cityJson.getString("name");
            JSONObject cityCoord = cityJson.getJSONObject("coord");
            double cityLatitude = cityCoord.getDouble("lat");
            double cityLongitude = cityCoord.getDouble("lon");
            JSONArray weatherArray = json.getJSONArray("list");

            for(int i = 0; i < 7; i++) {
                // These are the values that will be collected.
                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);
                MainActivity.dt[i]=dayForecast.getLong("dt");
                MainActivity.pressure[i] = dayForecast.getDouble("pressure");
                MainActivity.humidity[i] = dayForecast.getInt("humidity");
                MainActivity.windSpeed[i] = dayForecast.getDouble("speed");
                MainActivity.windDirection[i] = dayForecast.getDouble("deg");
                // Description is in a child array called "weather", which is 1 element long.
                // That element also contains a weather code.
                JSONObject weatherObject =
                        dayForecast.getJSONArray("weather").getJSONObject(0);
                MainActivity.description[i] = weatherObject.getString("description");
                MainActivity.weatherId[i] = weatherObject.getInt("id");

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject("temp");
                MainActivity.high[i] = temperatureObject.getDouble("max");
                MainActivity.low[i] = temperatureObject.getDouble("min");
            }
        } catch(JSONException e)
        {
            Log.wtf("json", e);
        }
    }
}

