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
    int[] numPass,shaPass,colPass;
    String[] shaArraySpinner = new String[]{"Square","Rectangle","Circle","Triangle"};
    String[] colArraySpinner = new String[]{"Blue","Red","Yellow","Gray","Green","Black","White","Cyan"};
    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shaArraySpinner);
    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colArraySpinner);
    Spinner shaS1 = (Spinner) findViewById(R.id.Sha1);
    Spinner shaS2 = (Spinner) findViewById(R.id.Sha2);
    Spinner shaS3 = (Spinner) findViewById(R.id.Sha3);
    Spinner shaS4 = (Spinner) findViewById(R.id.Sha4);
    Spinner colS1 = (Spinner) findViewById(R.id.Col1);
    Spinner colS2 = (Spinner) findViewById(R.id.Col2);
    Spinner colS3 = (Spinner) findViewById(R.id.Col3);
    Spinner colS4 = (Spinner) findViewById(R.id.Col4);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
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
        shaS1.setAdapter(adapter1);
        shaS2.setAdapter(adapter1);
        shaS3.setAdapter(adapter1);
        shaS4.setAdapter(adapter1);
        colS1.setAdapter(adapter2);
        colS2.setAdapter(adapter2);
        colS3.setAdapter(adapter2);
        colS4.setAdapter(adapter2);

    }

    public void setPassword(View v){

        CheckBox nC = (CheckBox)findViewById(R.id.NumPass);
        CheckBox sC = (CheckBox)findViewById(R.id.ShapePass);
        CheckBox cC = (CheckBox)findViewById(R.id.ColourPass);

        char[] temp = (((EditText)findViewById(R.id.NumDigit)).toString()).toCharArray();
        int[] tempArray = new int[3];
        int i = 0;
        for(char c : temp){
            tempArray[i] = Character.getNumericValue(c);
            i++;
        }
        numPass = tempArray;

        shaPass = new int[]{shaS1.getSelectedItemPosition(),shaS2.getSelectedItemPosition(),shaS3.getSelectedItemPosition(),shaS4.getSelectedItemPosition()};
        colPass = new int[]{colS1.getSelectedItemPosition(),colS2.getSelectedItemPosition(),colS3.getSelectedItemPosition(),colS4.getSelectedItemPosition()};
        PasswordWhole transit = new PasswordWhole(numPass,shaPass,colPass,nC.isChecked(),sC.isChecked(),cC.isChecked());
        Intent intent = new Intent(this,CheckPassword.class);
        intent.putExtra("password",transit);
        startActivity(intent);
    }

}
