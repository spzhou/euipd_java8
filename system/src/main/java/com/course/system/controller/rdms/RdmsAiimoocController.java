/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsUser;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsUserDto;
import com.course.server.dto.rdms.RdmsUserDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.rdms.RdmsCustomerUserService;
import com.course.server.service.rdms.RdmsUserService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@RestController
@RequestMapping("/aiimooc")
public class RdmsAiimoocController {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsUserController.class);
    public static final String BUSINESS_NAME = "开发者";

    @Resource
    private RdmsUserService rdmsUserService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;

    @PostMapping("/getRegisterUserInfo/{phone}")
    @Transactional
    public ResponseDto getRegisterUserInfo(@PathVariable String phone) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        RdmsUserDto userDto = new RdmsUserDto();

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection connection;
        PreparedStatement prepareStatement;
        try {
            dataSource.setDriverClass( "com.mysql.cj.jdbc.Driver" );
            dataSource.setJdbcUrl( "jdbc:mysql://rm-uf6gtv7wr3f54g707lo.mysql.rds.aliyuncs.com:3306/aiimooc_jenvei" );
            dataSource.setUser("zhousp");
            dataSource.setPassword("YOUR_PASSWORD");

            connection = dataSource.getConnection();
            String sql = "SELECT * FROM destoon_member WHERE mobile=" + phone + ";";
            prepareStatement = connection.prepareStatement(sql );
//            prepareStatement.executeUpdate(); //新建 插入等用这个函数
            ResultSet rs = prepareStatement.executeQuery(sql);
//            System.out.println("用户数据");
            int flag = 0;
            while (rs.next()) {
                flag++;
//                System.out.println(rs.getString(1) + "\t" + rs.getString("truename"));
                userDto.setId(null);
                userDto.setLoginName(rs.getString("mobile"));
                if(!ObjectUtils.isEmpty(rs.getString("truename"))){
                    userDto.setTrueName(rs.getString("truename"));
                }else{
                    userDto.setTrueName(rs.getString("mobile"));
                }
                userDto.setSubject(rs.getString("career"));
                userDto.setCompanyName(rs.getString("department"));
                userDto.setDescription(rs.getString("signature"));
                String regtime = rs.getString("regtime");
                long time1 = Long.valueOf(regtime);
                Date date1 = new Date(time1*1000);   //对应的就是时间戳对应的Date
                userDto.setCreateTime(date1);

                //判断这个user是否已经在user表中,如果没有则创建这个user;如果有了,则不做任何改动
                LoginUserDto byLoginName = rdmsUserService.findByLoginName(userDto.getLoginName());
                if(byLoginName == null){
                    rdmsUserService.save(userDto);
                }
            }
            if(flag > 0){
                responseDto.setContent(userDto);
            }else{
                responseDto.setContent(null);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            dataSource.close();
        }
        return responseDto;
    }
}
