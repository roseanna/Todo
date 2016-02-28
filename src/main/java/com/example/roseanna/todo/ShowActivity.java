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

import java.io.Serializable;

/**
 * Created by roseanna on 2/28/16.
 */
public class ShowActivity extends AppCompatActivity implements View.OnClickListener{

    Button back;
    TextView title, desc;
    Intent myIntent;
    Bundle myBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity);

        title   = (TextView) findViewById(R.id.title);
        desc    = (TextView) findViewById(R.id.showTextView);
        back    = (Button) findViewById(R.id.back);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        String x = myBundle.getString("title");
        String y = myBundle.getString("description");
        Log.i(x, y);
        title.setText(x);
        desc.setText(y);

        back.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }
}
