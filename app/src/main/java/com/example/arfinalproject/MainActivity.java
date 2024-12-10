package com.example.arfinalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private View lastClickedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up button click listeners
        findViewById(R.id.btnChristRedeemer).setOnClickListener(this::onLandmarkButtonClicked);
        findViewById(R.id.btnEiffelTower).setOnClickListener(this::onLandmarkButtonClicked);
        findViewById(R.id.btnPyramidMenkaure).setOnClickListener(this::onLandmarkButtonClicked);
        findViewById(R.id.btnStatueLiberty).setOnClickListener(this::onLandmarkButtonClicked);
        findViewById(R.id.btnTorrePisa).setOnClickListener(this::onLandmarkButtonClicked);
    }

    private void onLandmarkButtonClicked(View view) {
        // Save the clicked view to retry action after permission is granted
        lastClickedView = view;

        // Check if camera permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, proceed to ARActivity
            openARActivity(view);
        } else {
            // Request the camera permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private void openARActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ARActivity.class);

        // Determine which button was clicked and pass the corresponding data
        if (view.getId() == R.id.btnChristRedeemer) {
            intent.putExtra("modelPath", "christ_the_redeemer.glb");
            intent.putExtra("landmarkName", "Christ the Redeemer");
            intent.putExtra("landmarkDescription", "An iconic statue of Jesus Christ in Rio de Janeiro, Brazil. The statue was made between 1922 and 1931 and is 98 feet tall.");
        } else if (view.getId() == R.id.btnEiffelTower) {
            intent.putExtra("modelPath", "eiffel_tower.glb");
            intent.putExtra("landmarkName", "Eiffel Tower");
            intent.putExtra("landmarkDescription", "A wrought-iron lattice tower in Paris, France. It was made between 1887 and 1889 and is 1024 feet tall.");
        } else if (view.getId() == R.id.btnPyramidMenkaure) {
            intent.putExtra("modelPath", "pyramid_of_menkaure.glb");
            intent.putExtra("landmarkName", "Pyramid of Menkaure");
            intent.putExtra("landmarkDescription", "The smallest of the three pyramids of Giza, Egypt. It is 200 feet tall and is made of limestone and granite.");
        } else if (view.getId() == R.id.btnStatueLiberty) {
            intent.putExtra("modelPath", "statue_of_liberty.glb");
            intent.putExtra("landmarkName", "Statue of Liberty");
            intent.putExtra("landmarkDescription", "A colossal neoclassical sculpture on Liberty Island in New York City. It was made between 1876 and 1886 and is made of copper.");
        } else if (view.getId() == R.id.btnTorrePisa) {
            intent.putExtra("modelPath", "torre_pisa.glb");
            intent.putExtra("landmarkName", "Torre Pisa");
            intent.putExtra("landmarkDescription", "The famous leaning tower in Pisa, Italy. It was made between 1173 and 1372 and has a tilt of 3.97 degrees.");
        }

        // Start the ARActivity
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retry the last clicked action
                if (lastClickedView != null) {
                    openARActivity(lastClickedView);
                }
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // Permission denied, but user can be asked again
                Toast.makeText(this, "Camera permission is required to use AR features. Please allow it.", Toast.LENGTH_LONG).show();
            } else {
                // Permission denied and user checked "Don't ask again"
                Toast.makeText(this, "Permission denied. Please enable camera access in settings.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
