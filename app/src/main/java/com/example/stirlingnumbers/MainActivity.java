package com.example.stirlingnumbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText n, k;
    private Button b_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        k = (EditText) findViewById(R.id.k_number);
        n = (EditText) findViewById(R.id.n_number);
        b_start = (Button) findViewById(R.id.start);
        b_start.setOnClickListener(this);
    }
    //обрабатываем нажатие кнопки
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start) {
            Toast toast;
            if (!k.getText().toString().equals("") & !n.getText().toString().equals("") ) {
                if (Integer.parseInt(k.getText().toString()) <= Integer.parseInt(n.getText().toString()) &
                        Integer.parseInt(k.getText().toString()) >= 0 & Integer.parseInt(n.getText().toString()) >= 0) {
                    Intent intent = new Intent(MainActivity.this, Calculate_answer.class);
                    intent.putExtra("k", Integer.parseInt(k.getText().toString()));
                    intent.putExtra("n", Integer.parseInt(n.getText().toString()));
                    startActivity(intent);
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Incorrect input data", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                toast = Toast.makeText(getApplicationContext(), "No input data", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
