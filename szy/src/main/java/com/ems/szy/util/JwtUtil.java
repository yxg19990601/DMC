package com.ems.szy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {
    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 60000*60*24;

    /**
     * token私钥
     */
    private static final String SIGN = "df9ehg0sdf90weg";

    /**
     * 生成签名,15分钟后过期
     * @param userId
     * @param pwd
     * @return
     */
    public static String getToken(String userId,String pwd){
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(SIGN);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withHeader(header).withClaim("userId",userId)
                .withClaim("pwd",pwd).withExpiresAt(date).sign(algorithm);
    }


    public static DecodedJWT verity(String token){
        Algorithm algorithm = Algorithm.HMAC256(SIGN);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public static void main(String[] args) {
        String token = getToken("张三", "1234kjadsf");

        System.out.println("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwd2QiOiIxMjM0a2phZHNmIiwiZXhwIjoxNTk5MzE1NTY0LCJ1c2VySWQiOiLlvKDkuIkifQ.DjulAT-IAlvxtEyuSwr_Z06JJ6_w_VioMRcL3VdJO0E");

        DecodedJWT verity = verity("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwd2QiOiIxMjM0a2phZHNmIiwiZXhwIjoxNTk5MzE1NTY0LCJ1c2VySWQiOiLlvKDkuIkifQ.DjulAT-IAlvxtEyuSwr_Z06JJ6_w_VioMRcL3VdJO0E");
        System.out.println(verity.getClaim("pwd").asString());


    }
}
