package com.ems.szy.wegw.entity.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateXmlAdapter extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public DateXmlAdapter (){
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null || v.equals("")) {
            return  null;
        }

        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {

        if (v == null) {
            return  null;
        }
        return dateFormat.format(v);
    }
}
