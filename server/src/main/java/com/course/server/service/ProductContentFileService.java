/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.domain.ProductContentFile;
import com.course.server.domain.ProductContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.ProductContentFileDto;
import com.course.server.mapper.CourseContentFileMapper;
import com.course.server.mapper.ProductContentFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductContentFileService {

    @Resource
    private ProductContentFileMapper productContentFileMapper;

    /**
     * 列表查询
     * 根据产品ID查询产品内容文件列表，按图片分类排序
     * 
     * @param courseId 产品ID
     * @return 返回产品内容文件数据传输对象列表
     */
    public List<ProductContentFileDto> list(String courseId) {
        ProductContentFileExample example = new ProductContentFileExample();
        ProductContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(courseId);
        List<ProductContentFile> fileList = productContentFileMapper.selectByExample(example);

        fileList = fileList.stream().sorted(Comparator.comparing(ProductContentFile::getImgClass)).collect(Collectors.toList());
        return CopyUtil.copyList(fileList, ProductContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新产品内容文件信息
     * 
     * @param courseContentFileDto 产品内容文件数据传输对象
     */
    public void save(ProductContentFileDto courseContentFileDto) {
        ProductContentFile productContentFile = CopyUtil.copy(courseContentFileDto, ProductContentFile.class);
        if (ObjectUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(productContentFile);
        } else {
            this.update(productContentFile);
        }
    }

    /**
     * 新增
     * 创建新的产品内容文件记录
     * 
     * @param productContentFile 产品内容文件对象
     */
    private void insert(ProductContentFile productContentFile) {
        productContentFile.setId(UuidUtil.getShortUuid());
        productContentFileMapper.insert(productContentFile);
    }

    /**
     * 更新
     * 更新产品内容文件信息
     * 
     * @param productContentFile 产品内容文件对象
     */
    private void update(ProductContentFile productContentFile) {
        productContentFileMapper.updateByPrimaryKey(productContentFile);
    }

    /**
     * 删除
     * 根据ID删除产品内容文件记录
     * 
     * @param id 产品内容文件ID
     */
    public void delete(String id) {
        productContentFileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据图片分类,获取产品图片
     * 根据产品ID和图片分类获取产品图片URL列表
     * 
     * @param id 产品ID
     * @param imgClass 图片分类
     * @return 返回图片URL列表，如果不存在则返回null
     */
    public List<String> getProductImgUrl(String id, String imgClass){
        ProductContentFileExample productContentFileExample = new ProductContentFileExample();
        ProductContentFileExample.Criteria criteria = productContentFileExample.createCriteria();
        criteria.andProductIdEqualTo(id).andImgClassEqualTo(imgClass);
        List<ProductContentFile> productContentFiles = productContentFileMapper.selectByExample(productContentFileExample);
        if(productContentFiles.size() == 0){
            return null;
        }
        List<ProductContentFile> productContentFilesTemp = new ArrayList<>();
        for(ProductContentFile productContentFile : productContentFiles){
            //如果不想显示某张图片,只要将其顺序号设置为0 就可以了
            if(productContentFile.getOrderNum() !=null && productContentFile.getOrderNum() != 0){
                productContentFilesTemp.add(productContentFile);
            }
        }
        productContentFilesTemp = productContentFilesTemp.stream().sorted(Comparator.comparing(ProductContentFile::getOrderNum)).collect(Collectors.toList());

        List<String> imgList = new ArrayList<>();
        for(ProductContentFile productContentFile : productContentFilesTemp){
            imgList.add(productContentFile.getUrl());
        }

        return imgList;
    }
}
