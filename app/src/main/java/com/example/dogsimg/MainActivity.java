package com.example.dogsimg;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Single;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MainViewModel viewModel;
    private ImageView dogImageView;
    private Button dogButton;
    private ProgressBar dogProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();
        viewModel.getIsSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {

                } else {
                    Toast.makeText(MainActivity.this, "404", LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    dogProgressBar.setVisibility(View.VISIBLE);
                } else {
                    dogProgressBar.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getDogImage().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(dogImageView);
                Log.d(TAG, dogImage.getMessage());
            }
        });

        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initViews() {
        dogImageView = findViewById(R.id.dogImageView);
        dogButton = findViewById(R.id.dogButton);
        dogProgressBar = findViewById(R.id.dogProgressBar);
    }
}