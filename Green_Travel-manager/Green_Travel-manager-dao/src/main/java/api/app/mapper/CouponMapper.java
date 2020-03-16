package api.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import api.app.pojo.Coupon;

/**
 * 优惠券持久化接口
 * @author sky
 *
 */
public interface CouponMapper {

	/**
	 * 添加优惠券
	 */
	public void insertCoupon(Coupon coupon);
	
	/**
	 * 查询所有优惠券，根据分页
	 */
	public List<Coupon> getAllCoupon(@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 删除优惠券
	 */
	public void deleteCoupon(int[] co_id);
	
	/**
	 * 修改优惠券信息
	 */
	public void updateCoupon(Coupon coupon);
	
	/**
	 * 查询所有优惠券
	 */
	public List<Coupon> getAllCoupons();
	
	/**
	 * 查询优惠券条数
	 */
	public Integer totalCoupon();
	
	/**
	 * 根据优惠券id查询优惠券
	 */
	public Map<String,Object> getCouponByid(Integer uc_id);
	
	/**
	 * 改变优惠券字段，已用
	 */
	public void updateCouponStatus(Integer uc_id);
	
}
