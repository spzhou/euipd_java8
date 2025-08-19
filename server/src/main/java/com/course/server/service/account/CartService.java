/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.account;

import com.course.server.domain.MallGoodsInfo;
import com.course.server.domain.MallGoodsInfoExample;
import com.course.server.domain.MallShoppingCartItem;
import com.course.server.domain.MallShoppingCartItemExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.MallGoodsInfoDto;
import com.course.server.dto.MallShoppingCartItemDto;
import com.course.server.dto.ProductDto;
import com.course.server.enums.GoodsClassEnum;
import com.course.server.enums.ServiceResultEnum;
import com.course.server.mapper.MallGoodsInfoMapper;
import com.course.server.mapper.MallShoppingCartItemMapper;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final Logger LOG = LoggerFactory.getLogger(CartService.class);

    @Resource
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;
    @Resource
    private MallGoodsInfoMapper mallGoodsInfoMapper;


    /**
     * 保存购物车项
     * 将购物车项信息保存到数据库
     * 
     * @param mallShoppingCartItem 购物车项对象
     */
    public void saveCartItem(MallShoppingCartItem mallShoppingCartItem){
        mallShoppingCartItemMapper.insert(mallShoppingCartItem);
    }

    //查询某个客户的所有购物项
    public List<MallShoppingCartItemDto> getMyShoppingCartItems(String customerId) {
        List<MallShoppingCartItemDto> mallMallShoppingCartItemDtoS = new ArrayList<>();
        MallShoppingCartItemExample mallShoppingCartItemExample = new MallShoppingCartItemExample();
        MallShoppingCartItemExample.Criteria criteria = mallShoppingCartItemExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByExample(mallShoppingCartItemExample);
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> mallGoodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoodsInfo> mallGoodsInfos = new ArrayList<>();
            for(Long goodsId: mallGoodsIds){
                MallGoodsInfo goodsInfo = mallGoodsInfoMapper.selectByPrimaryKey(goodsId);
                mallGoodsInfos.add(goodsInfo);
            }
            Map<Long, MallGoodsInfo> mallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoodsInfos)) {
                mallGoodsMap = mallGoodsInfos.stream().collect(Collectors.toMap(MallGoodsInfo::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                MallShoppingCartItemDto mallShoppingCartItemDto = CopyUtil.copy(mallShoppingCartItem, MallShoppingCartItemDto.class);

                if (mallGoodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    MallGoodsInfo mallGoodsTemp = mallGoodsMap.get(mallShoppingCartItem.getGoodsId());
                    mallShoppingCartItemDto.setImage(mallGoodsTemp.getImage());

                    String goodsName = mallGoodsTemp.getGoodsName();
                    String goodsIntro = mallGoodsTemp.getGoodsIntro();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    mallShoppingCartItemDto.setGoodsName(goodsName);
                    mallShoppingCartItemDto.setGoodsIntro(goodsIntro);
                    mallShoppingCartItemDto.setSellingPrice(mallGoodsTemp.getSellingPrice());
                    mallMallShoppingCartItemDtoS.add(mallShoppingCartItemDto);
                }
            }
        }
        return mallMallShoppingCartItemDtoS;
    }

    /**
     * 根据购物车项ID获取购物车项信息
     * 通过购物车项ID查询对应的购物车项详情
     * 
     * @param cartItemId 购物车项ID
     * @return 返回购物车项对象
     */
    public MallShoppingCartItem getMallCartItemById(String cartItemId) {
        return mallShoppingCartItemMapper.selectByPrimaryKey(cartItemId);
    }


    /**
     * 更新购物车项
     * 更新购物车项的数量、选中状态等信息
     * 
     * @param mallShoppingCartItem 购物车项对象
     * @return 返回操作结果字符串
     */
    public String updateMallCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem mallShoppingCartItemUpdate = mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItem.getCartItemId());
        if (mallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }

        //当前登录账号的customerId与待修改的cartItem中customerId不同，返回错误
        if (!mallShoppingCartItemUpdate.getCustomerId().equals(mallShoppingCartItem.getCustomerId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        /*if (mallShoppingCartItem.getGoodsCount().equals(mallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }*/
        mallShoppingCartItemUpdate.setGoodsCount(mallShoppingCartItem.getGoodsCount());
        mallShoppingCartItemUpdate.setSelected(mallShoppingCartItem.getSelected());
        mallShoppingCartItemUpdate.setBuyfrom(mallShoppingCartItem.getBuyfrom());
        mallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (mallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 根据ID删除购物车项
     * 删除指定ID的购物车项，需要验证客户ID权限
     * 
     * @param shoppingCartItemId 购物车项ID
     * @param customerId 客户ID
     * @return 返回删除是否成功
     */
    public Boolean deleteById(String shoppingCartItemId, String customerId) {
        MallShoppingCartItem mallShoppingCartItem = mallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!customerId.equals(mallShoppingCartItem.getCustomerId())) {
            return false;
        }
        return mallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    /**
     * 全选购物车项
     * 将指定客户的所有购物车项设置为选中状态
     * 
     * @param customerId 客户ID
     * @return 返回更新后的购物车项列表
     */
    public List<MallShoppingCartItem> selectAll(String customerId) {
        MallShoppingCartItemExample mallShoppingCartItemExample = new MallShoppingCartItemExample();
        MallShoppingCartItemExample.Criteria criteria = mallShoppingCartItemExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<MallShoppingCartItem> shoppingCartItems = mallShoppingCartItemMapper.selectByExample(mallShoppingCartItemExample);
        List<MallShoppingCartItem> tmpShoppingCartItem = new ArrayList<>();
        for(MallShoppingCartItem shoppingCartItem : shoppingCartItems){
            shoppingCartItem.setSelected(true);
            mallShoppingCartItemMapper.updateByPrimaryKey(shoppingCartItem);
            tmpShoppingCartItem.add(shoppingCartItem);
        }
        return tmpShoppingCartItem;
    }

    /**
     * 取消全选购物车项
     * 将指定客户的所有购物车项设置为未选中状态
     * 
     * @param customerId 客户ID
     * @return 返回更新后的购物车项列表
     */
    public List<MallShoppingCartItem> unSelectAll(String customerId) {
        MallShoppingCartItemExample mallShoppingCartItemExample = new MallShoppingCartItemExample();
        MallShoppingCartItemExample.Criteria criteria = mallShoppingCartItemExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<MallShoppingCartItem> shoppingCartItems = mallShoppingCartItemMapper.selectByExample(mallShoppingCartItemExample);        List<MallShoppingCartItem> tmpShoppingCartItem = new ArrayList<>();
        for(MallShoppingCartItem shoppingCartItem : shoppingCartItems){
            shoppingCartItem.setSelected(false);
            mallShoppingCartItemMapper.updateByPrimaryKey(shoppingCartItem);
            tmpShoppingCartItem.add(shoppingCartItem);
        }
        return tmpShoppingCartItem;
    }

}
