package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ListView listView;

    public static ArrayList<String> notes, newNotes;

    public static SharedPreferences sharedPreferences;

    public static ArrayAdapter ad;

    @Override
    protected void onResume() {

        //ad.notifyDataSetChanged();
        ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newNotes);
        listView.setAdapter(ad);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.addNote) {
            //Log.i("Item Selected:", "Add Note");
            Intent i = new Intent(getApplicationContext(), NoteActivity.class);
            i.putExtra("replace", -1);
            startActivity(i);

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        notes = new ArrayList<>();

        sharedPreferences = this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        //notes.add("Add a note..");

        /*try {

            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();

        } catch (IOException e) {

            e.printStackTrace();

        }*/

        try {

            newNotes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {

            e.printStackTrace();

        }

        ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newNotes);
        listView.setAdapter(ad);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete Item")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    MainActivity.notes = (ArrayList<String>) ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }

                                notes.remove(position);

                                try {

                                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }

                                try {

                                    newNotes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }

                                //ad.notifyDataSetChanged();
                                ad = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, newNotes);
                                listView.setAdapter(ad);

                            }
                        })
                        .setNegativeButton("No", null).show();

                return true;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //Toast.makeText(MainActivity.this, "Donee!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                        i.putExtra("text", listView.getItemAtPosition(position).toString());
                        i.putExtra("pos", position);
                        i.putExtra("replace", 1);
                        startActivity(i);

            }
        });

    }

    public void test (View view) {
        Intent i = new Intent(getApplicationContext(), NoteActivity.class);
        startActivity(i);
    }

}