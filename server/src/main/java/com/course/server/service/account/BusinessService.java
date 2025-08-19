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
import com.course.server.mapper.InstitutionMapper;
import com.course.server.mapper.MallGoodsInfoMapper;
import com.course.server.mapper.OnlinePayGoodsMapper;
import com.course.server.service.InstitutionService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 经营管理
 */

@Service
public class BusinessService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessService.class);

    @Resource
    private OnlinePayGoodsMapper onlinePayGoodsMapper;
    @Resource
    private GoodsService goodsService;
    @Resource
    private MallGoodsInfoMapper mallGoodsInfoMapper;
    @Resource
    private InstitutionService institutionService;

    /**
     * 获取经销产品分页list
     */
    @Transactional
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        OnlinePayGoodsExample onlinePayGoodsExample = new OnlinePayGoodsExample();
        List<OnlinePayGoods> onlinePayGoods = onlinePayGoodsMapper.selectByExample(onlinePayGoodsExample);
        PageInfo<OnlinePayGoods> pageInfo = new PageInfo<>(onlinePayGoods);
        pageDto.setTotal(pageInfo.getTotal());
        List<OnlinePayGoodsDto> onlinePayGoodsDtos = new ArrayList<>();
        for(OnlinePayGoods onlinePayGoods1 : onlinePayGoods){
            OnlinePayGoodsDto onlinePayGoodsDto = new OnlinePayGoodsDto();
            onlinePayGoodsDto.setId(onlinePayGoods1.getId());
            onlinePayGoodsDto.setGoodsId(onlinePayGoods1.getGoodsId());
            MallGoodsInfo mallGoodsById = goodsService.getMallGoodsById(onlinePayGoods1.getGoodsId());
            onlinePayGoodsDto.setProductId(mallGoodsById.getProductId());
            onlinePayGoodsDto.setProductName(mallGoodsById.getGoodsName());
            onlinePayGoodsDto.setSellingPrice(mallGoodsById.getSellingPrice().toString());
            OnlinePayGoods onlinePayGoodsItem = getOnlinePayGoodsItem(onlinePayGoods1.getGoodsId());
            onlinePayGoodsDto.setAccountPrice(onlinePayGoodsItem.getAccountPrice().toString());
            onlinePayGoodsDto.setInstitutionId(onlinePayGoods1.getInstitutionId());
            Institution institution = institutionService.getInstitutionInfo(onlinePayGoods1.getInstitutionId());
            onlinePayGoodsDto.setInstitutionName(institution.getName());
            onlinePayGoodsDtos.add(onlinePayGoodsDto);
        }
        pageDto.setList(onlinePayGoodsDtos);
    }

    /**
     * 新增一款经销产品
     *
     */
    public void save(OnlinePayGoodsDto onlinePayGoodsDto) {
        OnlinePayGoods onlinePayGoods = CopyUtil.copy(onlinePayGoodsDto, OnlinePayGoods.class);
        onlinePayGoods.setAccountPrice(BigDecimal.valueOf(Float.parseFloat(onlinePayGoodsDto.getAccountPrice())));
        //如果销售价格低于结算价格,则将销售价格设置为结算价格的1.15倍
        MallGoodsInfoExample mallGoodsInfoExample = new MallGoodsInfoExample();
        MallGoodsInfoExample.Criteria criteria = mallGoodsInfoExample.createCriteria();
        criteria.andProductIdEqualTo(onlinePayGoodsDto.getProductId());
        List<MallGoodsInfo> mallGoodsInfos = mallGoodsInfoMapper.selectByExample(mallGoodsInfoExample);
        if(mallGoodsInfos.size()==0){
            return;
        }
        MallGoodsInfo mallGoodsInfo = mallGoodsInfos.get(0);
        if(Float.parseFloat(mallGoodsInfo.getSellingPrice().toString()) <= Float.parseFloat(onlinePayGoodsDto.getAccountPrice())){
            BigDecimal sellingPrice = BigDecimal.valueOf(Float.parseFloat(onlinePayGoodsDto.getAccountPrice()) * 1.15);
            mallGoodsInfo.setSellingPrice(sellingPrice);
            mallGoodsInfoMapper.updateByPrimaryKey(mallGoodsInfo);
        }

        OnlinePayGoodsExample onlinePayGoodsExample = new OnlinePayGoodsExample();
        OnlinePayGoodsExample.Criteria criteria1 = onlinePayGoodsExample.createCriteria();
        criteria1.andProductIdEqualTo(onlinePayGoodsDto.getProductId());
        List<OnlinePayGoods> onlinePayGoodsList = onlinePayGoodsMapper.selectByExample(onlinePayGoodsExample);
        if (onlinePayGoodsList.size()>0) {
            this.update(onlinePayGoods);
        } else {
            this.insert(onlinePayGoods);
        }
    }

    /**
     * 新增
     */
    private void insert(OnlinePayGoods onlinePayGoods) {
        onlinePayGoods.setId(UuidUtil.getShortUuid());
        onlinePayGoods.setProductId(onlinePayGoods.getProductId());
        onlinePayGoods.setAccountPrice(onlinePayGoods.getAccountPrice());
        List<MallGoodsInfoDto> mallGoodsInfo = goodsService.getMallGoodsInfo(onlinePayGoods.getProductId());
        onlinePayGoods.setGoodsId(mallGoodsInfo.get(0).getGoodsId());
        Institution institution = institutionService.getInstitutionByLoginName(mallGoodsInfo.get(0).getSupplierLoginname());
        onlinePayGoods.setInstitutionId(institution.getId());

        onlinePayGoodsMapper.insert(onlinePayGoods);
    }

    /**
     * 更新
     */
    private void update(OnlinePayGoods onlinePayGoods) {
        OnlinePayGoodsExample onlinePayGoodsExample =new OnlinePayGoodsExample();
        OnlinePayGoodsExample.Criteria criteria = onlinePayGoodsExample.createCriteria();
        criteria.andProductIdEqualTo(onlinePayGoods.getProductId());
        onlinePayGoodsMapper.updateByExampleSelective(onlinePayGoods, onlinePayGoodsExample);
    }

    /**
     * 编辑经销产品
     * 编辑经销产品信息
     */
    public void edit() {

    }

    /**
     * 删除经销产品
     */
    public void delete(String productId) {
        OnlinePayGoodsExample onlinePayGoodsExample = new OnlinePayGoodsExample();
        OnlinePayGoodsExample.Criteria criteria = onlinePayGoodsExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        onlinePayGoodsMapper.deleteByExample(onlinePayGoodsExample);
    }


    /**
     * 根据商品ID获取在线支付商品信息
     * 通过商品ID查询对应的在线支付商品记录
     * 
     * @param goodsId 商品ID
     * @return 返回在线支付商品对象，如果不存在则返回null
     */
    public OnlinePayGoods getOnlinePayGoodsItem(Long goodsId){
        OnlinePayGoodsExample onlinePayGoodsExample = new OnlinePayGoodsExample();
        OnlinePayGoodsExample.Criteria criteria = onlinePayGoodsExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<OnlinePayGoods> onlinePayGoods = onlinePayGoodsMapper.selectByExample(onlinePayGoodsExample);
        if(onlinePayGoods.size()>0){
            return onlinePayGoods.get(0);
        }
        return null;
    }
}
