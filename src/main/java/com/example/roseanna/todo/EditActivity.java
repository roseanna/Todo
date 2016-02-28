package com.example.roseanna.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by roseanna on 2/28/16.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    Button done;
    EditText title, desc;
    Intent myIntent;
    Bundle myBundle;
    String oldTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        title   = (EditText) findViewById(R.id.title);
        desc    = (EditText) findViewById(R.id.showTextView);
        done    = (Button) findViewById(R.id.done);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        oldTitle = myBundle.getString("title");
        String y = myBundle.getString("description");
        Log.i(oldTitle, y);
        title.setText(oldTitle);
        desc.setText(y);

        done.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        myBundle.putString("newTitle", String.valueOf(title.getText()));
        myBundle.putString("newDesc", String.valueOf(desc.getText()));
        myBundle.putString("oldTitle", oldTitle);
        myIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }
}
