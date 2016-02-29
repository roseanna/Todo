package com.example.roseanna.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, Serializable {
    public TaskAdapter todoAdapter;
    public ListView todoLV;

    public ArrayList<ToDo> tasks = new ArrayList<>();

    public Button addButton, delButton, clearButton, showButton, editButton;

    public EditText newTask;
    public String filename;
    public boolean returned = false;
    public String newTitle;
    public int oldPosition;
    public String newDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoLV = (ListView) findViewById(R.id.todoLV);
        newTask = (EditText) findViewById(R.id.newTaskField);

        addButton = (Button) findViewById(R.id.addButton);
        delButton = (Button) findViewById(R.id.deleteButton);
        editButton = (Button) findViewById(R.id.editButton);
        showButton = (Button) findViewById(R.id.showButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        filename = "Tasks";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                addClick();
                break;
            case R.id.deleteButton:
                deleteClick();
                break;
            case R.id.clearButton:
                clearClick();
                break;
            case R.id.showButton:
                showClick();
                break;
            case R.id.editButton:
                editClick();
                break;
        }
        clearSelect();
    }

    public void editClick(){
        oldPosition = 0;
        ArrayList<ToDo> clicked = new ArrayList<ToDo>();
        for (int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).isSelected()){
                oldPosition = i;
                Log.i("OLD POS", String.valueOf(oldPosition));
                clicked.add(tasks.get(i));
            }
        }
        if (clicked.size() != 1) {
            Toast.makeText(MainActivity.this, "CHOOSE EXACTLY ONE TASK!", Toast.LENGTH_SHORT).show();
            oldPosition = 0;
            return;
        }
        ToDo toEdit = clicked.get(0);
        Intent showActivity = new Intent(MainActivity.this, EditActivity.class);
        Bundle myBundle     = new Bundle();
        myBundle.putString("title", toEdit.getTitle());
        myBundle.putString("description", toEdit.getDescription());

        showActivity.putExtras(myBundle);
        startActivityForResult(showActivity, 200);
    }
    public void clearSelect(){
        for(ToDo i: tasks){
            i.unset();
        }
    }
    public void showClick() {
        ArrayList<ToDo> clicked = new ArrayList<ToDo>();
        for(ToDo t : tasks){
            if(t.isSelected()){
                clicked.add(t);
            }
        }
        if (clicked.size() != 1) {
            Toast.makeText(MainActivity.this, "CHOOSE EXACTLY ONE TASK!", Toast.LENGTH_SHORT).show();
            return;
        }

        ToDo t              = clicked.get(0);
        Intent showActivity = new Intent(MainActivity.this, ShowActivity.class);
        Bundle myBundle     = new Bundle();
        myBundle.putString("title", t.getTitle());
        myBundle.putString("description", t.getDescription());

        showActivity.putExtras(myBundle);
        startActivityForResult(showActivity, 100);
    }
    public void clearClick() {
        tasks.clear();
        todoAdapter.notifyDataSetChanged();
    }
    public void deleteClick() {
        Log.i("DELETE TASK", tasks.toString());
        ArrayList<ToDo> rem = new ArrayList<ToDo>();
        for (ToDo t : tasks) {
            if (t.isSelected())
                rem.add(t);
        }
        for (ToDo a : rem)
            tasks.remove(a);
        if (rem.size() == 0) {
            Toast.makeText(MainActivity.this, "Choose something to delete", Toast.LENGTH_SHORT).show();
        }
        todoAdapter.notifyDataSetChanged();
    }
    public void addClick() {
        Log.i("ADD TASK", tasks.toString());
        String input = newTask.getText().toString();
        if (!input.isEmpty()) {
            tasks.add(new ToDo(input, " "));
            newTask.setText("");
            todoAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, "Add a task!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Object o = load(this, filename);

        if (o != null && o instanceof ArrayList)
            tasks = (ArrayList<ToDo>) o;
        else
            tasks = new ArrayList<ToDo>();

        todoAdapter = new TaskAdapter(this, tasks);
        todoLV.setAdapter(todoAdapter);

        addButton.setOnClickListener(this);
        delButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        save(this, filename, tasks);
    }
    public static void save(Context context, String fileName, Object obj) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);
            oos.close();
        } catch (Exception e) {
            Log.e("A", "EXCEPTION: " + e.getMessage());
        }
    }
    public static Object load(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            Log.e("B", "EXCEPTION: " + e.getMessage());
            return null;
        }
    }

    public void onResume(){
        super.onResume();
        if (returned){
            returned = false;
            Log.i("onREsume", String.valueOf(oldPosition));
            ToDo temp = tasks.get(oldPosition);
            temp.setDescription(newDesc);
            temp.setTitle(newTitle);
            todoAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100) {
                Log.i("HERE FROM SHOW", "!");
            }
            else if(requestCode == 200){
                Bundle dataBundle   = data.getExtras();
                newTitle            = dataBundle.getString("newTitle");
                newDesc             = dataBundle.getString("newDesc");
                returned = true;
            }
        } catch (Exception e) {
            Log.i("ERROR", String.valueOf(requestCode));
        }
    }
}