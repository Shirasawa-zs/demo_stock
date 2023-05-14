package com.scy.stock.controller;

import com.scy.stock.service.UserService;
import com.scy.stock.vo.req.LoginReqVo;
import com.scy.stock.vo.resp.LoginRespVo;
import com.scy.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo){
        R<LoginRespVo> r= this.userService.login(loginReqVo);
        return r;
    }

    @GetMapping("/captcha")
    public R<Map> generateCaptcha(){
        return this.userService.generateCaptcha();
    }

}
