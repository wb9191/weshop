package tech.wetech.weshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.weshop.mapper.BrandMapper;
import tech.wetech.weshop.po.Brand;
import tech.wetech.weshop.query.BrandPageQuery;
import tech.wetech.weshop.service.BrandService;
import tech.wetech.weshop.vo.CreateBrandFormVO;
import tech.wetech.weshop.vo.UpdateBrandFormVO;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

/**
 * @author cjbi
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public PageInfo<Brand> queryBrandPageInfo(BrandPageQuery brandPageQuery) {
        Weekend<Brand> example = Weekend.of(Brand.class);
        WeekendCriteria<Brand, Object> criteria = example.weekendCriteria();
        if (brandPageQuery.getId() != null) {
            criteria.andEqualTo(Brand::getId, brandPageQuery.getId());
        }
        if (brandPageQuery.getName() != null) {
            criteria.andEqualTo(Brand::getName, brandPageQuery.getName());
        }
        return PageHelper.startPage(brandPageQuery.getPageNum(), brandPageQuery.getPageSize())
                .doSelectPageInfo(() -> brandMapper.selectByExample(example));
    }

    @Override
    public void createBrand(CreateBrandFormVO createBrandFormVO) {
        Brand brand = new Brand(createBrandFormVO);
        brandMapper.insertSelective(brand);
    }

    @Override
    public void updateBrand(UpdateBrandFormVO updateBrandFormVO) {
        Brand brand = new Brand(updateBrandFormVO);
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void deleteBrand(Integer brandId) {
        brandMapper.deleteByPrimaryKey(brandId);
    }
}
