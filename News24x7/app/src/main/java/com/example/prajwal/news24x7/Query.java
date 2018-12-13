package com.example.prajwal.news24x7;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Query {


    private Query(){

    }

    public static ArrayList<News> newsExtract(String stringurl){
        ArrayList<News> news=new ArrayList<>();
        String jsonResponse="";



        URL url=createUrl(stringurl);
        try {
            jsonResponse=makeHTTPrequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject response=new JSONObject(jsonResponse);
            JSONArray newsArray=response.getJSONArray("articles");
            for(int i=0;i<newsArray.length();i++){
                JSONObject currentNews=newsArray.getJSONObject(i);
                JSONObject source=currentNews.getJSONObject("source");
                String name=source.getString("name");
                String title=currentNews.getString("title");
                String uri=currentNews.getString("url");
                String imageUri=currentNews.getString("urlToImage");
                String desc=currentNews.getString("description");
                String date=currentNews.getString("publishedAt");
                String content=currentNews.getString("content");

                news.add(new News(name,title,desc,uri,imageUri,date,content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    private static String makeHTTPrequest(URL url) throws IOException {
        String response="";
        if(url==null)
            return response;
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                response=readFromStream(inputStream);
            }
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader =new BufferedReader(inputStreamReader);
            String line =reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringurl) {
        URL url =null;
        try {
            url=new URL(stringurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
