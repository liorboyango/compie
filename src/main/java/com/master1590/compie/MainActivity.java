package com.master1590.compie;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText txt_x1;
    private EditText txt_y1;
    private EditText txt_x2;
    private EditText txt_y2;
    private EditText txt_x3;
    private EditText txt_y3;
    private FloatingActionButton fabSend;
    private HashMap<Integer, Point> triangle_points= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize view components
        initComponents();
    }

    private void initComponents(){
        txt_x1 = (EditText) findViewById(R.id.txt_x1);
        txt_x2 = (EditText) findViewById(R.id.txt_x2);
        txt_x3 = (EditText) findViewById(R.id.txt_x3);
        txt_y1 = (EditText) findViewById(R.id.txt_y1);
        txt_y2 = (EditText) findViewById(R.id.txt_y2);
        txt_y3 = (EditText) findViewById(R.id.txt_y3);

        fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput()) {
                    //input validation - ok

                    //clear previous points from the HashMap
                    triangle_points.clear();
                    //insert the new ones
                    triangle_points.put(1, new Point(Integer.valueOf(txt_x1.getText().toString()),
                            Integer.valueOf(txt_y1.getText().toString())));
                    triangle_points.put(2, new Point(Integer.valueOf(txt_x2.getText().toString()),
                            Integer.valueOf(txt_y2.getText().toString())));
                    triangle_points.put(3, new Point(Integer.valueOf(txt_x3.getText().toString()),
                            Integer.valueOf(txt_y3.getText().toString())));
                    //send the values to display activity
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("triangle_values", triangle_points);
                    startActivity(intent);
                }else{
                    //input validation - NOT ok
                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.parameters_missing),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkInput(){
        if(txt_x1.getText().toString().isEmpty()){
            txt_x1.requestFocus();
            return false;
        }
        if(txt_x2.getText().toString().isEmpty()){
            txt_x2.requestFocus();
            return false;
        }
        if(txt_x3.getText().toString().isEmpty()){
            txt_x3.requestFocus();
            return false;
        }
        if(txt_y1.getText().toString().isEmpty()){
            txt_y1.requestFocus();
            return false;
        }
        if(txt_y2.getText().toString().isEmpty()){
            txt_y2.requestFocus();
            return false;
        }
        if(txt_y3.getText().toString().isEmpty()){
            txt_y3.requestFocus();
            return false;
        }
        return true;
    }
}
