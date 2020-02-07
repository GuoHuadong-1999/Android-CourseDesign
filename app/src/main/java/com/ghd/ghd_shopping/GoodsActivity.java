package com.ghd.ghd_shopping;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.ghd.ghd_shopping.tools.XmlParse;
import com.loopj.android.image.SmartImageView;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class GoodsActivity extends AppCompatActivity {
    ListView lv_goods_content = null;
    List<Goods> goodsList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String goods_information = intent.getStringExtra("goods_information");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("goods_information", goods_information)
                .build();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(MyProperties.getUrlProperty(this) + "SelectGoodsServlet")
                    .post(formBody)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.body().string();
                try {
                    goodsList = XmlParse.getGoodsList(message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv_goods_content.setAdapter(new MyAdapter());
                        }
                    });
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        lv_goods_content = findViewById(R.id.lv_goods_content);
        lv_goods_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GoodsActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods_id",goodsList.get(position).getId());
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = View.inflate(GoodsActivity.this, R.layout.main_goods, null);
            }
            TextView tv_description = convertView.findViewById(R.id.tv_description);
            TextView tv_price = convertView.findViewById(R.id.tv_price);
            SmartImageView image_goods = convertView.findViewById(R.id.image_goods);

            tv_description.getPaint().setFakeBoldText(true);
            tv_price.getPaint().setFakeBoldText(true);
            tv_description.setText(goodsList.get(position).getDescription());
            tv_price.setText(String.format("%.2f", goodsList.get(position).getPrice()));
            image_goods.setImageUrl(goodsList.get(position).getImage());
            return convertView;
        }
    }
}
