package com.ghd.ghd_shopping.navigateFragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghd.ghd_shopping.R;
import com.ghd.ghd_shopping.SearchActivity;
import com.ghd.ghd_shopping.javaBean.Info;
import com.ghd.ghd_shopping.tools.InfoService;

import java.io.InputStream;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassicFragment extends Fragment {
    protected Drawable drawableSearch;
    protected EditText editTextSearch;
    protected ListView lv_label;
    protected ListView lv_information;
    protected View layout;
    protected int cPosition = 0;
    protected String[] labels = {"推荐分类", "京东超市", "国际名牌", "奢侈品", "京东国际",
            "唯品会", "男装", "女装", "男鞋", "女鞋", "内衣配饰", "箱包手袋", "美妆护肤",
            "个护清洁", "钟表珠宝", "手机数码", "电脑办公", "家用电器", "食品生鲜", "酒水饮料",
            "母婴童装", "玩具乐器", "医药保健", "计生情趣", "运动户外", "汽车生活", "家居厨具",
            "家居家装", "礼品鲜花", "宠物生活", "生活旅行", "图书文娱", "艺术邮币", "农资园艺",
            "特产馆", "京东金融", "拍卖", "房产", "二手商品", "京东服务", "工业品"};
    protected List<List<Info>> infos;
    protected MyLabelAdapter myLabelAdapter = new MyLabelAdapter();

    public ClassicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 获取自己想要的控件
        layout = inflater.inflate(R.layout.fragment_classic, container, false);
        initView();
        InputStream is = layout.getContext().getResources().openRawResource(R.raw.classic_informations);
        try {
            infos = InfoService.getInfosFromXML(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    private void initView() {
        editTextSearch = (EditText) layout.findViewById(R.id.et_search);
        drawableSearch = (Drawable) getResources().getDrawable(R.drawable.search);
        lv_label = (ListView) layout.findViewById(R.id.classic_lv_label);
        lv_information = (ListView) layout.findViewById(R.id.classic_lv_information);
        // 设置控件内容
        drawableSearch.setBounds(0, 0, 80, 80);
        editTextSearch.setCompoundDrawables(drawableSearch, null, null, null);
        lv_label.setAdapter(myLabelAdapter);
        // 设置监听器
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layout.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        lv_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cPosition = position;
                myLabelAdapter.changeSelected(position);
                lv_information.setAdapter(new MyInformationAdapter());
            }
        });
        lv_label.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myLabelAdapter.changeSelected(position);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class MyLabelAdapter extends BaseAdapter {
        int mSelect = 0; //选中项

        public void changeSelected(int positon) { //刷新方法
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return labels.length;
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
//            if(convertView==null){
            View view = View.inflate(layout.getContext(), R.layout.classic_label, null);
            TextView tv = view.findViewById(R.id.classic_tv_label);
            tv.setText(labels[position]);
            TextView flag = view.findViewById(R.id.classic_flag);
//            }else{
//                tv = (TextView) convertView;
//                tv.setText(labels[position]);
//            }
            if (mSelect == position) {
                tv.setBackgroundResource(R.color.colorGrayBackground);  //选中项背景
                tv.setTextColor(layout.getResources().getColor(R.color.colorBlackFont));
                tv.getPaint().setFakeBoldText(true);
                lv_information.setAdapter(new MyInformationAdapter());
                flag.setVisibility(View.VISIBLE);
            } else {
                tv.setBackgroundResource(R.color.colorWhiteBackground);  //其他项背景
                tv.setTextColor(layout.getResources().getColor(R.color.colorGrayFont));
            }
            return view;
        }
    }

    class MyInformationAdapter extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return infos.get(cPosition).size();
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
            View view = View.inflate(layout.getContext(), R.layout.classic_information, null);
            TextView textView = view.findViewById(R.id.classic_tv_information_label);
            LinearLayout linearLayout = view.findViewById(R.id.classic_ll_content);
            textView.setText(infos.get(cPosition).get(position).getLabel());
            textView.getPaint().setFakeBoldText(true);
            List<String> names = infos.get(cPosition).get(position).getName();
            for (int i = 0; i < names.size() / 3; i++) {
                View view1 = View.inflate(layout.getContext(), R.layout.classic_information_name, null);
                TextView textView01 = view1.findViewById(R.id.classic_tv_information_name01);
                TextView textView02 = view1.findViewById(R.id.classic_tv_information_name02);
                TextView textView03 = view1.findViewById(R.id.classic_tv_information_name03);
                textView01.setOnClickListener(this);
                textView02.setOnClickListener(this);
                textView03.setOnClickListener(this);
                textView01.setText(names.get(i * 3));
                textView02.setText(names.get(i * 3 + 1));
                textView03.setText(names.get(i * 3 + 2));
                linearLayout.addView(view1);
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(layout.getContext(), SearchActivity.class);
            intent.putExtra("goodsInfo", ((TextView) v).getText().toString().trim());
            startActivity(intent);
        }
    }

}
