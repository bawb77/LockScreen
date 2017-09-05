package ca.drsystems.lockscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//Created by Ben Baxter

public class CheckPassword extends AppCompatActivity {
    Integer[] numericOptions = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Integer[] shapeOptions = new Integer[]{0, 1, 2};
    Integer[] colourOptions = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7};
    protected static final int passwordLength = 4;
    protected static final int gridSize = 28;
    PasswordWhole superSecret;
    ArrayList<Integer> numericList;
    ArrayList<Integer> shapeList;
    ArrayList<Integer> colourList;
    ArrayList<PasswordBlock> output;

    PasswordBlock[] displayArray = new PasswordBlock[gridSize];


    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        superSecret = getIntent().getParcelableExtra("password");
        numericList = new ArrayList<>();
        shapeList = new ArrayList<>();
        colourList = new ArrayList<>();

        output = new ArrayList<>(passwordLength);
        populateGrid();
    }

    public void populateGrid() {
        numericList.addAll(Arrays.asList(numericOptions));
        shapeList.addAll(Arrays.asList(shapeOptions));
        colourList.addAll(Arrays.asList(colourOptions));
        GridLayout gL = (GridLayout) findViewById(R.id.gL);
        gL.setColumnCount(passwordLength);
        for (int i = 0; i < displayArray.length; i++) {
            displayArray[i] = GenerateBlock();
            gL.addView(makeButton(i));
        }
    }

    public void checkPassword(PasswordBlock in) {
        output.add(in);
        if (output.size() == passwordLength) {
            PasswordBlock[] out = new PasswordBlock[passwordLength];

            for (int i = 0;i<out.length;i++) {
                out[i] = output.get(i);
            }

            if (superSecret.checkIt(out)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                output.clear();
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

        return new PasswordBlock(tempNumeric, tempShape, tempColour);
    }

    public Button makeButton(int i){
        Button addButton = new Button(this);

        GradientDrawable gradientDrawable = new GradientDrawable();
        switch (displayArray[i].getColour()) {
            case 0:
                gradientDrawable.setColor(Color.BLUE);
                break;
            case 1:
                gradientDrawable.setColor(Color.RED);
                break;
            case 2:
                gradientDrawable.setColor(Color.YELLOW);
                break;
            case 3:
                gradientDrawable.setColor(Color.GRAY);
                break;
            case 4:
                gradientDrawable.setColor(Color.GREEN);
                break;
            case 5:
                gradientDrawable.setColor(Color.BLACK);
                break;
            case 6:
                gradientDrawable.setColor(Color.WHITE);
                break;
            case 7:
                gradientDrawable.setColor(Color.CYAN);
                break;
            default:
                Log.v("color","default");
                break;
        }
        switch (displayArray[i].getShape()) {
            case 0:
                gradientDrawable.setShape(GradientDrawable.OVAL);
                break;//Oval
            case 1:
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;//Rectangle
            case 2:
                gradientDrawable.setShape(GradientDrawable.RING);
                break;//Circle
            case 3:
                gradientDrawable.setShape(GradientDrawable.LINE);
                break;//Line
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            addButton.setBackground(gradientDrawable);
        }
        addButton.setText(String.valueOf(displayArray[i].getNumeric()));
        final int transfer = i;
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPassword(displayArray[transfer]);
            }
        });
        return addButton;
    }



}
