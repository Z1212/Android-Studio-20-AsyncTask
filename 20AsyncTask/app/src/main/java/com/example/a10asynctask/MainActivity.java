package com.example.a10asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView imageView;
    Button button;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imgV1);
        button = findViewById(R.id.loading);
        button.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
    }


    @Override
    public void onClick(View v) {
        new downloadimg().execute("https://images.pexels.com/photos/2219433/pexels-photo-2219433.jpeg?cs=srgb&dl=pexels-lucas-pezeta-2219433.jpg&fm=jpg");

    }
    private  class  downloadimg extends AsyncTask<String, Integer, Bitmap> {
        HttpURLConnection httpURLConnection;


        @Override
        protected Bitmap doInBackground(String... strings) {
            //從Url載入圖片
            try {
                URL url = new URL(strings[0]);
                progressBar.incrementProgressBy(25);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                progressBar.incrementProgressBy(50);
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                progressBar.incrementProgressBy(75);
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                progressBar.incrementProgressBy(100);
                return temp;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                imageView.setImageBitmap(bitmap);
        }}

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null){
                progressBar.setProgress(values[0]);

            }
        }
    }
}