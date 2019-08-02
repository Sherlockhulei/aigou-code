package cn.itsource.web.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.plat.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user){
        if (user != null) {
            String userName = user.getUserName();
            String password = user.getPassword();
            if ("luffy".equals(userName) && "123".equals(password)){
                return AjaxResult.getAjaxResult().setSuccess(true).setMsg("登陆成功");
            }
        }
        return AjaxResult.getAjaxResult().setSuccess(false).setMsg("登陆失败，请重新确认用户名及密码");
    }
}
