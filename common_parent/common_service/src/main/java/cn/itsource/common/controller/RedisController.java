package cn.itsource.common.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.RedisUtils;
import cn.itsource.common.client.RedisClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController implements RedisClient{
    /**
     * 存到缓存里
     *
     * @param key
     * @param value
     */
    @Override
    @PostMapping("/redis")
    public AjaxResult set(String key, String value) {
        try {
            RedisUtils.INSTANCE.set(key, value);
            return AjaxResult.getAjaxResult().setMsg("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMsg("保存失败"+e.getMessage());
        }
    }

    /**
     * 拿到缓存中的数据
     *
     * @param key
     */
    @Override
    @GetMapping("/redis")
    public AjaxResult get(String key) {
        try {
            String json = RedisUtils.INSTANCE.get(key);
            return AjaxResult.getAjaxResult().setMsg("保存成功").setResultObj(json);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMsg("保存失败"+e.getMessage());
        }
    }
}
