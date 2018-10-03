package com.example.huynh.httpurlconnectiondemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadJSON extends AsyncTask<String, Void, String> {

    private TextView textView;
    Context context;
    Student student;
    TextView ten, tuoi;

    public DownloadJSON(TextView textView, Context context, Student student, TextView ten, TextView tuoi) {
        this.textView = textView;
        this.context = context;
        this.student = student;
        this.ten = ten;
        this.tuoi = tuoi;
    }


    @Override
    protected String doInBackground(String... params) {
        String textUrl = params[0];

        InputStream in = null;
        BufferedReader br= null;
        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

//            httpConn.setAllowUserInteraction(false);
//            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                br= new BufferedReader(new InputStreamReader(in));

                StringBuilder sb= new StringBuilder();
                String s= null;
                while((s= br.readLine())!= null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        if(result  != null){
            this.textView.setText(result);
            Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show();

            //Jackson
            ObjectMapper mapper = new ObjectMapper();
            try {
                student = mapper.readValue(result, Student.class);
                ten.setText(student.getTen());
                tuoi.setText(student.getTuoi());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
