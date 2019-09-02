package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button start, pause, reset, lap;
    long milliSecondTime, startTime, timeBuff, updateTime = 0L;
    Handler handler;
    int second, minutes, milliSeconds;
    ListView listView;
    String[] listElements = new String[]{};
    List<String> listElementArrayList;
    ArrayAdapter<String> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        start = (Button) findViewById(R.id.button);
        pause = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);
        lap = (Button) findViewById(R.id.button4);
        listView = (ListView) findViewById(R.id.listView1);

        handler = new Handler();

        listElementArrayList = new ArrayList<String>(Arrays.asList(listElements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listElementArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += milliSecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milliSecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                second = 0;
                minutes = 0;
                milliSeconds = 0;

                textView.setText("00 : 00 : 00");
                listElementArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listElementArrayList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            milliSecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milliSecondTime;
            second = (int) (updateTime / 1000);
            minutes = second / 60;
            second = second % 60;
            milliSeconds = (int) (updateTime % 1000);
            textView.setText("" + minutes + ":" + String.format("%02d", second) + ":" + String.format("%03d", milliSeconds));
            handler.postDelayed(this, 0);
        }
    };
}
