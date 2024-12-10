package com.example.arfinalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.InputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class MyFragment extends ArFragment {

    private boolean sessionInitialized = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check ARCore availability
        if (!checkARCoreAvailability(context)) {
            return; // Exit if ARCore is not available
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeARSession();

        // Set up the listener for plane taps
        setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Log.d("MyFragment", "Plane tapped!");

            // Retrieve the model path from ARActivity
            ARActivity activity = (ARActivity) requireActivity();
            String modelPath = activity.getModelPath();

            if (modelPath == null || modelPath.isEmpty()) {
                Toast.makeText(getContext(), "Model path is not set!", Toast.LENGTH_SHORT).show();
                Log.e("MyFragment", "Model path is null or empty.");
                return;
            }

            Log.d("MyFragment", "Model path: " + modelPath);

            // Create an anchor and place the model
            Anchor anchor = hitResult.createAnchor();
            Log.d("MyFragment", "Anchor created at: " + anchor.getPose());
            placeModel(anchor, modelPath);
        });
    }

    private boolean checkARCoreAvailability(Context context) {
        switch (ArCoreApk.getInstance().checkAvailability(context)) {
            case SUPPORTED_INSTALLED:
                Log.d("MyFragment", "ARCore is installed and supported.");
                return true;

            case SUPPORTED_APK_TOO_OLD:
            case SUPPORTED_NOT_INSTALLED:
                Toast.makeText(context, "Please install or update ARCore.", Toast.LENGTH_LONG).show();
                return false;

            case UNSUPPORTED_DEVICE_NOT_CAPABLE:
                Toast.makeText(context, "This device does not support ARCore.", Toast.LENGTH_LONG).show();
                return false;

            default:
                Toast.makeText(context, "Unknown ARCore status.", Toast.LENGTH_LONG).show();
                return false;
        }
    }

    private void initializeARSession() {
        try {
            if (sessionInitialized) return;

            // Create AR session
            Session session = new Session(requireContext());

            // Configure AR session
            Config config = new Config(session);
            config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
            session.configure(config);

            // Attach the AR session to the ArFragment
            getArSceneView().setupSession(session);
            sessionInitialized = true;

            Log.d("MyFragment", "AR session successfully initialized.");
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to initialize AR session: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MyFragment", "AR session initialization error", e);
        }
    }

    private void placeModel(Anchor anchor, String modelPath) {
        Log.d("MyFragment", "Attempting to load model: " + modelPath);

        try {
            // Verify the file exists in the assets folder
            InputStream inputStream = requireContext().getAssets().open(modelPath);
            inputStream.close();
            Log.d("MyFragment", "Model file found: " + modelPath);
        } catch (Exception e) {
            Log.e("MyFragment", "Model file not found in assets: " + modelPath, e);
            Toast.makeText(requireContext(), "Model file not found: " + modelPath, Toast.LENGTH_LONG).show();
            return;
        }

        // Attempt to load the model
        ModelRenderable.builder()
                .setSource(requireContext(), Uri.parse(modelPath))
                .build()
                .thenAccept(renderable -> {
                    Log.d("MyFragment", "Model loaded successfully: " + modelPath);

                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setRenderable(renderable);
                    anchorNode.setParent(getArSceneView().getScene());
                })
                .exceptionally(throwable -> {
                    Log.e("MyFragment", "Failed to load model: " + throwable.getMessage(), throwable);
                    Toast.makeText(requireContext(), "Failed to load model: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    return null;
                });
    }

}
