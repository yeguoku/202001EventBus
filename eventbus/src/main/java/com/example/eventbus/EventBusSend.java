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

public class EventBusSend extends AppCompatActivity {

    private Button btn1_send, btn2_send;
    private TextView text_send;

    boolean isFist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);

        initView();
        inintData();
        inintListener();
    }

    private void inintListener() {
        //主线程发送数据
        btn1_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4发送消息，发送和接收的事件类型要一致
                EventBus.getDefault().post(new MessageEvent("主线程发送过来的数据"));
                finish();
            }
        });
        //接收粘性数据
        btn2_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFist) {
                    //让点击效果点击一次
                    isFist = false;
                    //4注册广播事件
                    EventBus.getDefault().register(EventBusSend.this);
                }
            }
        });
    }

    //3接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(StickyEvnet evnet) {
        //显示接收的消息
        text_send.setText(evnet.mag);
    }

    private void inintData() {
        setTitle("EventBus发送数据页面");
    }

    private void initView() {
        btn1_send = findViewById(R.id.btn1_send);
        btn2_send = findViewById(R.id.btn2_send);
        text_send = findViewById(R.id.text_send);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5解除广播事件，将所有的粘性事件移除
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSend.this);
    }
}
