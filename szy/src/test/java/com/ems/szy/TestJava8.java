package com.ems.szy;

import java.util.HashMap;
import java.util.Map;

public class TestJava8 {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("a","a");
        map.put("b","b");
        map.forEach((k,v) -> {

        });

        map.values().forEach((v) -> {
        });

    }
}
