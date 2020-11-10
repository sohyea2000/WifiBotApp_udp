package com.example.wifibotapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    public String status;
    ImageView fwdBtn;
    ImageView backBtn;
    ImageView leftBtn;
    ImageView rightBtn;
    Button up;
    Button down;
    Button open;
    Button close;

    SeekBar seekBar;
    private Toolbar mToolbar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fwdBtn = findViewById(R.id.forward);
        backBtn = findViewById(R.id.backward);
        leftBtn = findViewById(R.id.left);
        rightBtn = findViewById(R.id.right);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        mToolbar = findViewById(R.id.mainAppBar);
        setSupportActionBar(mToolbar);
        seekBar = findViewById(R.id.seekbar);
        fwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "FORWARD";
                sendMessage(status);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "BACKWARD";
                sendMessage(status);
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "LEFT";
                sendMessage(status);
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "RIGHT";
                sendMessage(status);
            }
        });
       up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "UP";
                sendMessage(status);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "DOWN";
                sendMessage(status);
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "OPEN";
                sendMessage(status);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "CLOSE";
                sendMessage(status);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int i = seekBar.getProgress();
                status = Integer.toString(i);
                sendMessage(status);
                Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.line:
                status = "LF";
                sendMessage(status);

                return true;
            case R.id.wifi:
                status = "WB";
                sendMessage(status);

                return true;
            case R.id.ultra:
                status = "US";
                sendMessage(status);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void sendMessage(final String message) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            String stringData;

            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    // IP Address below is the IP address of that Device where server socket is opened.
                    InetAddress serverAddr = InetAddress.getByName("192.168.4.1");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr, 9001);
                    ds.send(dp);

                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    stringData = new String(lMsg, 0, dp.getLength());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        /*String s = mTextViewReplyFromServer.getText().toString();
                        if (stringData.trim().length() != 0)
                            mTextViewReplyFromServer.setText(s + "\nFrom Server : " + stringData);*/

                    }
                });
            }
        });

        thread.start();
    }
}

