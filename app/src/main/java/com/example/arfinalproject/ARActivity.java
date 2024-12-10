package com.example.arfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ARActivity extends AppCompatActivity {

    private String modelPath;
    private String landmarkName;
    private String landmarkDescription;
    private TextView landmarkInfo;
    private boolean isInfoVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        // Retrieve landmark information from the Intent
        modelPath = getIntent().getStringExtra("modelPath");
        landmarkName = getIntent().getStringExtra("landmarkName");
        landmarkDescription = getIntent().getStringExtra("landmarkDescription");

        // Set up landmark info display
        landmarkInfo = findViewById(R.id.landmarkInfo);
        if (landmarkName != null && landmarkDescription != null) {
            landmarkInfo.setText(landmarkName + "\n\n" + landmarkDescription);
        }

        // Back button functionality
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Toggle info button functionality
        Button btnToggleInfo = findViewById(R.id.btnToggleInfo);
        btnToggleInfo.setOnClickListener(v -> {
            isInfoVisible = !isInfoVisible;
            landmarkInfo.setVisibility(isInfoVisible ? View.VISIBLE : View.GONE);
        });

        // Add MyFragment to the activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MyFragment())
                .commit();
    }

    // Expose modelPath to the fragment
    public String getModelPath() {
        return modelPath;
    }
}
