package com.example.stirlingnumbers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class Calculate_answer extends AppCompatActivity {
    private TextView answer;
    private Stirling_calc calc;
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
        calc = (Stirling_calc) getLastNonConfigurationInstance();
        if (calc == null) {

            calc = new Stirling_calc();
            calc.execute(n, k);
        }
        calc.link(this);
    }

    public Object onRetainCustomNonConfigurationInstance() {
        if (calc != null) {
            calc.unLink();
        }
        return calc;
    }

    static class Stirling_calc extends AsyncTask<Integer, Integer, Integer> {

        Calculate_answer activity;

        void link(Calculate_answer act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }

        @Override
        protected Integer doInBackground(Integer... n_k) {
            try {
                int counter = 0;
                for (int i = 0; i < 10; ++i) {
                    publishProgress(++counter);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return Stirling(n_k[0], n_k[1]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            activity.answer.setText(result.toString());
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0]==10)
                activity.mHorizontalProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }

        private int Stirling(int n, int k) {
            if (n == k) return 1;
            else if (k == 0) return 0;
            else
                return Stirling(n - 1, k - 1) + k * Stirling(n - 1, k);
        }
    }
}
