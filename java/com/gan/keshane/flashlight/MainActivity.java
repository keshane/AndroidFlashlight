package com.gan.keshane.flashlight;


import android.hardware.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private enum FlashlightState {
        ON, OFF;
    }

    private FlashlightState flashlightState;
    private Button button;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = Camera.open(); // open first back-facing camera
        flashlightState = FlashlightState.OFF;
        button = (Button) findViewById(R.id.toggleButton);


        setButtonText();

    }

    public void toggleFlashlight(View view) {
        if (flashlightState == FlashlightState.ON) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            camera.setParameters(parameters);

            if (camera.getParameters().getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                flashlightState = FlashlightState.OFF;
                setButtonText();
            }
        }
        else {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

            camera.setParameters(parameters);

            if (camera.getParameters().getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                flashlightState = FlashlightState.ON;
                setButtonText();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (camera == null) {
            camera = Camera.open();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (camera != null)
            camera.release();

        flashlightState = FlashlightState.OFF;
        setButtonText();
        camera = null;
    }

    private void setButtonText() {
        if (flashlightState == FlashlightState.OFF) {
            button.setText(R.string.on_text);
        }
        else {
            button.setText(R.string.off_text);
        }
    }



}
