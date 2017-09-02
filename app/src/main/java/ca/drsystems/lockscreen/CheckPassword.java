package ca.drsystems.lockscreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CheckPassword extends AppCompatActivity {
    Integer[] numericOptions = new Integer[]{0,1,2,3,4,5,6,7,8,9};
    Integer[] shapeOptions = new Integer[]{0,1,2};
    Integer[] colourOptions = new Integer[]{0,1,2,3,4,5,6,7};

    ArrayList<Integer> numericList = new ArrayList();
    ArrayList<Integer> shapeList = new ArrayList();
    ArrayList<Integer> colourList = new ArrayList();
    ArrayList<PasswordBlock> output = new ArrayList<>(3);

    PasswordBlock[] displayArray = new PasswordBlock[29];
    PasswordWhole superSecret = getIntent().getParcelableExtra("password");

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateGrid();
    }

    public void populateGrid(){

        numericList.addAll(Arrays.asList(numericOptions));
        shapeList.addAll(Arrays.asList(shapeOptions));
        colourList.addAll(Arrays.asList(colourOptions));

        for (int i = 0; i > displayArray.length;i++){
            displayArray[i] = GenerateBlock();
        }
        for (PasswordBlock b : displayArray){
            Button addButton = new Button(this);
            switch(b.getColour()){
                case 0: addButton.setBackgroundColor(Color.BLUE);
                case 1: addButton.setBackgroundColor(Color.RED);
                case 2: addButton.setBackgroundColor(Color.YELLOW);
                case 3: addButton.setBackgroundColor(Color.GRAY);
                case 4: addButton.setBackgroundColor(Color.GREEN);
                case 5: addButton.setBackgroundColor(Color.BLACK);
                case 6: addButton.setBackgroundColor(Color.WHITE);
                case 7: addButton.setBackgroundColor(Color.CYAN);
            }
            switch(b.getShape()){
                case 0:addButton.setWidth(100); addButton.setHeight(100);//square
                case 1:addButton.setWidth(200); addButton.setHeight(100);//rectangle
                case 2:addButton.setWidth(100); addButton.setHeight(100);//circle TBC
                case 3://triangle TBC
            }

            addButton.setText(b.getNumeric());
            addButton.setTag(b);
            GridLayout gL = (GridLayout)findViewById(R.id.gL);
            gL.addView(addButton);
        }
    }
    public void checkPassword(PasswordBlock in){
        output.add(in);
        if(!output.contains(null)){
            PasswordBlock[] out = new PasswordBlock[3];
            int i =0;
            for(PasswordBlock b : output){
                out[i] = b;
                i++;
            }

            if(superSecret.checkIt(out)){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public PasswordBlock GenerateBlock(){
        int num = 0;int sha = 0; int col = 0;
        if(numericList.isEmpty()){
            numericList.addAll(Arrays.asList(numericOptions));
        }
        else {
            num = numericList.remove(rand.nextInt(numericList.size() - 1));
        }
        if(shapeList.isEmpty()){
            shapeList.addAll(Arrays.asList(shapeOptions));
        }
        else {
            sha = shapeList.remove(rand.nextInt(shapeList.size() - 1));
        }
        if(colourList.isEmpty()){
            colourList.addAll(Arrays.asList(colourOptions));
        }
        else {
            col = colourList.remove(rand.nextInt(colourList.size() - 1));
        }
        PasswordBlock block = new PasswordBlock(num,sha,col);

        return block;
    }

}
