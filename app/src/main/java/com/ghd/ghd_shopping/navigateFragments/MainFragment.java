package com.ghd.ghd_shopping.navigateFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghd.ghd_shopping.GoodsDetailActivity;
import com.ghd.ghd_shopping.R;
import com.ghd.ghd_shopping.SearchActivity;
import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.tools.XmlParse;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.loopj.android.image.SmartImageView;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    final String TAG = "MainFragment";
    protected Drawable drawableSearch;
    protected EditText editTextSearch;
    protected ListView listView;
    protected View layout;
    protected List<Goods> goodsList = null;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 获取自己想要的控件
        layout = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        //1.获取OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.获取Request对象
        Request request = null;
        try {
            request = new Request.Builder().get().url(MyProperties.getUrlProperty(layout.getContext())+"GetGoodsServlet").build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.将Request封装为Call对象
        Call call = okHttpClient.newCall(request);
        //4.执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    goodsList = XmlParse.getGoodsList(response.body().string());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(new MyAdapter());
                        }
                    });
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
        return layout;
    }

    private void initView() {
        editTextSearch = (EditText) layout.findViewById(R.id.et_search);
        listView = (ListView) layout.findViewById(R.id.main_lv);
        drawableSearch = (Drawable) getResources().getDrawable(R.drawable.search);
        // 设置控件内容
        drawableSearch.setBounds(0, 0, 80, 80);
        editTextSearch.setCompoundDrawables(drawableSearch, null, null, null);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layout.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(layout.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goods_id",goodsList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

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

        @SuppressLint("DefaultLocale")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = View.inflate(layout.getContext(), R.layout.main_goods, null);
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
