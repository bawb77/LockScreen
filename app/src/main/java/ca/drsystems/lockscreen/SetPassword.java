package ca.drsystems.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class SetPassword extends AppCompatActivity {
    int[] numPass, shaPass, colPass;
    Spinner shaS1, shaS2, shaS3, shaS4, colS1, colS2, colS3, colS4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        String[] shaArraySpinner = new String[]{"Square", "Rectangle", "Circle", "Triangle"};
        String[] colArraySpinner = new String[]{"Blue", "Red", "Yellow", "Gray", "Green", "Black", "White", "Cyan"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shaArraySpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colArraySpinner);
        shaS1 = (Spinner) findViewById(R.id.Sha1);
        shaS2 = (Spinner) findViewById(R.id.Sha2);
        shaS3 = (Spinner) findViewById(R.id.Sha3);
        shaS4 = (Spinner) findViewById(R.id.Sha4);
        colS1 = (Spinner) findViewById(R.id.Col1);
        colS2 = (Spinner) findViewById(R.id.Col2);
        colS3 = (Spinner) findViewById(R.id.Col3);
        colS4 = (Spinner) findViewById(R.id.Col4);
        shaS1.setAdapter(adapter1);
        shaS2.setAdapter(adapter1);
        shaS3.setAdapter(adapter1);
        shaS4.setAdapter(adapter1);
        colS1.setAdapter(adapter2);
        colS2.setAdapter(adapter2);
        colS3.setAdapter(adapter2);
        colS4.setAdapter(adapter2);
    }

    public void setPassword(View v) {
        CheckBox nC = (CheckBox) findViewById(R.id.NumPass);
        CheckBox sC = (CheckBox) findViewById(R.id.ShapePass);
        CheckBox cC = (CheckBox) findViewById(R.id.ColourPass);

        char[] temp = ((findViewById(R.id.NumDigit)).toString()).toCharArray();
        int[] tempArray = new int[3];
        for (int i = 0; i < 3; i++) {
            tempArray[i] = Character.getNumericValue(temp[i]);
        }
        numPass = tempArray;

        shaPass = new int[]{shaS1.getSelectedItemPosition(), shaS2.getSelectedItemPosition(), shaS3.getSelectedItemPosition(), shaS4.getSelectedItemPosition()};
        colPass = new int[]{colS1.getSelectedItemPosition(), colS2.getSelectedItemPosition(), colS3.getSelectedItemPosition(), colS4.getSelectedItemPosition()};
        PasswordBlock[] packaging = new PasswordBlock[3];
        for(int i = 0; i < 4;i++)
        {
            PasswordBlock tempBlock = new PasswordBlock(numPass[i],shaPass[i],colPass[i]);
            packaging[i] = tempBlock;
        }
        PasswordWhole transit = new PasswordWhole(packaging, nC.isChecked(), sC.isChecked(), cC.isChecked());
        Intent intent = new Intent(this, CheckPassword.class);
        intent.putExtra("password", transit);
        startActivity(intent);
    }

}
