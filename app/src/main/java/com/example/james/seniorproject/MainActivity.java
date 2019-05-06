package com.example.james.seniorproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Button controlButton;
    public Button manualButton;
    public Button cameraButton;
    public boolean extraRevealed = false;

    public LinearLayout linearScroll;
    public AssignmentTracker assignmentTracker = new AssignmentTracker(this);

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content_main);
        controlButton = (Button) findViewById(R.id.control_button);
        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExtraButtons();
            }
        });
        manualButton = (Button) findViewById(R.id.manual_button);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = assignmentTracker.addBlankAssignment(v.getContext(), MainActivity.this.linearScroll);
                assignmentTracker.createDialogBox(v.getContext(), id).show();
            }
        });
        cameraButton = (Button) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked camera", Toast.LENGTH_SHORT).show();
            }
        });
        linearScroll = (LinearLayout) findViewById(R.id.linear_scroll);
    }

    void toggleExtraButtons() {
        extraRevealed = !extraRevealed;
        manualButton.setVisibility(extraRevealed? View.VISIBLE : View.INVISIBLE);
        cameraButton.setVisibility(extraRevealed? View.VISIBLE : View.INVISIBLE);
    }
}
