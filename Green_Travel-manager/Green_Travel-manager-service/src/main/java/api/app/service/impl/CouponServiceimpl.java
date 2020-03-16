package api.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.CouponMapper;
import api.app.pojo.Coupon;
import api.app.service.CouponService;
import api.app.utils.RandomID;

@Service
public class CouponServiceimpl implements CouponService {

	@Autowired
	private CouponMapper couponMapper;
	
	/**
	 * 添加优惠券
	 */
	@Override
	public JSONObject insertCoupon(Map<String,Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
			String description = (String) map.get("description");
			Double integral = Double.valueOf(map.get("integral").toString());
			Integer day = Integer.parseInt(map.get("day").toString()) ;
			//保存数据
			RandomID randomId = new RandomID();
			Integer co_id = randomId.randomID();
			Coupon coupon = new Coupon();
			coupon.setCo_id(co_id);
			coupon.setDay(day);
			coupon.setDescription(description);
			coupon.setIntegral(integral);
			try {
				couponMapper.insertCoupon(coupon);			//添加优惠券
				code = 0;
				js.put("code", code);	
			}catch(Exception e) {
				js.put("code", code);
				e.printStackTrace();
				return js;
			}				
		return js;
		
	}

	/**
	 * 查询所有优惠券，根据分页
	 */
	@Override
	public JSONObject getAllCoupon(Integer currentPage, Integer pageSize) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			/*			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = "test_id";
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
				}*/	
			
			Integer cur = (currentPage - 1) * pageSize;			//页数
			List<Coupon> coupon = couponMapper.getAllCoupon(cur, pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", coupon);
			
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			//System.out.println("没有缓存！！！！！！");
			/*client.hset(CONTENT_KEY, redis_id+"", js.toString());			*/	
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}		
		return js;

	}

	/**
	 * 删除优惠券
	 */
	@Override
	public JSONObject deleteCoupon(int[] co_id) {
		int code = 1;
		JSONObject js = new JSONObject();
			try {
				couponMapper.deleteCoupon(co_id);
				code = 0;
				js.put("code", code);
			}catch(Exception e) {
				js.put("code", code);
				e.printStackTrace();
				return js;
			}			
		return js;
		
	}
	
	/**
	 * 修改优惠券信息
	 */
	@Override
	public JSONObject updateCoupon(Map<String,Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();

			String description = (String) map.get("description");
			Double integral = Double.valueOf(map.get("integral").toString());
			Integer day = (Integer) map.get("day");
			Integer co_id = (Integer) map.get("id");
			//保存数据
			Coupon coupon = new Coupon();
			coupon.setCo_id(co_id);
			coupon.setDay(day);
			coupon.setDescription(description);
			coupon.setIntegral(integral);
			try {
				couponMapper.updateCoupon(coupon);			//修改优惠券
				code = 0;				
				js.put("code", code);	
			}catch(Exception e) {
				js.put("code", code);
				e.printStackTrace();
				return js;
			}
		return js;
		
	}
	
	/**
	 * 查询所有优惠券
	 */
	@Override
	public JSONObject getAllCoupons(){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			/*			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = "test_id";
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
				}*/	
			
			List<Coupon> coupon =  couponMapper.getAllCoupons();
			code = 0;
			js.put("code", code);
			js.put("data", coupon);
			
			
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			//System.out.println("没有缓存！！！！！！");
			/*client.hset(CONTENT_KEY, redis_id+"", js.toString());			*/	
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}		
		return js;
	}

	/**
	 * 查询优惠券条数
	 */
	@Override
	public JSONObject totalCoupon() {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer total = couponMapper.totalCoupon();
			code = 0;
			js.put("code", code);
			js.put("data", total);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	
	/**
	 * 根据优惠券id查询优惠券
	 */
	@Override
	public Map<String,Object> getCouponByid(Integer uc_id){
		return couponMapper.getCouponByid(uc_id);
	}
	
	/**
	 * 改变优惠券字段，已用
	 */
	@Override
	public void updateCouponStatus(Integer uc_id) {
		couponMapper.updateCouponStatus(uc_id);
	}
}
