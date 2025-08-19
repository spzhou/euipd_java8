/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionGoods;
import com.course.server.domain.InstitutionGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionGoodsMapper {
    long countByExample(InstitutionGoodsExample example);

    int deleteByExample(InstitutionGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InstitutionGoods record);

    int insertSelective(InstitutionGoods record);

    List<InstitutionGoods> selectByExample(InstitutionGoodsExample example);

    InstitutionGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InstitutionGoods record, @Param("example") InstitutionGoodsExample example);

    int updateByExample(@Param("record") InstitutionGoods record, @Param("example") InstitutionGoodsExample example);

    int updateByPrimaryKeySelective(InstitutionGoods record);

    int updateByPrimaryKey(InstitutionGoods record);
}