package com.example.lineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lineapp.lineapp1.Activiyapp1;
import com.example.lineapp.lineapp2.Activiyapp2;
import com.example.lineapp.lineapp3.Activiyapp3;
//import com.example.lineapp.dimapp2.Activiyapp2;


//import com.example.dimappfinal.Ejercicio_2.Activity2;
//import com.example.dimappfinal.Ejercicio_3.Activity3;
//import com.example.dimappfinal.Ejercicio_4.Activity4;
//import com.example.dimappfinal.Ejercicio_5.Activity5;
//import com.example.dimappfinal.Entregable.Activity6;


public class MainActivity extends  AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activiyapp1.class));
            }
        });

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activiyapp2.class));
            }
        });
        Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activiyapp3.class));
            }
        });


    }
}
