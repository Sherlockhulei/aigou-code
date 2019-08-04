package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.common.client.RedisClient;
import cn.itsource.common.client.StaticPageClient;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author solargen
 * @since 2019-07-31
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StaticPageClient staticPageClient;

    @Override
    public List<ProductType> loadTree() {
        AjaxResult result = redisClient.get("productTypeList");
        String productTypeListJson = (String) result.getResultObj();
        List<ProductType> productTypeList = JSON.parseArray(productTypeListJson, ProductType.class);
        if (productTypeList == null || productTypeList.size() <= 0) {
            productTypeList = loop();
            redisClient.set("productTypeList", JSON.toJSONString(productTypeList));
        }
        return productTypeList;
    }

    @Override
    public void homePage() {

        //生成product.type.vm.html
        Map<String,Object> map = new HashMap<>();
        String templatePath = "D:/ruanjian/IDEA/aigou-parent/product_parent" +
                "/product_server/src/main/resources/template/product.type.vm";
        String targetPath = "D:/ruanjian/IDEA/aigou-parent/product_parent" +
                "/product_server/src/main/resources/template/product.type.vm.html";
        List<ProductType> list = loadTree();
        map.put("model", list);
        map.put("templatePath", templatePath);
        map.put("targetPath", targetPath);

        //生成home.html
        map = new HashMap<>();
        templatePath = "D:/ruanjian/IDEA/aigou-parent/product_parent/product_server/" +
                "src/main/resources/template/home.vm";
        targetPath = "D:/ruanjian/IDEA/aigou-web-parent/aigou-web-homePage/home.html";
        Map<String,String> model = new HashMap<>();
        model.put("staticRoot", "D:/ruanjian/IDEA/aigou-parent/product_parent/" +
                "product_server/src/main/resources/");
        map.put("model", model);
        map.put("templatePath", templatePath);
        map.put("targetPath", targetPath);
        staticPageClient.gStaticPage(map);

    }

    //循环
    public List<ProductType> loop() {
        List<ProductType> parents = baseMapper.selectList(null);
        List<ProductType> list = null;
        if (parents != null) {
            list = new ArrayList<>();//存放查询结果
            Map<Long,ProductType> map = new HashMap<>();//
            for (ProductType parent : parents) {
                map.put(parent.getId(), parent );
            }
            for (ProductType parent : parents) {
                if(parent.getPid() == 0L){
                    list.add(parent);
                }else {
                    ProductType productType = map.get(parent.getPid());
                    List<ProductType> children = parent.getChildren();
                    if (children == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    productType.getChildren().add(parent);
                }
            }
            return list;
        }
        return null;
    }

    public List<ProductType> recursive(Long id){
        List<ProductType> parents = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid", id));
        for (ProductType parent : parents) {
            List<ProductType> rec = recursive(parent.getId());
            if(!rec.isEmpty()){
                parent.setChildren(rec);
            }
        }
        return parents;
    }

   /* @Override
    public boolean saveBatch(Collection<ProductType> entityList) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<ProductType> entityList) {
        return false;
    }

    @Override
    public boolean update(Wrapper<ProductType> updateWrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<ProductType> entityList) {
        return false;
    }

    @Override
    public ProductType getOne(Wrapper<ProductType> queryWrapper) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<ProductType> list() {
        return null;
    }

    @Override
    public IPage<ProductType> page(IPage<ProductType> page) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return null;
    }

    @Override
    public List<Object> listObjs() {
        return null;
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public List<Object> listObjs(Wrapper<ProductType> queryWrapper) {
        return null;
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<ProductType> page) {
        return null;
    }

    @Override
    public QueryChainWrapper<ProductType> query() {
        return null;
    }

    @Override
    public LambdaQueryChainWrapper<ProductType> lambdaQuery() {
        return null;
    }

    @Override
    public UpdateChainWrapper<ProductType> update() {
        return null;
    }

    @Override
    public LambdaUpdateChainWrapper<ProductType> lambdaUpdate() {
        return null;
    }*/
}
