package com.example.nationaldayparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ThirdActivity extends AppCompatActivity {

    TextView tvID;
    EditText etSTitle, etSName, etSYear;
    RadioGroup rg2;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    RadioButton rbstars;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    int stars = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tvID = findViewById(R.id.tvID);
        etSTitle = findViewById(R.id.etSTitle);
        etSName = findViewById(R.id.etSName);
        etSYear = findViewById(R.id.etSYear);
        rg2 = findViewById(R.id.rg2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        rb1 = findViewById(R.id.radioButton6);
        rb2 = findViewById(R.id.radioButton7);
        rb3 = findViewById(R.id.radioButton8);
        rb4 = findViewById(R.id.radioButton9);
        rb5 = findViewById(R.id.radioButton10);
        ArrayList<RadioButton> rbList = new ArrayList<>(Arrays.asList(rb1, rb2, rb3, rb4, rb5));

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");
        tvID.setText(data.getId() + "");
        etSTitle.setText(data.getTitle());
        etSName.setText(data.getSinger());
        etSYear.setText(data.getYear() + "");
        rbList.get(data.getStars()-1).setChecked(true);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                String title = etSTitle.getText().toString();
                String name = etSName.getText().toString();
                int year = Integer.parseInt(etSYear.getText().toString());
                stars = rg2.getCheckedRadioButtonId();
                rbstars = (RadioButton) findViewById(stars);
                int stars = Integer.parseInt(rbstars.getText().toString());
                Song newsong = new Song(data.getId(), title, name, year, stars);
                dbh.updateSong(newsong);
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
