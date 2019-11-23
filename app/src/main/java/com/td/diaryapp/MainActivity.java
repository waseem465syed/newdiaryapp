package com.td.diaryapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.td.diaryapp.MyLibraries.CommonRoutines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtMessage;
    private FloatingActionButton fab;

    private String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtMessage = findViewById(R.id.txtMessage);

        //set animation
        Animation animLeftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right_animation);
        txtMessage.setAnimation(animLeftToRight);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void startActivity(Class<?> cls){
        Intent i = new Intent(this, cls);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id)
        {
            case R.id.menu_openPhoneActivity:

                //check for permissions
                if (CommonRoutines.permissionGranted(this, Manifest.permission.READ_CONTACTS, "Reading Contacts")) {
                    startActivity(PhoneActivity.class);
                }
                else
                {
                    errorMessage = "Please enable read contacts permissions to use device";
                    CommonRoutines.displayMessage(this, fab, errorMessage, Snackbar.LENGTH_LONG, Toast.LENGTH_LONG);
                }
                break;

            case R.id.menu_openNewNoteActivity:
                startActivity(NewNoteActivity.class);
                break;

            case R.id.menu_openViewNotesActivity:
                startActivity(ViewNotesActivity.class);
                break;

            case R.id.action_settings:
                //do nothing
                break;
        }

        return true;
    }
}
