package com.ghd.ghd_shopping.tools;

import android.util.Xml;
import com.ghd.ghd_shopping.javaBean.Info;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InfoService {
    public static List<List<Info>> getInfosFromXML(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<List<Info>> classics = null;
        List<Info> infos = null;
        Info info = null;
        int type = parser.getEventType();
        while(type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("classic".equals(parser.getName())){
                        classics = new ArrayList<>();
                    }else if("infos".equals(parser.getName())){
                        infos = new ArrayList<>();
                    }else if("info".equals(parser.getName())){
                        info = new Info();
                        List<String> names = new ArrayList<>();
                        String label = parser.getAttributeValue(0);
                        info.setLabel(label);
                        info.setName(names);
                    }else if("name".equals(parser.getName())){
                        String temp = parser.nextText();
                        info.getName().add(temp);
                    }
                    break;
                    case XmlPullParser.END_TAG:
                        if("info".equals(parser.getName())){
                            infos.add(info);
                            info = null;
                        }else if("infos".equals(parser.getName())){
                            classics.add(infos);
                            infos = null;
                        }
                        break;
            }
            type = parser.next();
        }
        return classics;
    }
}
