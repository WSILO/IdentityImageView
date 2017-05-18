package com.atguigu.maxwu.selfcustomerviewexer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int angle = 30;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private IdentityImageView iiv;
    private int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        iiv = (IdentityImageView) findViewById(R.id.iiv);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bt1) {
            iiv.getBigImageView().setImageResource(R.drawable.guojia);
        } else if (v == bt2) {
            iiv.getSmallImageView().setImageResource(R.drawable.v2);
        } else if (v == bt3) {
            iiv.setRadiusScale(0.2f);
        } else if (v == bt4) {
            iiv.setBorderWidth(60);
            iiv.setBorderColor(R.color.colorTest);
        } else if (v == bt5) {
            iiv.setAngle(angle += 5);
        } else if (v == bt6) {
            iiv.setIsProgressbar(true);
            iiv.setProgressColor(R.color.colorAccent);
            iiv.setProgress(i += 10);
        }
    }

}
