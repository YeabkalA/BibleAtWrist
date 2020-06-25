package com.yeabkalwubshit.watchbible;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData {
    private static final String WEATHER_API_STR = "https://api.openweathermap.org/data/2.5/weather?q=irving&appid=e8ec2779e79854df6e170e0bac6d2be4";

    private static final String TAG = "WeatherData";
    JSONObject weatherDataJson;
    JSONObject baseJsonObject;

    public WeatherData() throws Exception {
        loadWeatherDataJson();
    }


    private void loadWeatherDataJson() throws Exception{
        URL obj = new URL(WEATHER_API_STR);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        InputStream inputStream;
        StringBuffer response = new StringBuffer();

        Log.d(TAG, "GOing to start try block weather");
        try {
            con.setRequestMethod("GET");
            inputStream = new BufferedInputStream(con.getInputStream());
            Log.d(TAG, "Got input stream try block weather");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch(Exception e) {
            System.err.println("Network error" + e.toString());
        } finally {
            con.disconnect();
        }

        Log.d(TAG, "Done try block weather");


        JSONObject jsonObject = new JSONObject(response.toString());
        this.baseJsonObject = jsonObject;

        String mainDataStr = jsonObject.get("main").toString();

        this.weatherDataJson = new JSONObject(mainDataStr);
    }

    private static double kelvinToC(double kelvinT) {
        return kelvinT - 273.0;
    }

    public String getIconCode() throws Exception {
        JSONObject weatherInfo = getWeatherInfo();
        return weatherInfo.get("icon").toString();
    }

    public String getIconUrl() throws Exception {
        return "http://openweathermap.org/img/wn/" + getIconCode() + "@2x.png";
    }

    public Drawable loadIcon() {
        try {
            InputStream is = (InputStream) new URL(getIconUrl()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject getWeatherInfo() throws Exception {
        JSONArray weatherDataArray = new JSONArray(baseJsonObject.get("weather").toString());
        return new JSONObject(weatherDataArray.get(0).toString());
    }

    public double getTemperature() throws Exception {
        if (weatherDataJson == null) {
            return -1.0;
        }
        double kelvinT = Double.parseDouble(weatherDataJson.get("temp").toString());

        return kelvinToC(kelvinT);
    }
}
