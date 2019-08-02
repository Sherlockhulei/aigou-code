package cn.itsource.common.client;

import cn.itsource.basic.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "COMMON-SERVER",fallbackFactory = RedisFallBackFactory.class)
public interface RedisClient {

    /**
     * 存到缓存里
     */
    @PostMapping("/redis")
    public AjaxResult set(@RequestParam("key") String key, @RequestParam("value") String value);

    /**
     * 拿到缓存中的数据
     */
    @GetMapping("/redis")
    public AjaxResult get(@RequestParam("key") String key);
}
