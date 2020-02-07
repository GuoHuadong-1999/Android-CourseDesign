package com.ghd.ghd_shopping;

import android.app.SearchableInfo;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {
    EditText etSearch = null;
    Button btnSearch = null;
    Drawable drawableSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        Intent intent = getIntent();
        String data = intent.getStringExtra("goodsInfo");
        etSearch.setText(data);

    }

    private void initView() {
        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);
        drawableSearch = (Drawable) getResources().getDrawable(R.drawable.search);
        drawableSearch.setBounds(0, 0, 80, 80);
        etSearch.setCompoundDrawables(drawableSearch, null, null, null);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,GoodsActivity.class);
                String goods_informarion = etSearch.getText().toString();
                intent.putExtra("goods_information",goods_informarion);
                startActivity(intent);
            }
        });
    }
}
