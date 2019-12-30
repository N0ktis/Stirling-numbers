package com.example.stirlingnumbers;

import android.app.IntentService;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

public class CalcIntentService extends IntentService {
    public static final String ACTION_CALCINTENTSERVICE = "com.example.stirlingnumbers.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String ACTION_UPDATE = "com.example.stirlingnumbers.UPDATE";
    public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";

    public CalcIntentService() {
        super("Calc_ans");
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int k = intent.getIntExtra("k", 0);
        int n = intent.getIntExtra("n", 0);


        Intent updateIntent = new Intent();
        updateIntent.setAction(ACTION_UPDATE);
        updateIntent.addCategory(Intent.CATEGORY_DEFAULT);


        /*
        Так как у меня вычисления идут рекурсивно, то я не вижу способа отслеживать прогресс
        выполнения вычислений в реальном времени, поэтому для демеонстрации много поточности
        была добавлена искусственная задержка в 3 секунды и progress bar(горизонтальный), который
        заканчивает свою работу при сигнале 1.
         */
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateIntent.putExtra(EXTRA_KEY_UPDATE, 1);

        //посылаем промежуточное значение
        sendBroadcast(updateIntent);


        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION_CALCINTENTSERVICE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(EXTRA_KEY_OUT, Integer.toString(Stirling(n, k)));

        //посылаем финальное значение
        sendBroadcast(responseIntent);
    }

    //функция вычисления числа стерлинга второго рода
    private int Stirling(int n, int k) {
        if (n == k) return 1;
        else if (k == 0) return 0;
        else
            return Stirling(n - 1, k - 1) + k * Stirling(n - 1, k);
    }
}
