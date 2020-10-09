package com.ems.szy.controller;

import com.ems.szy.domain.User;
import com.ems.szy.dto.ResponseDto;
import com.ems.szy.dto.Token;
import com.ems.szy.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
//@CrossOrigin(origins = "*",maxAge = 3600)
public class LoginController {


    @ResponseBody
    @PostMapping(value="/vue-admin-template/user/login")
    public ResponseDto login( @RequestBody User user){
        Token token = new Token();
        String tokenStr = JwtUtil.getToken(user.getUserId(), user.getPwd());
        token.setToken(tokenStr);
        return  new ResponseDto(20000,token);
    }

    @ResponseBody
    @GetMapping(value="/vue-admin-template/user/info")
    public ResponseDto info(String token){
        // 查询数据库
        System.out.println(token);
        Map<String,Object>  data = new HashMap<>();
        ArrayList<String> role = new ArrayList<>();
        role.add("admin");
        data.put("token",role);
        data.put("introduction","I am a super administrator");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name","Super Admin");

        return  new ResponseDto(20000,data);
    }


    @ResponseBody
    @PostMapping(value="/vue-admin-template/user/logout")
    public ResponseDto logout(@RequestHeader("token") String token){
        System.out.println(token);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(20000);
        responseDto.setData("success");
        return  responseDto;
    }

  /*  @GetMapping(value="/getData")
    public ModelAndView getData(){
        ModelAndView modelAndView = new ModelAndView("/static/index.html");
        return  modelAndView;
    }*/
}
