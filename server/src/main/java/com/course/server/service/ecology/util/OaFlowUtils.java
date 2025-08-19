/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.utils.StringUtils;
import com.course.server.dto.rdms.RdmsSAPMaterialDto;
import com.course.server.enums.rdms.MaterialUsageModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class OaFlowUtils<A> {

    public OaRes doCreateRequest(String apiUrl, String userId, Integer workflowId, String title, A mainData, DetailData... details) throws Exception {
        log.info("已进入新建OA流程，workflowId={}...", workflowId);
        String path = OaConfig.getHost() + apiUrl;
        Map<String, String> headers = new HashMap<>();
        headers.put("token", OaTokenUtils.getToken());
        headers.put("appid", OaConfig.getAppId());

        if (!ObjectUtils.isEmpty(userId)) {
            //对secret使用公钥加密后的字符串
            String u = RsaEncrypt.encrypt(userId, OaConfig.getSpk());
            headers.put("userid", u);
        }
        Create oc = new Create();
        oc.setWorkflowId(workflowId);
        oc.setMainData(objToOaMainData(mainData).toString());
        oc.setDetailData(objToOaDetailData(details).toString());
        oc.setRequestName(title);
        Map<String, Object> body = BeanUtil.beanToMap(oc);
        HttpRequest request = HttpRequest.post(path).addHeaders(headers).form(body);

        log.info("header:{}", JSONUtil.parseObj(request.headers()));
        log.info("form:{}", JSONUtil.parseObj(request.form()));
        String response = request.execute().body();
        log.info("调用创建流程接口完毕,OA出参：{}", response);
        OaRes oaRes = JSONUtil.toBean(response, OaRes.class);
        return oaRes;
    }

    private JSONArray objToOaDetailData(DetailData... details) throws ServerException {
        if (details.length == 0)
            return new JSONArray();
        return JSONUtil.parseArray(details);
    }

    private JSONArray objToOaMainData(A obj) {
//        List<Field> fields = objToOaFields(obj);
        return JSONUtil.parseArray(obj);
    }

    private static List<Field> objToOaFields(Object obj) {
        Map<String, Object> map = BeanUtil.beanToMap(obj);
        List<Field> fields = new ArrayList<>();
        map.forEach((k, v) -> {
            if (!ObjectUtils.isEmpty(v)) {
                Field field = new Field(k, v);
                fields.add(field);
            }
        });
        return fields;
    }

    /**
     * 实现标准物料对象字段和 Raykeen的物料数据库字段对其
     * @param sapMaterialDto
     * @return
     */
    public WorkflowRequestTableRecord createOADetailDateRecord(@NotNull RdmsSAPMaterialDto sapMaterialDto) {

        WorkflowRequestTableRecord workflowRequestTableRecord = new WorkflowRequestTableRecord();
        workflowRequestTableRecord.setRecordOrder(sapMaterialDto.getCode());
        List<Field> workflowRequestTableFields = new ArrayList<>();

        Field<String> detailItemData1 = new Field<>();
        detailItemData1.setFieldName("wldm");  // 物料编号
        detailItemData1.setFieldValue(sapMaterialDto.getMaterialNo());
        workflowRequestTableFields.add(detailItemData1);

        Field<String> detailItemData2 = new Field<>();
        detailItemData2.setFieldName("ggxh"); // 规格型号
        detailItemData2.setFieldValue(sapMaterialDto.getMaterialSpcf());
        workflowRequestTableFields.add(detailItemData2);

        Field<String> detailItemData3 = new Field<>();
        detailItemData3.setFieldName("wlms"); // 物料名称
        detailItemData3.setFieldValue(sapMaterialDto.getMaterialName());
        workflowRequestTableFields.add(detailItemData3);

        Field<String> detailItemData4 = new Field<>();
        detailItemData4.setFieldName("sl"); // 数量
        detailItemData4.setFieldValue(sapMaterialDto.getNum().toString());
        workflowRequestTableFields.add(detailItemData4);

        Field<String> detailItemData5 = new Field<>();
        detailItemData5.setFieldName("pchxlh"); // 批次号
        detailItemData5.setFieldValue(sapMaterialDto.getBatchCodeSno());
        workflowRequestTableFields.add(detailItemData5);

        Field<String> detailItemData6 = new Field<>();
        detailItemData6.setFieldName("yfxmdm"); // 研发项目代码
        detailItemData6.setFieldValue(sapMaterialDto.getReserve1());
        workflowRequestTableFields.add(detailItemData6);

        Field<String> detailItemData10 = new Field<>();
        detailItemData10.setFieldName("xmdm"); // 研发项目代码
        detailItemData10.setFieldValue(sapMaterialDto.getProjectCode());
        workflowRequestTableFields.add(detailItemData10);

        Field<String> detailItemData9 = new Field<>();
        detailItemData9.setFieldName("yfxmmc"); // 研发项目名称
        detailItemData9.setFieldValue(sapMaterialDto.getReserve2());
        workflowRequestTableFields.add(detailItemData9);

        Field<String> detailItemData11 = new Field<>();
        detailItemData11.setFieldName("xmmc"); // 研发项目名称
        detailItemData11.setFieldValue(sapMaterialDto.getProjectName());
        workflowRequestTableFields.add(detailItemData11);

        Field<String> detailItemData7 = new Field<>();
        detailItemData7.setFieldName("ck"); // 仓库编号
        detailItemData7.setFieldValue(sapMaterialDto.getWarehouseNo());
        workflowRequestTableFields.add(detailItemData7);

        Field<String> detailItemData8 = new Field<>();
        detailItemData8.setFieldName("yjsfhtk");  //是否会退库  是 或 否
        if(sapMaterialDto.getUsageMode().equals(MaterialUsageModeEnum.BORROW.getStatus())){
            detailItemData8.setFieldValue("0");
        }else{
            detailItemData8.setFieldValue("1");
        }
        workflowRequestTableFields.add(detailItemData8);

        workflowRequestTableRecord.setWorkflowRequestTableFields(workflowRequestTableFields);

        return workflowRequestTableRecord;
    }

    public WorkflowRequestTableRecord createOADetailDateRecord_materialReturn(@NotNull RdmsSAPMaterialDto sapMaterialDto) {

        WorkflowRequestTableRecord workflowRequestTableRecord = new WorkflowRequestTableRecord();
        workflowRequestTableRecord.setRecordOrder(sapMaterialDto.getCode());
        List<Field> workflowRequestTableFields = new ArrayList<>();

        Field<String> detailItemData1 = new Field<>();
        detailItemData1.setFieldName("wldm");  // 物料编号
        detailItemData1.setFieldValue(sapMaterialDto.getMaterialNo());
        workflowRequestTableFields.add(detailItemData1);

        Field<String> detailItemData2 = new Field<>();
        detailItemData2.setFieldName("ggxh"); // 规格型号
        detailItemData2.setFieldValue(sapMaterialDto.getMaterialSpcf());
        workflowRequestTableFields.add(detailItemData2);

        Field<String> detailItemData3 = new Field<>();
        detailItemData3.setFieldName("wlms"); // 物料名称/描述
        detailItemData3.setFieldValue(sapMaterialDto.getMaterialName());
        workflowRequestTableFields.add(detailItemData3);

        Field<String> detailItemData4 = new Field<>();
        detailItemData4.setFieldName("sl"); // 数量
        if(!ObjectUtils.isEmpty(sapMaterialDto.getNum())){
            detailItemData4.setFieldValue(sapMaterialDto.getNum().toString());
            workflowRequestTableFields.add(detailItemData4);
        }else{
            detailItemData4.setFieldValue("0");
            workflowRequestTableFields.add(detailItemData4);
        }

        Field<String> detailItemData5 = new Field<>();
        detailItemData5.setFieldName("pchxlh"); // 批次号
        detailItemData5.setFieldValue(sapMaterialDto.getBatchCodeSno());
        workflowRequestTableFields.add(detailItemData5);

        Field<String> detailItemData10 = new Field<>();
        detailItemData10.setFieldName("xmdm"); // 项目代码
        detailItemData10.setFieldValue(sapMaterialDto.getProjectCode());
        workflowRequestTableFields.add(detailItemData10);

        Field<String> detailItemData12 = new Field<>();
        detailItemData12.setFieldName("yfxmdm"); // 原始项目代码
        detailItemData12.setFieldValue(sapMaterialDto.getReserve1());   //原始项目代码
        workflowRequestTableFields.add(detailItemData12);

        Field<String> detailItemData11 = new Field<>();
        detailItemData11.setFieldName("xmmc"); // 项目名称
        detailItemData11.setFieldValue(sapMaterialDto.getProjectName());
        workflowRequestTableFields.add(detailItemData11);

        Field<String> detailItemData7 = new Field<>();
        detailItemData7.setFieldName("ck"); // 仓库编号
        detailItemData7.setFieldValue(sapMaterialDto.getWarehouseNo());
        workflowRequestTableFields.add(detailItemData7);

        Field<String> detailItemData8 = new Field<>();
        detailItemData8.setFieldName("sfjy");  //是否检查  是:0 或 否:1
        if(!ObjectUtils.isEmpty(sapMaterialDto.getIsRecheck())){
            detailItemData8.setFieldValue(sapMaterialDto.getIsRecheck().toString());
            workflowRequestTableFields.add(detailItemData8);
        }else{
            detailItemData8.setFieldValue("0");
            workflowRequestTableFields.add(detailItemData8);
        }

        Field<String> detailItemData9 = new Field<>();
        detailItemData9.setFieldName("bjdh"); // 报检单号
        detailItemData9.setFieldValue(sapMaterialDto.getRecheckItemNo());
        workflowRequestTableFields.add(detailItemData9);

        Field<String> detailItemData13 = new Field<>();
        detailItemData13.setFieldName("mbck"); // 目标仓库
        detailItemData13.setFieldValue(sapMaterialDto.getTargetWarehouseCode());
        workflowRequestTableFields.add(detailItemData13);

        Field<String> detailItemData6 = new Field<>();
        detailItemData6.setFieldName("gllllc"); // 关联领料AO申请ID
        detailItemData6.setFieldValue(sapMaterialDto.getRequestId());
        workflowRequestTableFields.add(detailItemData6);

        workflowRequestTableRecord.setWorkflowRequestTableFields(workflowRequestTableFields);

        return workflowRequestTableRecord;
    }

    public List<WorkflowRequestTableRecord> createOADetailDateRecordList(@NotNull List<RdmsSAPMaterialDto> sapMaterialDtoList) {
        List<WorkflowRequestTableRecord> workflowRequestTableRecords = new ArrayList<>();
        for(RdmsSAPMaterialDto materialDto: sapMaterialDtoList){
            WorkflowRequestTableRecord oaDetailDateRecord = createOADetailDateRecord(materialDto);
            workflowRequestTableRecords.add(oaDetailDateRecord);
        }
        return workflowRequestTableRecords;
    }

    public List<WorkflowRequestTableRecord> createOADetailDateRecordList_materialReturn(@NotNull List<RdmsSAPMaterialDto> sapMaterialDtoList) {
        List<WorkflowRequestTableRecord> workflowRequestTableRecords = new ArrayList<>();
        for(RdmsSAPMaterialDto materialDto: sapMaterialDtoList){
            WorkflowRequestTableRecord oaDetailDateRecord = createOADetailDateRecord_materialReturn(materialDto);
            workflowRequestTableRecords.add(oaDetailDateRecord);
        }
        return workflowRequestTableRecords;
    }



}
