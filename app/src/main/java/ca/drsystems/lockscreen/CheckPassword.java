package ca.drsystems.lockscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
        output = new ArrayList<>(4);
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
            GradientDrawable gD = new GradientDrawable();
            gD.setCornerRadius(100);
            switch (displayArray[i].getShape()) {
                case 0:
                    addButton.setWidth(100);
                    addButton.setHeight(100);//square
                case 1:
                    addButton.setWidth(200);
                    addButton.setHeight(100);//rectangle
                case 2:
                    addButton.setWidth(100);
                    addButton.setHeight(100);
                    addButton.setBackgroundDrawable(gD);
                case 3:
                    addButton.setWidth(100);
                    addButton.setHeight(60);
                    addButton.setBackgroundDrawable(gD);
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
        if (output.size() == 4) {
            PasswordBlock[] out = new PasswordBlock[4];

            for (int i = 0;i<out.length;i++) {
                out[i] = output.get(i);
            }

            if (superSecret.checkIt(out)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public PasswordBlock GenerateBlock() {
        int tempNumeric = 0;
        int tempShape = 0;
        int tempColour = 0;
        if (numericList.isEmpty()) {
            numericList.addAll(Arrays.asList(numericOptions));
        } else {
            tempNumeric = numericList.remove(rand.nextInt(numericList.size()));
        }
        if (shapeList.isEmpty()) {
            shapeList.addAll(Arrays.asList(shapeOptions));
        } else {
            tempShape = shapeList.remove(rand.nextInt(shapeList.size()));
        }
        if (colourList.isEmpty()) {
            colourList.addAll(Arrays.asList(colourOptions));
        } else {
            tempColour = colourList.remove(rand.nextInt(colourList.size()));
        }
        PasswordBlock block = new PasswordBlock(tempNumeric, tempShape, tempColour);

        return block;
    }

}
