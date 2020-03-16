package api.app.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;



/**
 * 优惠券业务层接口
 * @author sky
 *
 */

public interface CouponService {


	/**
	 * 添加优惠券
	 */
	public JSONObject insertCoupon(Map<String,Object> map);
	
	/**
	 * 查询所有优惠券，根据分页
	 */
	public JSONObject getAllCoupon(Integer currentPage,Integer pageSize);
	
	/**
	 * 删除优惠券
	 */
	public JSONObject deleteCoupon(int[] co_id);
	
	/**
	 * 修改优惠券信息
	 */
	public JSONObject updateCoupon(Map<String,Object> map);
	
	/**
	 * 查询所有优惠券
	 */
	public JSONObject getAllCoupons();
	
	/**
	 * 查询优惠券条数
	 */
	public JSONObject totalCoupon();
	
	/**
	 * 根据优惠券id查询优惠券
	 */
	public Map<String,Object> getCouponByid(Integer uc_id);
	
	/**
	 * 改变优惠券字段，已用
	 */
	public void updateCouponStatus(Integer uc_id);
	
}
