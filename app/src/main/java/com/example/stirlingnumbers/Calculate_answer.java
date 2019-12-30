package com.example.stirlingnumbers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Calculate_answer extends AppCompatActivity {
    private TextView answer;
    private MyBroadcastReceiver mMyBroadcastReceiver;
    private UpdateBroadcastReceiver mUpdateBroadcastReceiver;
    private ProgressBar mHorizontalProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        int k, n;
        answer = findViewById(R.id.answer_number);
        mHorizontalProgressBar = findViewById(R.id.progress_horizontal);
        k = getIntent().getExtras().getInt("k");
        n = getIntent().getExtras().getInt("n");

        //инициализируем и запускаем сервис
        Intent intentMyIntentService = new Intent(this, CalcIntentService.class);
        startService(intentMyIntentService.putExtra("k", k).putExtra("n", n));

        //инициализируем ресиверы для получения промежуточных и финальных значений
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        mUpdateBroadcastReceiver = new UpdateBroadcastReceiver();

        //получаем финальное значение работы сервиса
        IntentFilter intentFilter = new IntentFilter(CalcIntentService.ACTION_CALCINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);

        //получаем промежуточные значения работы сервиса
        IntentFilter updateIntentFilter = new IntentFilter(CalcIntentService.ACTION_UPDATE);
        updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mUpdateBroadcastReceiver, updateIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    //метод для обработки конечного сигнала сервиса
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(CalcIntentService.EXTRA_KEY_OUT);
            answer.setText(result);
        }
    }

    //метод для обработки промежуточных сигналов сервиса
    public class UpdateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(CalcIntentService.EXTRA_KEY_UPDATE, 0) == 1)
                mHorizontalProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
