/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.account;

import com.course.server.domain.MallShipping;
import com.course.server.domain.MallShippingExample;
import com.course.server.domain.Member;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.ShippingFormDto;
import com.course.server.mapper.MallShippingMapper;
import com.course.server.service.MemberService;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class ShippingService {

	@Resource
	private MallShippingMapper shippingMapper;
	@Resource
	private MemberService memberService;

	public ResponseDto<Map<String, Integer>> add(ShippingFormDto form) {
		ResponseDto responseDto = new ResponseDto();
		Member customer = memberService.selectByPrimaryKey(form.getMemberId());
		MallShipping shipping = new MallShipping();
		BeanUtils.copyProperties(form, shipping);
		shipping.setCustomerId(customer.getId());
		String uuid = UuidUtil.getShortUuid();
		shipping.setId(uuid);
		shipping.setCreateTime(new Date());
		shipping.setUpdateTime(new Date());
		int row = shippingMapper.insertSelective(shipping);
		if (row == 0) {
			responseDto.setSuccess(false);
			responseDto.setMessage("地址数据错误!");
			return responseDto;
		}

		Map<String, String> map = new HashMap<>();
		map.put("shippingId", shipping.getId());
		responseDto.setContent(map);
		return responseDto;
	}

	public ResponseDto delete(String shippingId) {
		ResponseDto responseDto = new ResponseDto();
		MallShippingExample mallShippingExample = new MallShippingExample();
		MallShippingExample.Criteria criteria = mallShippingExample.createCriteria();
		criteria.andIdEqualTo(shippingId);
		int row = shippingMapper.deleteByExample(mallShippingExample);
		if (row == 0) {
			responseDto.setSuccess(false);
			responseDto.setMessage("地址数据错误!");
			return responseDto;
		}
		return responseDto;
	}

	public ResponseDto update(String shippingId, ShippingFormDto form) {
		ResponseDto responseDto = new ResponseDto();
		Member customer = memberService.selectByPrimaryKey(form.getMemberId());
		MallShipping shipping = new MallShipping();
		BeanUtils.copyProperties(form, shipping);
		shipping.setCustomerId(customer.getId());
		shipping.setId(shippingId);
		shipping.setUpdateTime(new Date());
		int row = shippingMapper.updateByPrimaryKeySelective(shipping);
		if (row == 0) {
			responseDto.setSuccess(false);
			responseDto.setMessage("地址数据错误!");
			return responseDto;
		}
		return responseDto;
	}

	public ResponseDto list(String memberId, Integer pageNum, Integer pageSize) {
		ResponseDto responseDto = new ResponseDto();
		Member customer = memberService.selectByPrimaryKey(memberId);
		PageHelper.startPage(pageNum, pageSize);
		MallShippingExample mallShippingExample = new MallShippingExample();
		MallShippingExample.Criteria criteria = mallShippingExample.createCriteria();
		criteria.andCustomerIdEqualTo(customer.getId());
		List<MallShipping> shippings = shippingMapper.selectByExample(mallShippingExample);
		PageInfo pageInfo = new PageInfo(shippings);
		responseDto.setContent(pageInfo);
		return responseDto;
	}

	public MallShipping selectByMemberIdAndShippingId(String memberId, String shippingId) {
		MallShippingExample mallShippingExample = new MallShippingExample();
		MallShippingExample.Criteria criteria = mallShippingExample.createCriteria();
		criteria.andCustomerIdEqualTo(memberId).andIdEqualTo(shippingId);
		List<MallShipping> shippings = shippingMapper.selectByExample(mallShippingExample);
		if(shippings.size()>0){
			return shippings.get(0);
		}
		return null;
	}
}
