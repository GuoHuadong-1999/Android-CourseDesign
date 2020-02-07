package com.ghd.ghd_shopping;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PaySuccessActivity extends AppCompatActivity {
    Button btn_back = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaySuccessActivity.this,MainActivity.class);
                intent.putExtra("fragment_flag",3);
                startActivity(intent);
            }
        });
    }
}
