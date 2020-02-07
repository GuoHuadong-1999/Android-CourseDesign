package com.ghd.ghd_shopping;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghd.ghd_shopping.navigateFragments.CartFragment;
import com.ghd.ghd_shopping.navigateFragments.ClassicFragment;
import com.ghd.ghd_shopping.navigateFragments.MainFragment;
import com.ghd.ghd_shopping.navigateFragments.MeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected LinearLayout mMenuMain;
    protected LinearLayout mMenuMe;
    protected LinearLayout mMenuCart;
    protected LinearLayout mMenuClassic;
    //定义图标
    protected ImageView ImageMain;
    protected ImageView ImageMe;
    protected ImageView ImageCart;
    protected ImageView ImageClassic;
    //定义Fragment
    protected MainFragment mainFragment = new MainFragment();
    protected MeFragment meFragment = new MeFragment();
    protected CartFragment cartFragment = new CartFragment();
    protected ClassicFragment classicFragment = new ClassicFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //获取管理类
        getSupportFragmentManager().beginTransaction().add(R.id.container_content, mainFragment)   //增加容器里面的fragment
                .add(R.id.container_content, meFragment)
                .hide(meFragment)               //隐藏
                .add(R.id.container_content, cartFragment)
                .hide(cartFragment)   //隐藏
                .add(R.id.container_content, classicFragment)
                .hide(classicFragment)   //隐藏
                .commit();  //提交
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        int fragmentFlag = intent.getIntExtra("fragment_flag",1);
        switch (fragmentFlag){
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(mainFragment)
                        .hide(meFragment)               //隐藏
                        .hide(cartFragment)   //隐藏
                        .hide(classicFragment)
                        .commit();
                ImageMain.setBackgroundResource(R.mipmap.nav_main_click);
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_normal);
                ImageMe.setBackgroundResource(R.mipmap.nav_me_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                intent.removeExtra("fragment_flag");
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(cartFragment)
                        .hide(mainFragment)
                        .hide(meFragment)
                        .hide(classicFragment)
                        .commit();
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_click);
                ImageMain.setBackgroundResource(R.mipmap.nav_main_normal);
                ImageMe.setBackgroundResource(R.mipmap.nav_me_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                intent.removeExtra("fragment_flag");
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(meFragment)
                        .hide(mainFragment)
                        .hide(cartFragment)   //隐藏
                        .hide(classicFragment)
                        .commit();
                ImageMe.setBackgroundResource(R.mipmap.nav_me_click);
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_normal);
                ImageMain.setBackgroundResource(R.mipmap.nav_main_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                intent.removeExtra("fragment_flag");
                break;
        }
    }

    //初始化
    private void initView() {
        mMenuMain = this.findViewById(R.id.menu_main);
        mMenuCart = this.findViewById(R.id.menu_cart);
        mMenuMe = this.findViewById(R.id.menu_me);
        mMenuClassic = this.findViewById(R.id.menu_classic);
        ImageMain = this.findViewById(R.id.image_main);
        ImageMe = this.findViewById(R.id.image_me);
        ImageCart = this.findViewById(R.id.image_cart);
        ImageClassic = this.findViewById(R.id.image_classic);
        mMenuMain.setOnClickListener(this);
        mMenuMe.setOnClickListener(this);
        mMenuCart.setOnClickListener(this);
        mMenuClassic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(mainFragment)
                        .hide(meFragment)               //隐藏
                        .hide(cartFragment)   //隐藏
                        .hide(classicFragment)
                        .commit();
                ImageMain.setBackgroundResource(R.mipmap.nav_main_click);
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_normal);
                ImageMe.setBackgroundResource(R.mipmap.nav_me_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                break;
            case R.id.menu_classic:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(classicFragment)
                        .hide(mainFragment)
                        .hide(meFragment)
                        .hide(cartFragment)
                        .commit();
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_click);
                ImageMain.setBackgroundResource(R.mipmap.nav_main_normal);
                ImageMe.setBackgroundResource(R.mipmap.nav_me_normal);
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_normal);
                break;
            case R.id.menu_cart:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(cartFragment)
                        .hide(mainFragment)
                        .hide(meFragment)
                        .hide(classicFragment)
                        .commit();
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_click);
                ImageMain.setBackgroundResource(R.mipmap.nav_main_normal);
                ImageMe.setBackgroundResource(R.mipmap.nav_me_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                break;
            case R.id.menu_me:
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(meFragment)
                        .hide(mainFragment)
                        .hide(cartFragment)   //隐藏
                        .hide(classicFragment)
                        .commit();
                ImageMe.setBackgroundResource(R.mipmap.nav_me_click);
                ImageCart.setBackgroundResource(R.mipmap.nav_cart_normal);
                ImageMain.setBackgroundResource(R.mipmap.nav_main_normal);
                ImageClassic.setBackgroundResource(R.mipmap.nav_classic_normal);
                break;
        }

    }
}
