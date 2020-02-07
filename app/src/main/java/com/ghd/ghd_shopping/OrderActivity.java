package com.ghd.ghd_shopping;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.javaBean.User;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.ghd.ghd_shopping.tools.XmlParse;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    ListView lv_goods_content = null;
    List<Goods> goodsList = null;
    TextView tv_user_name = null;
    TextView tv_user_email = null;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = sharedPreferences.getInt("id", 1);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        String image = sharedPreferences.getString("image", "");
        String email = sharedPreferences.getString("email", "");
        user = new User(id, name, password, image, email);
        try {
            getGoodsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGoodsList() throws IOException {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", String.valueOf(user.getId()))
                .build();
        Request request = new Request.Builder()
                .url(MyProperties.getUrlProperty(this) + "GetOrderServlet")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    goodsList = XmlParse.getGoodsList(response.body().string());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_user_name.setText(user.getName());
                        tv_user_email.setText(user.getEmail());
                        lv_goods_content.setAdapter(new MyAdapter());
                    }
                });
            }
        });
    }

    private void initView() {
        sharedPreferences = OrderActivity.this.getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_email = findViewById(R.id.tv_user_email);
        lv_goods_content = findViewById(R.id.lv_goods_content);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(goodsList==null){
                return 0;
            }else{
                return goodsList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(OrderActivity.this, R.layout.order_goods, null);
            }
            TextView tv_goods_name = convertView.findViewById(R.id.tv_goods_name);
            TextView tv_goods_price = convertView.findViewById(R.id.tv_goods_price);
            tv_goods_name.setText(goodsList.get(position).getName());
            tv_goods_price.setText(String.format("%.2f", goodsList.get(position).getPrice()));
            return convertView;
        }
    }
}
