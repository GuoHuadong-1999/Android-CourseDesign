package com.ghd.ghd_shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghd.ghd_shopping.javaBean.User;
import com.ghd.ghd_shopping.tools.MD5Util;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.ghd.ghd_shopping.tools.XmlParse;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    TextView tv_regist = null;
    Button btn_login = null;
    EditText et_name = null;
    EditText et_password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_regist = findViewById(R.id.tv_regist);
        tv_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.btn_login);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                OkHttpClient mOkHttpClient=new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("password", MD5Util.md5(password))
                        .build();
                Request request = null;
                try {
                    request = new Request.Builder()
                            .url(MyProperties.getUrlProperty(LoginActivity.this)+"LoginServlet")
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
                        if(message.equals("登录失败")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"抱歉，用户名或密码错误！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("fragment_flag",4);
                            User user = null;
                            try {
                                user = XmlParse.getUser(message);
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            }
                            System.out.println(user);
                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("id", user.getId());
                            editor.putString("name", user.getName());
                            editor.putString("password", user.getPassword());
                            editor.putString("image", user.getImage());
                            System.out.println("---------------------------->"+user.getImage());
                            editor.putString("email",user.getEmail());
                            editor.commit();
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}
