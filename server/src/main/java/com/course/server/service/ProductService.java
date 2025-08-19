/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.enums.PageCategoryEnum;
import com.course.server.enums.ProductStatusEnum;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private InstitutionAuthorityMapper institutionAuthorityMapper;
    @Resource
    private MemberService memberService;

    /**
     * 列表查询
     */
    public List<ProductDto> all() {
        ProductExample productExample = new ProductExample();
        List<Product> productList = productMapper.selectByExample(productExample);
        return CopyUtil.copyList(productList, ProductDto.class);
    }

    /**
     * 查询某个用户的产品
     */
    public List<ProductDto> getUserProduct(String loginName) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<Product> productList = productMapper.selectByExample(productExample);
        return CopyUtil.copyList(productList, ProductDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ProductDto productDto) {
        Product product = CopyUtil.copy(productDto, Product.class);
        if (ObjectUtils.isEmpty(productDto.getProductId())) {
            this.insert(product);
        } else {
            this.update(product);
        }

        // 批量保存产品分类
        productCategoryService.saveBatch(product.getProductId(), productDto.getCategorys());
    }

    /**
     * 新增
     */
    private void insert(Product product) {
        //loginNameProduct.setId(UuidUtil.getShortUuid());
        product.setProductId(UuidUtil.getShortUuid());
        product.setCreateTime(new Date());
        if(product.getStatus()==null){
            product.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        productMapper.insert(product);
    }

    /**
     * 更新
     */
    private void update(Product product) {
        if(product.getStatus()==null){
            product.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        productMapper.updateByPrimaryKey(product);
    }
    /**
     * 选择性更新产品信息
     * 根据产品ID选择性更新产品信息
     * 
     * @param product 产品对象
     */
    public void updateBySelective(Product product) {
        if(product.getStatus()==null){
            product.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        productMapper.updateByPrimaryKeySelective(product);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        productMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据产品ID查找产品信息
     * 通过产品ID查询产品详细信息
     * 
     * @param id 产品ID
     * @return 返回产品数据传输对象
     */
    public ProductDto findById(String id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return CopyUtil.copy(product, ProductDto.class);
    }

    /**
     * 根据产品ID获得产品提供者
     * 通过产品ID查询产品的创建者登录名
     * 
     * @param productId 产品ID
     * @return 返回产品创建者的登录名
     */
    public String getCreatorLoginname(String productId){
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        List<Product> products = productMapper.selectByExample(productExample);
        return products.get(0).getLoginName();
    }

    /**
     * 产品排序
     * 根据排序数据传输对象更新产品的排序值
     * 
     * @param sortDto 排序数据传输对象
     */
    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        // 排序主要要用,根据关注,点赞等计算他的排序值,用于页面显示时的动态排序;
        Product product = productMapper.selectByPrimaryKey(sortDto.getId());
        product.setSort(sortDto.getNewSort());
        productMapper.updateByPrimaryKey(product);

    }

    /**
     * 新课列表查询
     * 查询已发布的新产品，按创建日期倒序排列
     * 
     * @param pageDto 分页查询对象
     * @return 返回新产品数据传输对象列表
     */
    public List<ProductDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(ProductStatusEnum.PUBLISH.getCode());
        productExample.setOrderByClause("created_at desc");
        List<Product> productList = productMapper.selectByExample(productExample);

        List<ProductDto> productDtoList = CopyUtil.copyList(productList, ProductDto.class);
        return productDtoList;
    }

    /**
     * 列表查询：关联课程分类表
     * @param pageDto
     */
    @Transactional
    public void list(ProductPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //1. 查出所有的课程
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("sort desc");
        ProductExample.Criteria criteria2 = productExample.createCriteria();
        criteria2.andLoginNameEqualTo(userDto.getLoginName());
        productList = productMapper.selectByExample(productExample);
        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        pageDto.setTotal(pageInfo.getTotal());

        productDtoList = CopyUtil.copyList(productList, ProductDto.class);

        //为产品卡片添加机构信息
        for(ProductDto productDto : productDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(productDto.getLoginName());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                productDto.setInstitutionName(institutions.get(0).getName());
                productDto.setInstitutionId(institutions.get(0).getId());
                productDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        pageDto.setList(productDtoList);
    }

    /**
     * Web端产品列表查询
     * 根据分类ID查询产品列表，支持分页和分类筛选
     * 
     * @param pageDto 产品分页查询对象
     */
    @Transactional
    public void webList(ProductPageDto pageDto) {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<ProductCategory> productCategoryList = null;
        //1. 如果categoryId不为null，就只查询对应categoryId的课程；
        if(!ObjectUtils.isEmpty(pageDto.getCategoryId())){
            ProductCategoryExample productCategoryExample = new ProductCategoryExample();
            ProductCategoryExample.Criteria criteria4 = productCategoryExample.createCriteria();
            criteria4.andCategoryIdEqualTo(pageDto.getCategoryId());
            productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);

            List<Product> productList_temp = new ArrayList<>();
            //2. 根据categoryList查询对应的courseList
            for(ProductCategory productCategory: productCategoryList){
                Product product = productMapper.selectByPrimaryKey(productCategory.getProductId());
                if(product!=null){
                    productList_temp.add(product);
                }

            }
            //机构中存在机构被删除,但是分类表中还有这个institutionId的情况
            List<Product> productList = new ArrayList<>();
            for(Product product :productList_temp){
                if(product.getSort()==null){
                    product.setSort(0);
                }
                if(product.getStatus()==null){
                    product.setStatus(CourseStatusEnum.DRAFT.getCode());
                }
                productList.add(product);
            }

            for(Product product : productList){
                if(product.getStatus().equals(CourseStatusEnum.PUBLISH.getCode())){
                    ProductDto productDto = CopyUtil.copy(product, ProductDto.class);
                    productDtoList.add(productDto);
                }
            }

            productDtoList = productDtoList.stream().sorted(Comparator.comparing(ProductDto::getSort).reversed()).collect(Collectors.toList());

            int count = productDtoList.size();
            int page = pageDto.getPage();
            int size = pageDto.getSize();
            int end = page*size;
            if(end > count){
                end = count;
            }

            productDtoList= productDtoList.subList((page-1)*size, end);
            pageDto.setTotal(count);

        }else{//categoryId为空
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            List<Product> productList = new ArrayList<>();

            //1. 查出所有的机构
            ProductExample productExample = new ProductExample();
            productExample.setOrderByClause("sort desc");
            ProductExample.Criteria criteria = productExample.createCriteria();
            criteria.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
            productList = productMapper.selectByExample(productExample);
            PageInfo<Product> pageInfo = new PageInfo<>(productList);
            pageDto.setTotal(pageInfo.getTotal());

            productDtoList = CopyUtil.copyList(productList, ProductDto.class);
        }

        //为产品卡片添加机构信息
        for(ProductDto productDto : productDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(productDto.getLoginName());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                productDto.setInstitutionName(institutions.get(0).getName());
                productDto.setInstitutionId(institutions.get(0).getId());
                productDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.PRODUCT.getCode());
        for(ProductDto productDto : productDtoList){
            memberActionDto.setParamId(productDto.getProductId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            productDto.setWatchNum(actionAllInfoDto.getWatchNum());
            productDto.setLikeNum(actionAllInfoDto.getLikeNum());
            productDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        pageDto.setList(productDtoList);
    }

    /**
     * 获取产品详细信息
     * 根据产品ID获取产品的完整信息，包括机构信息和用户行为数据
     * 
     * @param productId 产品ID
     * @return 返回产品详细信息数据传输对象
     */
    @Transactional
    public ProductDto getProductDto(String productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDto productDto = CopyUtil.copy(product, ProductDto.class);

        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(productDto.getLoginName());
        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        if(institutions.size()>0){
            productDto.setInstitutionName(institutions.get(0).getName());
            productDto.setInstitutionId(institutions.get(0).getId());
            productDto.setInstitutionLogo(institutions.get(0).getLogo());
        }


        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.PRODUCT.getCode());
        memberActionDto.setParamId(productDto.getProductId());
        ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
        if(actionAllInfoDto!=null){
            actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
            actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
            actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
        }
        productDto.setWatchNum(actionAllInfoDto.getWatchNum());
        productDto.setLikeNum(actionAllInfoDto.getLikeNum());
        productDto.setCollectNum(actionAllInfoDto.getCollectNum());

        return productDto;
    }

    /**
     * 首页产品列表查询
     * 查询产品列表用于首页展示，支持状态筛选
     * 
     * @param pageDto 产品分页查询对象
     */
    @Transactional
    public void indexList(ProductPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserDto userDto = pageDto.getUser();
        List<ProductDto> productDtoList = new ArrayList<>();

        //1. 查出所有的课程
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria2 = productExample.createCriteria();

        List<Product> productList = new ArrayList<>();

        if(!ObjectUtils.isEmpty(pageDto.getStatus())) {
            criteria2.andStatusEqualTo(pageDto.getStatus());
            productList = productMapper.selectByExample(productExample);
        }else{
            productList = productMapper.selectByExample(productExample);
        }
        productDtoList = CopyUtil.copyList(productList, ProductDto.class);

        productDtoList = productDtoList.stream().sorted(Comparator.comparing(ProductDto::getSort).reversed()).collect(Collectors.toList());
        //为课程卡片添加机构名称
        for(ProductDto productDto : productDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(productDto.getLoginName());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                productDto.setInstitutionName(institutions.get(0).getName());
                productDto.setInstitutionId(institutions.get(0).getId());
                productDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.PRODUCT.getCode());
        for(ProductDto productDto : productDtoList){
            memberActionDto.setParamId(productDto.getProductId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            productDto.setWatchNum(actionAllInfoDto.getWatchNum());
            productDto.setLikeNum(actionAllInfoDto.getLikeNum());
            productDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }


        pageDto.setList(productDtoList);
    }

}
