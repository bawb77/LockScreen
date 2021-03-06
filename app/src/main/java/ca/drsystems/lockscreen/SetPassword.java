package ca.drsystems.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

//Created by Ben Baxter

public class SetPassword extends AppCompatActivity {
    int[] numericPassword, shapePassword, colourPassword;
    Spinner shapeSpinner1, shapeSpinner2, shapeSpinner3, shapeSpinner4, colourSpinner1, colourSpinner2, colourSpinner3, colourSpinner4;
    protected static final int passwordLength = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        String[] shaArraySpinner = new String[]{"Square", "Rectangle", "Circle", "Oval"};
        String[] colArraySpinner = new String[]{"Blue", "Red", "Yellow", "Gray", "Green", "Black", "White", "Cyan"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shaArraySpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colArraySpinner);
        shapeSpinner1 = (Spinner) findViewById(R.id.Sha1);
        shapeSpinner2 = (Spinner) findViewById(R.id.Sha2);
        shapeSpinner3 = (Spinner) findViewById(R.id.Sha3);
        shapeSpinner4 = (Spinner) findViewById(R.id.Sha4);
        colourSpinner1 = (Spinner) findViewById(R.id.Col1);
        colourSpinner2 = (Spinner) findViewById(R.id.Col2);
        colourSpinner3 = (Spinner) findViewById(R.id.Col3);
        colourSpinner4 = (Spinner) findViewById(R.id.Col4);
        shapeSpinner1.setAdapter(adapter1);
        shapeSpinner2.setAdapter(adapter1);
        shapeSpinner3.setAdapter(adapter1);
        shapeSpinner4.setAdapter(adapter1);
        colourSpinner1.setAdapter(adapter2);
        colourSpinner2.setAdapter(adapter2);
        colourSpinner3.setAdapter(adapter2);
        colourSpinner4.setAdapter(adapter2);
    }

    public void setPassword(View v) {
        CheckBox numericCheckBox = (CheckBox) findViewById(R.id.NumPass);
        CheckBox shapeCheckBox = (CheckBox) findViewById(R.id.ShapePass);
        CheckBox colourCheckBox = (CheckBox) findViewById(R.id.ColourPass);
        EditText editText = (EditText) findViewById(R.id.NumDigit);
        String numericTemp = editText.getText().toString();
        numericPassword = new int[numericTemp.length()];
        char[] temp = numericTemp.toCharArray();
        for (int i = 0; i < temp.length; i++) {
            numericPassword[i] = Character.getNumericValue(temp[i]);
        }
        shapePassword = new int[]{shapeSpinner1.getSelectedItemPosition(), shapeSpinner2.getSelectedItemPosition(), shapeSpinner3.getSelectedItemPosition(), shapeSpinner4.getSelectedItemPosition()};
        colourPassword = new int[]{colourSpinner1.getSelectedItemPosition(), colourSpinner2.getSelectedItemPosition(), colourSpinner3.getSelectedItemPosition(), colourSpinner4.getSelectedItemPosition()};
        PasswordBlock[] packaging = new PasswordBlock[passwordLength];
        for(int i = 0; i < packaging.length;i++)
        {
            PasswordBlock tempBlock = new PasswordBlock(numericPassword[i],shapePassword[i],colourPassword[i]);
            packaging[i] = tempBlock;
        }
        PasswordWhole transit = new PasswordWhole(packaging, numericCheckBox.isChecked(), shapeCheckBox.isChecked(), colourCheckBox.isChecked());
        Intent intent = new Intent(this, CheckPassword.class);
        intent.putExtra("password", transit);
        startActivity(intent);
    }

}
