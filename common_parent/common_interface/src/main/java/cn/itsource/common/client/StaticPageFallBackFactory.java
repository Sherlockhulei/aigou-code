package cn.itsource.common.client;

import cn.itsource.basic.util.AjaxResult;
import feign.hystrix.FallbackFactory;

import java.util.Map;

public class StaticPageFallBackFactory implements FallbackFactory<StaticPageClient>{

    @Override
    public StaticPageClient create(Throwable throwable) {
        return new StaticPageClient() {
            @Override
            public AjaxResult gStaticPage(Map<String, Object> map) {
                return AjaxResult.getAjaxResult().setSuccess(false).setMsg("系统异常");
            }
        };
    }
}
