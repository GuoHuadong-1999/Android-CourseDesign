package com.ghd.ghd_shopping;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghd.ghd_shopping.tools.MD5Util;
import com.ghd.ghd_shopping.tools.MyProperties;
import okhttp3.*;

import java.io.IOException;

public class RegistActivity extends AppCompatActivity {
    String verification;
    EditText et_name = null;
    EditText et_password = null;
    EditText et_confirm_password = null;
    EditText et_email = null;
    EditText et_verification = null;
    Button btn_verification = null;
    Button btn_regist = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();

    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        et_email = findViewById(R.id.et_email);
        et_verification = findViewById(R.id.et_verification);
        btn_verification = findViewById(R.id.btn_verification);
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient mOkHttpClient=new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", et_email.getText().toString())
                        .build();
                Request request = null;
                try {
                    request = new Request.Builder()
                            .url(MyProperties.getUrlProperty(RegistActivity.this)+"GetVerificationServlet")
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
                        verification = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"验证码已发送到您的邮箱！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        btn_regist = findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String confirm_password = et_confirm_password.getText().toString();
                String my_verification = et_verification.getText().toString();
                String email = et_email.getText().toString();
                if(!password.equals(confirm_password)||password.equals("")||confirm_password.equals("")){
                    Toast.makeText(RegistActivity.this,"抱歉，您的密码不符合要求，请重试！",Toast.LENGTH_SHORT).show();
                }else{
                    if(!my_verification.equals(verification)){
                        Toast.makeText(RegistActivity.this,"抱歉,验证码不正确，请重试！",Toast.LENGTH_SHORT).show();
                    }else{
                        OkHttpClient mOkHttpClient=new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("name", name)
                                .add("password", MD5Util.md5(password))
                                .add("email",email)
                                .build();
                        Request request = null;
                        try {
                            request = new Request.Builder()
                                    .url(MyProperties.getUrlProperty(RegistActivity.this)+"InsertUserServlet")
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegistActivity.this,"恭喜您，注册成功！",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        Intent intent = new Intent(RegistActivity.this,MainActivity.class);
                        intent.putExtra("fragment_flag",4);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
