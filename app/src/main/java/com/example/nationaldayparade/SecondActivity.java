package com.example.nationaldayparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    Button btnFilter;
    Spinner spin;
    ArrayList<Song> songs;
    SongAdapter aa;
    ArrayList<Integer> years;
    ArrayAdapter<Integer> sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView) findViewById(R.id.lv);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        spin = (Spinner) findViewById(R.id.spinner);

        DBHelper db = new DBHelper(SecondActivity.this);
        songs = db.getAllSongs();
        db.close();

        years = new ArrayList<Integer>();

        aa = new SongAdapter(this, R.layout.row, songs);
        lv.setAdapter(aa);
        sa = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spin.setAdapter(sa);

        for (Song i:songs) {
            if (!years.contains(i.getYear()))
                years.add(i.getYear());
        }

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer year = years.get(position);
                DBHelper db = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(db.getSongFromYear(year));
                db.close();
                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song target = songs.get(position);
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("data", target);
                startActivityForResult(i, 9);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(db.getAll5Stars());
                db.close();
                aa.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(SecondActivity.this);
            songs.clear();
            songs.addAll(db.getAllSongs());
            db.close();
            aa.notifyDataSetChanged();
            refresh();
        }
    }

    public void refresh() {
        years.clear();
        for (Song i:songs) {
            if (!years.contains(i.getYear()))
                years.add(i.getYear());
        }
        sa.notifyDataSetChanged();
    }
}
