package ca.drsystems.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//Created by Ben Baxter

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextStep(View v) {
        Intent intent = new Intent(v.getContext(), SetPassword.class);
        startActivity(intent);
    }
}
