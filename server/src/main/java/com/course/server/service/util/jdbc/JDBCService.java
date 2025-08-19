/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util.jdbc;

import com.course.server.dto.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.ecology.util.OaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class JDBCService {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCService.class);

    public List<RdmsSAPMaterialDto> getSAPDBMaterialList(int page, int pageSize) {
        List<RdmsSAPMaterialDto> sapMaterialDtos = new ArrayList<>();

        Properties dbProps = new Properties();
        dbProps.setProperty("user", OaConfig.getProperty("sql.server.user"));
        dbProps.setProperty("password", OaConfig.getProperty("sql.server.password"));

        // 计算偏移量
        int offset = (page - 1) * pageSize;

        try (Connection connection = DriverManager.getConnection(OaConfig.getProperty("sql.server.url"), dbProps);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT  MaterialNo, MaterialName, MaterialSpcf, WarehouseNo, BatchCodeSno  FROM VW_Material_Onhandim WHERE WarehouseNo like '%' + '301' + '%' ORDER BY MaterialNo OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"
             )) {

            // 设置参数
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, pageSize);

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            // 处理结果集
            while (resultSet.next()) {
                RdmsSAPMaterialDto material = new RdmsSAPMaterialDto();
                material.setMaterialNo(resultSet.getString("MaterialNo"));
                material.setMaterialName(resultSet.getString("MaterialName"));
                material.setMaterialSpcf(resultSet.getString("MaterialSpcf"));
                material.setWarehouseNo(resultSet.getString("WarehouseNo"));
                material.setWarehouseName(resultSet.getString("WarehouseName"));
                material.setNum(resultSet.getInt("Num"));
                material.setUnit(resultSet.getString("Unit"));

                sapMaterialDtos.add(material);
            }

        } catch (SQLException e) {
            // 记录日志
            LOG.error("数据库链接错误, 错误类名: JDBCService, 发生函数: getSAPDBMaterialList");
            throw new BusinessException(BusinessExceptionCode.DB_CONNECT_ERROR);
        }
        return sapMaterialDtos;
    }

    public List<RdmsSAPMaterialDto> getSAPDBMaterialList() {
        List<RdmsSAPMaterialDto> sapMaterialDtos = new ArrayList<>();

        Properties dbProps = new Properties();
        dbProps.setProperty("user", OaConfig.getProperty("sql.server.user"));
        dbProps.setProperty("password", OaConfig.getProperty("sql.server.password"));

        try (Connection connection = DriverManager.getConnection(OaConfig.getProperty("sql.server.url"), dbProps);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT  MaterialNo, MaterialName, MaterialSpcf, WarehouseNo, BatchCodeSno, Num, Unit  FROM VW_Material_Onhandim "
             )) {

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            // 处理结果集
            while (resultSet.next()) {
                RdmsSAPMaterialDto material = new RdmsSAPMaterialDto();
                material.setMaterialNo(resultSet.getString("MaterialNo"));
                material.setMaterialName(resultSet.getString("MaterialName"));
                material.setMaterialSpcf(resultSet.getString("MaterialSpcf"));
                material.setWarehouseNo(resultSet.getString("WarehouseNo"));
                material.setBatchCodeSno(resultSet.getString("BatchCodeSno"));
                material.setNum(resultSet.getInt("Num"));
                material.setUnit(resultSet.getString("Unit"));

                sapMaterialDtos.add(material);
            }

        } catch (SQLException e) {
            // 记录日志
            LOG.error("数据库链接错误, 错误类名: JDBCService, 发生函数: getSAPDBMaterialList");
            throw new BusinessException(BusinessExceptionCode.DB_CONNECT_ERROR);
        }
        return sapMaterialDtos;
    }

    /**
     * 根据BomCode(物料编码)和batchCode(批次号)进行物料查询
     * @param bomCode
     * @return
     */
    public List<RdmsSAPMaterialDto> getSAPMaterialByBomCodeAndBatchCode(String bomCode, String warehouseCode) {
        List<RdmsSAPMaterialDto> sapMaterialDtos = new ArrayList<>();

        Properties dbProps = new Properties();
        dbProps.setProperty("user", OaConfig.getProperty("sql.server.user"));
        dbProps.setProperty("password", OaConfig.getProperty("sql.server.password"));
        try (Connection connection = DriverManager.getConnection(OaConfig.getProperty("sql.server.url"), dbProps);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM VW_Material_Onhandim WHERE MaterialNo = ? AND WarehouseNo = ?; "
             )) {

            // 设置参数
            preparedStatement.setString(1, bomCode);
            preparedStatement.setString(2, warehouseCode);

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            // 处理结果集
            while (resultSet.next()) {
                RdmsSAPMaterialDto material = new RdmsSAPMaterialDto();
                material.setMaterialNo(resultSet.getString("MaterialNo"));
                material.setMaterialName(resultSet.getString("MaterialName"));
                material.setMaterialSpcf(resultSet.getString("MaterialSpcf"));
                material.setWarehouseNo(resultSet.getString("WarehouseNo"));
//                material.setWarehouseName(resultSet.getString("WarehouseName"));
                material.setBatchCodeSno(resultSet.getString("BatchCodeSno"));
                material.setPrice(resultSet.getDouble("Price"));
                material.setNum(resultSet.getInt("Num"));
                material.setUnit(resultSet.getString("Unit"));

                sapMaterialDtos.add(material);
            }

        } catch (SQLException e) {
            // 记录日志
            LOG.error("数据库链接错误, 错误类名: JDBCService, 发生函数: getSAPMaterialByBomCodeAndBatchCode");
            throw new BusinessException(BusinessExceptionCode.DB_CONNECT_ERROR);
        }
        return sapMaterialDtos;
    }


}
