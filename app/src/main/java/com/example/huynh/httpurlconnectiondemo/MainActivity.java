package com.example.huynh.httpurlconnectiondemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String jsonStr;
    Student student = new Student();
    TextView ten,tuoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        ten = findViewById(R.id.ten);
        tuoi = findViewById(R.id.tuoi);

        String jsonUrl = "https://www.dl.dropboxusercontent.com/s/2rc1sz2t6kjrvvi/DemoJson.json?dl=0";

        // Tạo một đối tượng làm nhiệm vụ download nội dung json từ URL.
        DownloadJSON task = new DownloadJSON(this.textView, this, student, ten, tuoi);

        // Thực thi nhiệm vụ (Truyền vào URL).
        task.execute(jsonUrl);

        Toast.makeText(this, jsonStr, Toast.LENGTH_SHORT).show();

//        ObjectMapper mapper = new ObjectMapper();
//
////        try {
////            Student student = mapper.readValue(jsonStr, Student.class);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
    }

    private boolean checkInternetConnection() {

        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }


        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }
}


