package com.ems.szy.wegw.entity.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalXmlAdapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String v) throws Exception {
        if (v== null || v.equals(""))
            return null;

        BigDecimal value = new BigDecimal(v);
        return value.setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

    @Override
    public String marshal(BigDecimal v) throws Exception {
        if (v == null)
            return null;
        return v.setScale(2,BigDecimal.ROUND_HALF_DOWN).toPlainString();
    }
}
