package com.ghd.ghd_shopping.navigateFragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.ghd.ghd_shopping.GoodsDetailActivity;
import com.ghd.ghd_shopping.MainActivity;
import com.ghd.ghd_shopping.PaySuccessActivity;
import com.ghd.ghd_shopping.R;
import com.ghd.ghd_shopping.alipay.AuthResult;
import com.ghd.ghd_shopping.alipay.PayResult;
import com.ghd.ghd_shopping.alipay.util.OrderInfoUtil2_0;
import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.javaBean.User;
import com.ghd.ghd_shopping.tools.MyProperties;
import com.ghd.ghd_shopping.tools.XmlParse;
import com.loopj.android.image.SmartImageView;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    StringBuffer goods_ids = null;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    boolean flag = true; // 判断全选按钮是不是自己造成的，还有可能是所有选中的时候造成的
    User user = null;
    List<Goods> goodsList = null;
    boolean[] checks = null;
    View layout = null;
    TextView tv_total_price = null;
    CheckBox cb_select_all = null;
    ListView lv_goods = null;
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
                        // 支付成功调用
//                        Toast.makeText(getContext(),"支付成功",Toast.LENGTH_SHORT).show();
                        insertOrder();
//                        showAlert(getActivity(), getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(),"支付失败",Toast.LENGTH_SHORT).show();
//                        showAlert(getActivity(), getString(R.string.pay_failed) + payResult);
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
                        showAlert(getActivity(), getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(getActivity(), getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void deleteCart() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", String.valueOf(user.getId()))
                .add("goods_ids",goods_ids.toString())
                .build();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(MyProperties.getUrlProperty(layout.getContext()) + "DeleteCartServlet")
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), PaySuccessActivity.class);
                        startActivity(intent);
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
                .add("goods_ids",goods_ids.toString())
                .build();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(MyProperties.getUrlProperty(layout.getContext()) + "InsertOrderServlet")
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getContext(),"商品已插入订单表",Toast.LENGTH_SHORT).show();
                        deleteCart();
                    }
                });
            }
        });
    }

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        layout = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        btn_pay.setText("去结算(" + 0 + ")");
        tv_total_price.setText(String.format("%.2f", 0.00));
        cb_select_all.setChecked(false);
        int id = sharedPreferences.getInt("id", 0);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        String image = sharedPreferences.getString("image", "");
        user = new User(id, name, password, image);
        getGoodsList();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(goodsList==null){
            checks = new boolean[0];
        }else{
            checks = new boolean[goodsList.size()];
        }
        cb_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(int i=0;i<checks.length;i++){
                        checks[i]=true;
                    }
                    final int count = lv_goods.getChildCount();
                    for (int i = 0; i < count; i++) {
                        final LinearLayout content = (LinearLayout) lv_goods.getChildAt(i);
                        CheckBox temp = content.findViewById(R.id.cb_select_single);
                        temp.setChecked(true);
                    }
                }else{
                    if(flag){
                        for(int i=0;i<checks.length;i++){
                            checks[i]=false;
                        }
                        final int count = lv_goods.getChildCount();
                        for (int i = 0; i < count; i++) {
                            final LinearLayout content = (LinearLayout) lv_goods.getChildAt(i);
                            CheckBox temp = content.findViewById(R.id.cb_select_single);
                            temp.setChecked(false);
                        }
                    }
                }
            }
        });
        lv_goods.setAdapter(new MyGoodsAdapter());
        lv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(layout.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goods_id",goodsList.get(position).getId());
                startActivity(intent);
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if(user.getName().equals("")){
                    Toast.makeText(layout.getContext(),"抱歉，请您先登陆！",Toast.LENGTH_LONG).show();
                }else{
                    buyGoods();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void buyGoods() {
        goods_ids = new StringBuffer();
        for(int i=0;i<checks.length;i++){
            if(checks[i]==true){
                goods_ids.append(goodsList.get(i).getId()+",");
            }
        }
        if(goods_ids.length()==0){
            Toast.makeText(layout.getContext(),"抱歉，您未选中任何商品！",Toast.LENGTH_SHORT).show();
        }else{
            goods_ids.deleteCharAt(goods_ids.length()-1);
            // 支付宝付款
            // TODO: 2019/12/30 支付宝付款
            payV2();
//            insertOrder();
       }
    }

    /**
     * 支付宝支付业务示例
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getContext(), getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        double total_price = Double.parseDouble(tv_total_price.getText().toString());
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, total_price);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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


    private void getGoodsList() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", String.valueOf(user.getId()))
                .build();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(MyProperties.getUrlProperty(layout.getContext()) + "SelectCartServlet")
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
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        sharedPreferences = layout.getContext().getSharedPreferences("user", layout.getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tv_total_price = layout.findViewById(R.id.tv_total_price);
        btn_pay = layout.findViewById(R.id.btn_pay);
        cb_select_all = layout.findViewById(R.id.cb_select_all);
        lv_goods = layout.findViewById(R.id.lv_goods_content);
    }

    class MyGoodsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(goodsList==null)
                return 0;
            else{
                return goodsList.size();
            }
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(layout.getContext(), R.layout.cart_goods, null);
                holder = new ViewHolder();
                holder.checkBox = convertView.findViewById(R.id.cb_select_single);
                holder.goods_image = convertView.findViewById(R.id.goods_image);
                holder.goods_description = convertView.findViewById(R.id.goods_description);
                holder.tv_price = convertView.findViewById(R.id.tv_price);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final int pos = position; //pos必须声明为final
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checks[pos] = isChecked;
                    int number = 0;
                    double total_price = 0;
                    for(int i=0;i<checks.length;i++){
                        if(checks[i]){
                            number++;
                            total_price+=goodsList.get(i).getPrice();
                        }
                    }
                    if(number == checks.length){
                        flag = true;
                        cb_select_all.setChecked(true);
                    }else{
                        flag = false;
                        cb_select_all.setChecked(false);
                    }
                    btn_pay.setText("去结算(" + number + ")");
                    tv_total_price.setText(String.format("%.2f", total_price));
                }
            });
            if(checks.length!=0){
                holder.checkBox.setChecked(checks[pos]);
                holder.tv_price.setText(String.format("%.2f", goodsList.get(pos).getPrice()));
                holder.goods_description.setText(goodsList.get(pos).getDescription());
                holder.goods_image.setImageUrl(goodsList.get(pos).getImage());
            }
            return convertView;
        }

        class ViewHolder {
            SmartImageView goods_image;
            TextView goods_description;
            TextView tv_price;
            CheckBox checkBox;
        }
    }
}