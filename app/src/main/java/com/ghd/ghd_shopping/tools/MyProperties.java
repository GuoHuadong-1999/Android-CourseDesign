package com.ghd.ghd_shopping.tools;

import android.content.Context;

import java.io.*;
import java.util.Properties;

public class MyProperties {
    public static String getUrlProperty(Context context) throws IOException {
        Properties properties = new Properties();
        InputStream is = context.getAssets().open("goods_shopping_properties");
        properties.load(is);
        String url = properties.getProperty("url");
        return url;
    }
}
