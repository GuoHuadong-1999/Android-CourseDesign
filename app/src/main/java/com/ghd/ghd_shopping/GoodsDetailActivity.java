package com.ghd.ghd_shopping;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.ghd.ghd_shopping.alipay.AuthResult;
import com.ghd.ghd_shopping.alipay.PayResult;
import com.ghd.ghd_shopping.alipay.util.OrderInfoUtil2_0;
import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.javaBean.User;
import com.ghd.ghd_shopping.tools.XmlParse;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.loopj.android.image.SmartImageView;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

public class GoodsDetailActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

    LinearLayout ll_cart = null;
    Goods goods = null;
    User user = null;
    SmartImageView image = null;
    TextView price = null;
    TextView description = null;
    Button btn_cart = null;
    Button btn_pay = null;

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016101500688493";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102179613684";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "nkqxnf0903@sandbox.com";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCp7wG3jJtcM5291KJsP2NCjnc/6Pg3BLVM4hLr26M2Hx+Jk+tOGz4R7w8baAdHIDhPIDsApjwNmPHuAYMe5KJZXosXjn5ayTi2PSlfwr5/yANqYL3ACK+JOx7q5qtgShbjeC7uP1zzHFYOU+s9UFdf7DH2bwMhXABjjKiUCRcQmxI+77KtXDeMcyaCzdAEDZ52te8lfQSCuEbMXMLuOoqHLgjAH+0XPROPGNonVWVkOhITI+UPMwmLdZ4ZDXD+odL5d3iRLryc6dKFpSfB/ZkjjVZySDFWEqyu7NyPO6Ts8CSF0rGTV+uCJA+PvLh98Dos5YVxLSlBXX4WdN1kZkOpAgMBAAECggEALAfexQVst+4z4Bz5XNzrxjIN2gRuVCsnIt2tE2ncl0hnLomiPaG3aoQrwOkqgZZqoK30O83W35sSjJ3dsKKDIm7p0Ve9i5550FZ2ovZdz9QudmDMqbZWbVNhRnIwU4m+9n+MgXTvi5p6/clmuYNYsGUN6RGImRsyipjGkVztmgJ2137V8AB154wi8TBPwdBtuW3N/OYjVUFO98cmfoZMVBUINP++x5AAuyze0JBJtNnN8NC386FqsqOIXSQbcCrYfdtjDkvQPOZ6GoUoFZMK3kcHvh0akrNHo0+DNqm4eoicb7HWznh4PbahJj+NRDw/wHReSx19HlYkl5+uChsUZQKBgQDy3Eh6Jbrxy/cVYRnmGTQEeNEhpNvylppir9Uldvksb9ZRf1IziKNF9Skijxx1FYPscu8cSspAjSo1k69AV+R7R1JpRCvqUb+tx/6sVlvKx0z5nufcIOAAb9BIqlthqo/sAiOwKGbYho5TlvSXYL1PAiQJW0mM6NaD8H1Q1/82RwKBgQCzIKgiR9LfcsD8SsuVV0lZcJyVgDSFi/wO0cxAIyLfJfXqY6yjFvLOM5+yFeBpCAdwKmj29C22tKlYQl1SboiB6YjcrnZO4h8jYlFmpnSWZQborjeSrJ1T/+1eLUFc7efP0wXRm61by6VuG5iSqs/fJB7luoDv2kO5w30UTuB+jwKBgBie5GodsRoaR25PbcS6AWACr5Dtma66PeSLVtx5d0FeBSg19CttSsAo3oa2Y3grOjiaXktL+b0ZkpHj6vxm7K7iyCQL2TfkGb6Qa+0kxCGGmvMjfPYADzV8IG19d84q8HRIsz2EmwiQe5VV1G4UTJzZ5rVcH9S9NiIiG1I605C5AoGAOZu2FxhMTEqmoD0ZzlS9JUOfSEFsIssLbGSysKncLDIULaaSvfzCZ+iYYnjArGbFpaAuE8Yh8ZGWqOjnBHzB8C4AMyD0sZftdb7H+SBtfHTPTQPCoaxcCMjVOSWK0O0+UUtHosrTbSNId+nuHrVKlzQRr8ZUc08Z2uNxFZfsgScCgYEAh9PFet4/nItiJmM4ezKJdjY/wDxPkkUL3axZ6fUoOMwQW5X5ojJWR1ER6DJsvqKMAyHLsCIcyCYDpic6bFPGEC2vNGJevG3G0d/kcJOHwW/SiX2soZWx3T29ISPWmywPvZ3s0zrrRjLhebusb6vbxKofMzn5dLu5JRY7rhNm/90=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        insertOrder();
//                        showAlert(GoodsDetail.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(GoodsDetail.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(GoodsDetailActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(GoodsDetailActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    /**
     * 支付宝支付业务示例
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        double total_price = goods.getPrice();
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, total_price);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(GoodsDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        setContentView(R.layout.activity_goods_detail);
        initView();
        Intent intent = getIntent();
        int goods_id = intent.getIntExtra("goods_id", 1);
        try {
            getGoods(goods_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = sharedPreferences.getInt("id", 1);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        String image = sharedPreferences.getString("image", "");
        user = new User(id, name, password, image);
    }

    private void getGoods(int goods_id) throws IOException {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(goods_id))
                .build();
        Request request = new Request.Builder()
                .url(MyProperties.getUrlProperty(this) + "GoodsDetailServlet")
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
                    goods = XmlParse.getGoods(response.body().string());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageUrl(goods.getImage());
                        price.setText(String.format("%.2f", goods.getPrice()));
                        description.setText(goods.getDescription());
                    }
                });
            }
        });
    }

    private void initView() {
        sharedPreferences = GoodsDetailActivity.this.getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ll_cart = findViewById(R.id.ll_cart);
        price = findViewById(R.id.tv_price);
        description = findViewById(R.id.tv_description);
        image = findViewById(R.id.image_goods);
        btn_cart = findViewById(R.id.btn_cart);
        btn_pay = findViewById(R.id.btn_pay);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                // TODO: 2019/12/30 支付宝付款
                payV2();
//                insertOrder();
//                Intent intent = new Intent(GoodsDetailActivity.this,PaySuccessActivity.class);
//                startActivity(intent);
            }
        });

        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailActivity.this, MainActivity.class);
                intent.putExtra("fragment_flag", 3);
                startActivity(intent);
            }
        });
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(user.getName())) {
                    Toast.makeText(GoodsDetailActivity.this, "抱歉，请您先登录！", Toast.LENGTH_SHORT).show();
                } else {
                    insertCart();
                }
            }

            private void insertCart() {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("user_id", String.valueOf(user.getId()))
                        .add("goods_id", String.valueOf(goods.getId()))
                        .build();
                Request request = null;
                try {
                    request = new Request.Builder()
                            .url(MyProperties.getUrlProperty(GoodsDetailActivity.this) + "InsertCartServlet")
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
                        if (!message.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GoodsDetailActivity.this,"恭喜您，商品已加入购物车！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void insertOrder() {
        //将订单数据添加到数据库
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", String.valueOf(user.getId()))
                .add("goods_ids",String.valueOf(goods.getId()))
                .build();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(MyProperties.getUrlProperty(GoodsDetailActivity.this) + "InsertOrderServlet")
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
                        Intent intent = new Intent(GoodsDetailActivity.this,PaySuccessActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
