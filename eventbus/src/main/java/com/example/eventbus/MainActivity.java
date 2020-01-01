package com.example.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eventbus.event.MessageEvent;
import com.example.eventbus.event.StickyEvnet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        inintData();
        inintListener();
    }

    private void inintListener() {
        //跳转到发送页面
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventBusSend.class);
                startActivity(intent);
            }
        });
        //发送粘性事件跳转到发送页面
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //2发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvnet("我是粘性事件"));
                //页面跳转
                Intent intent = new Intent(MainActivity.this, EventBusSend.class);
                startActivity(intent);
            }
        });
    }

    private void inintData() {
        setTitle("EventBus");

        //1 注册事件
        EventBus.getDefault().register(MainActivity.this);
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        text = findViewById(R.id.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //2 解除事件
        EventBus.getDefault().unregister(MainActivity.this);
    }

    //5接收消息
    //在主线程中接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent event) {
        text.setText(event.name);
    }

}
