package com.example.huynh.httpurlconnectiondemo;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadJSON extends AsyncTask<String, Void, String> {

    private TextView textView;
    private Context context;
    private Student student;
    private TextView ten, tuoi;

    public DownloadJSON(TextView textView, Context context, Student student, TextView ten, TextView tuoi) {
        this.textView = textView;
        this.context = context;
        this.student = student;
        this.ten = ten;
        this.tuoi = tuoi;
    }


    @Override
    protected String doInBackground(String... params) {
        String chuoi = GetJSON(params[0],100);
        return chuoi;
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

    public String GetJSON(String url, int timeout) {

        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.connect();
            int status = conn.getResponseCode();
            if (status == 200 || status == 201)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;

    }
}
