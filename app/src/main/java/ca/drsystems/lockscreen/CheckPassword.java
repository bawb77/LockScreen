package ca.drsystems.lockscreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CheckPassword extends AppCompatActivity {
    Integer[] numericOptions = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Integer[] shapeOptions = new Integer[]{0, 1, 2};
    Integer[] colourOptions = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7};
    PasswordWhole superSecret;
    ArrayList<Integer> numericList;
    ArrayList<Integer> shapeList;
    ArrayList<Integer> colourList;
    ArrayList<PasswordBlock> output;

    PasswordBlock[] displayArray = new PasswordBlock[29];


    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        superSecret = getIntent().getParcelableExtra("password");
        numericList = new ArrayList();
        shapeList = new ArrayList();
        colourList = new ArrayList();
        output = new ArrayList<>(3);
        Log.v("debug", "process");
        populateGrid();
    }

    public void populateGrid() {

        numericList.addAll(Arrays.asList(numericOptions));
        shapeList.addAll(Arrays.asList(shapeOptions));
        colourList.addAll(Arrays.asList(colourOptions));
        Log.v("debug", "adding button 1");
        for (int i = 0; i < displayArray.length; i++) {
            Log.v("debug", "adding button 2");
            displayArray[i] = GenerateBlock();
            Button addButton = new Button(this);
            switch (displayArray[i].getColour()) {
                case 0:
                    addButton.setBackgroundColor(Color.BLUE);
                case 1:
                    addButton.setBackgroundColor(Color.RED);
                case 2:
                    addButton.setBackgroundColor(Color.YELLOW);
                case 3:
                    addButton.setBackgroundColor(Color.GRAY);
                case 4:
                    addButton.setBackgroundColor(Color.GREEN);
                case 5:
                    addButton.setBackgroundColor(Color.BLACK);
                case 6:
                    addButton.setBackgroundColor(Color.WHITE);
                case 7:
                    addButton.setBackgroundColor(Color.CYAN);
            }
            //GradientDrawable gD = new GradientDrawable();
            //gD.setCornerRadius(100);
            switch (displayArray[i].getShape()) {
                case 0:
                    addButton.setWidth(100);
                    addButton.setHeight(100);//square
                case 1:
                    addButton.setWidth(200);
                    addButton.setHeight(100);//rectangle
                case 2:
                    addButton.setWidth(100);
                    addButton.setHeight(100);//circle TBC
                case 3://triangle TBC
            }
            addButton.setText(String.valueOf(displayArray[i].getNumeric()));
            final int transfer = i;
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    checkPassword(displayArray[transfer]);
                }
            });
            GridLayout gL = (GridLayout) findViewById(R.id.gL);

            gL.addView(addButton);
        }
    }

    public void checkPassword(PasswordBlock in) {
        output.add(in);
        if (!output.contains(null)) {
            PasswordBlock[] out = new PasswordBlock[3];
            int i = 0;
            for (PasswordBlock b : output) {
                out[i] = b;
                i++;
            }

            if (superSecret.checkIt(out)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public PasswordBlock GenerateBlock() {
        int num = 0;
        int sha = 0;
        int col = 0;
        if (numericList.isEmpty()) {
            numericList.addAll(Arrays.asList(numericOptions));
        } else {
            num = numericList.remove(rand.nextInt(numericList.size()));
        }
        if (shapeList.isEmpty()) {
            shapeList.addAll(Arrays.asList(shapeOptions));
        } else {
            sha = shapeList.remove(rand.nextInt(shapeList.size()));
        }
        if (colourList.isEmpty()) {
            colourList.addAll(Arrays.asList(colourOptions));
        } else {
            col = colourList.remove(rand.nextInt(colourList.size()));
        }
        PasswordBlock block = new PasswordBlock(num, sha, col);

        return block;
    }

}
