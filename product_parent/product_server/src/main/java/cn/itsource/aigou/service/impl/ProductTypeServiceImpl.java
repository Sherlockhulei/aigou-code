package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<ProductType> loadTree() {
        return loop();
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
}
