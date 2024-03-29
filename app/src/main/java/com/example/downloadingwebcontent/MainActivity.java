package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private class DownloadTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... urls) {

            //Log.i("URL", urls[0]);
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;

                    result.append(current);

                    data = reader.read();
                }

                return result.toString();


            } catch (IOException e) {

                e.printStackTrace();

                return "Failed";

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {

            result = task.execute("https://www.ecowebhosting.co.uk/").get();

        } catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();
        }

        assert result != null;
        Log.i("Contents of URL ",result);
    }
}
