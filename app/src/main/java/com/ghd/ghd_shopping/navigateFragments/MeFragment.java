package com.ghd.ghd_shopping.navigateFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghd.ghd_shopping.*;
import com.ghd.ghd_shopping.javaBean.User;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import java.io.BufferedReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    LinearLayout ll_order = null;
    View layout = null;
    SmartImageView user_image = null;
    TextView user_name = null;
    TextView user_email = null;
    ImageView imageView_login = null;
    Button btn_out = null;
    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_me, container, false);
        initView();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = layout.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "未登录");
        String image = sharedPreferences.getString("image", "");
        String email = sharedPreferences.getString("email","邮箱");
        user_name.setText(name);
        user_name.getPaint().setFakeBoldText(true);
        if(image.equals("")){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image_default);
            user_image.setImageBitmap(bitmap);
        }else{
            user_image.setImageUrl(image);
        }
        user_email.setText(email);
        user_email.getPaint().setFakeBoldText(true);
    }

    private void initView() {
        sharedPreferences = layout.getContext().getSharedPreferences("user", layout.getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ll_order = layout.findViewById(R.id.ll_order);
        user_image = layout.findViewById(R.id.image_me);
        user_name = layout.findViewById(R.id.tv_user_name);
        user_email = layout.findViewById(R.id.tv_user_email);
        btn_out = layout.findViewById(R.id.btn_out);
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = sharedPreferences.getInt("id", 1);
                String name = sharedPreferences.getString("name", "");
                String password = sharedPreferences.getString("password", "");
                String image = sharedPreferences.getString("image", "");
                User user = new User(id, name, password, image);
                if ("".equals(user.getName())) {
                    Toast.makeText(layout.getContext(), "抱歉，请您先登录！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(layout.getContext(), OrderActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = layout.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(layout.getContext(),MainActivity.class);
                intent.putExtra("fragment_flag",4);
                Toast.makeText(layout.getContext(),"您已退出登录！",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        imageView_login = layout.findViewById(R.id.image_login);
        imageView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layout.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
