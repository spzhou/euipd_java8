/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.account;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.enums.GoodsClassEnum;
import com.course.server.enums.MallSellStatusEnum;
import com.course.server.mapper.*;

import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class GoodsService {

    private static final Logger LOG = LoggerFactory.getLogger(GoodsService.class);

    @Resource
    private MallGoodsInfoMapper mallGoodsInfoMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private OnlinePayGoodsMapper onlinePayGoodsMapper;

    /**
     * 将aiisci项目的产品信息保存的goods总表中
     * 将课程信息保存到商品总表中，如果已存在则更新，不存在则新增
     * 
     * @param courseDto 课程数据传输对象
     */
    @Transactional
    public void saveMallCourseInfo(CourseDto courseDto) {
        MallGoodsInfoDto mallGoodsInfoDto = new MallGoodsInfoDto(courseDto, GoodsClassEnum.COURSE.getCode());
        MallGoodsInfo mallGoodsInfo = CopyUtil.copy(mallGoodsInfoDto, MallGoodsInfo.class);
        //检查数据库是有有相同的productId,如果有就更新,如果没有就新增
        MallGoodsInfoExample mallGoodsInfoExample = new MallGoodsInfoExample();
        MallGoodsInfoExample.Criteria criteria = mallGoodsInfoExample.createCriteria();
        criteria.andProductClassEqualTo(GoodsClassEnum.COURSE.getCode()).andProductIdEqualTo(courseDto.getId());
        List<MallGoodsInfo> mallGoodsInfos = mallGoodsInfoMapper.selectByExample(mallGoodsInfoExample);

        if(Objects.equals(courseDto.getStatus(), CourseStatusEnum.PUBLISH.getCode())){
            mallGoodsInfo.setGoodsSellStatus(MallSellStatusEnum.SELL_STATUS_UP.getStatus());
        }else{
            mallGoodsInfo.setGoodsSellStatus(MallSellStatusEnum.SELL_STATUS_GOODS_APPROVE.getStatus());
        }

        if(mallGoodsInfos.size()>0){
            mallGoodsInfo.setGoodsId(mallGoodsInfos.get(0).getGoodsId());
            mallGoodsInfoMapper.updateByPrimaryKey(mallGoodsInfo);
        }else{
            mallGoodsInfoMapper.insert(mallGoodsInfo);
        }
    }

    /**
     * 保存产品信息到商品总表
     * 将产品信息保存到商品总表中，如果已存在则更新，不存在则新增
     * 
     * @param productDto 产品数据传输对象
     */
    @Transactional
    public void saveMallProductInfo(ProductDto productDto) {
        MallGoodsInfoDto mallGoodsInfoDto = new MallGoodsInfoDto(productDto, GoodsClassEnum.PRODUCT.getCode());
        MallGoodsInfo mallGoodsInfo = CopyUtil.copy(mallGoodsInfoDto, MallGoodsInfo.class);
        //检查数据库是有有相同的productId,如果有就更新,如果没有就新增
        MallGoodsInfoExample mallGoodsInfoExample = new MallGoodsInfoExample();
        MallGoodsInfoExample.Criteria criteria = mallGoodsInfoExample.createCriteria();
        criteria.andProductClassEqualTo(GoodsClassEnum.PRODUCT.getCode()).andProductIdEqualTo(productDto.getProductId());
        List<MallGoodsInfo> mallGoodsInfos = mallGoodsInfoMapper.selectByExample(mallGoodsInfoExample);

        if(Objects.equals(productDto.getStatus(), CourseStatusEnum.PUBLISH.getCode())){
            mallGoodsInfo.setGoodsSellStatus(MallSellStatusEnum.SELL_STATUS_UP.getStatus());
        }else{
            mallGoodsInfo.setGoodsSellStatus(MallSellStatusEnum.SELL_STATUS_GOODS_APPROVE.getStatus());
        }

        if(mallGoodsInfos.size()>0){
            mallGoodsInfo.setGoodsId(mallGoodsInfos.get(0).getGoodsId());
            mallGoodsInfoMapper.updateByPrimaryKey(mallGoodsInfo);
        }else{
            mallGoodsInfoMapper.insert(mallGoodsInfo);
        }
    }

    /**
     * 获取商品信息
     * 根据产品ID查询商品信息列表
     * 
     * @param productId 产品ID
     * @return 返回商品信息数据传输对象列表
     */
    public List<MallGoodsInfoDto> getMallGoodsInfo(String productId){
        MallGoodsInfoExample mallGoodsInfoExample = new MallGoodsInfoExample();
        MallGoodsInfoExample.Criteria criteria = mallGoodsInfoExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        List<MallGoodsInfo> mallGoodsInfos = mallGoodsInfoMapper.selectByExample(mallGoodsInfoExample);
        List<MallGoodsInfoDto> mallGoodsInfoDtos = CopyUtil.copyList(mallGoodsInfos, MallGoodsInfoDto.class);
        return mallGoodsInfoDtos;
    }


    /**
     * 根据商品ID获取机构信息
     * 通过商品ID查询对应的销售机构信息
     * 
     * @param goodsId 商品ID
     * @return 返回机构对象，如果不存在则返回null
     */
    public Institution getInstitutionByGoodsId(Long goodsId){
        MallGoodsInfo mallGoodsInfo = mallGoodsInfoMapper.selectByPrimaryKey(goodsId);

        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(mallGoodsInfo.getSupplierLoginname());//一般情况产品的创建者和销售者是相同的,但是,如果产品授权销售的时候,产品的创建者和销售者是不同的,这里是实际销售人

        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        if(institutions.size()>0){
            return institutions.get(0);
        }
        return null;
    }

    /**
     * 根据ID获取商品信息
     * 通过商品ID查询商品详细信息
     * 
     * @param goodsId 商品ID
     * @return 返回商品信息对象
     */
    public MallGoodsInfo getMallGoodsById(Long goodsId){
        MallGoodsInfo mallGoodsInfo = mallGoodsInfoMapper.selectByPrimaryKey(goodsId);
        return mallGoodsInfo;
    }

    /**
     * 判断是否为在线支付商品
     * 检查指定商品是否支持在线支付
     * 
     * @param goodsId 商品ID
     * @return 如果支持在线支付返回true，否则返回false
     */
    public boolean isOnlinePayGoods(Long goodsId){
        OnlinePayGoodsExample onlinePayGoodsExample = new OnlinePayGoodsExample();
        OnlinePayGoodsExample.Criteria criteria = onlinePayGoodsExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<OnlinePayGoods> onlinePayGoods = onlinePayGoodsMapper.selectByExample(onlinePayGoodsExample);
        if(onlinePayGoods.size()>0){
            return true;
        }
        return false;
    }
}
