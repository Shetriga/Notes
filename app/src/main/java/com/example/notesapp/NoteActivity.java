package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    EditText editText;

    int pos;

    int replace;

    @Override
    public void onBackPressed() {

        if (replace == 1) {
            try {

                MainActivity.notes = (ArrayList<String>) ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {

                e.printStackTrace();

            }

            MainActivity.notes.set(pos, editText.getText().toString());
            MainActivity.newNotes.clear();

            try {

                MainActivity.sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();

            } catch (IOException e) {

                e.printStackTrace();

            }

            try {

                MainActivity.newNotes = (ArrayList<String>) ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {

                e.printStackTrace();

            }

//        MainActivity.ad.notifyDataSetChanged();
        } else if (replace == -1){

            try {

                MainActivity.notes = (ArrayList<String>) ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {

                e.printStackTrace();

            }

            MainActivity.notes.add(editText.getText().toString());

            try {

                MainActivity.sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();

            } catch (IOException e) {

                e.printStackTrace();

            }

            try {

                MainActivity.newNotes = (ArrayList<String>) ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.editText);

        Intent i = getIntent();
        String text = i.getStringExtra("text");

        pos = i.getIntExtra("pos", -1);

        replace = i.getIntExtra("replace", 0);
        Toast.makeText(this, "" + replace, Toast.LENGTH_SHORT).show();

        editText.setText(text);
    }
}