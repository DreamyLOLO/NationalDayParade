package com.example.nationaldayparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etName, etYear;
    Button btnInsert, btnShow;
    RadioGroup rg1;
    RadioButton rbstars;
    int stars = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etYear = (EditText) findViewById(R.id.etYear);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnShow = (Button) findViewById(R.id.btnShow);
        rg1 = (RadioGroup) findViewById(R.id.rg1);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singer = etName.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                stars = rg1.getCheckedRadioButtonId();
                rbstars = (RadioButton) findViewById(stars);
                int stars = Integer.parseInt(rbstars.getText().toString());
                DBHelper dbh = new DBHelper(MainActivity.this);
                dbh.insertSong(title, singer, year, stars);
                dbh.close();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
