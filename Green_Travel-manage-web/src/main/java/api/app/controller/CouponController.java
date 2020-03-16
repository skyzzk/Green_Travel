package api.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.CouponService;


/**
 * 优惠券控制层
 */

@RequestMapping("api")
@Controller
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	/**
	 * 查询所有优惠券,根据分页
	 */
	@ResponseBody
	@RequestMapping(value="getAllCoupon",method = RequestMethod.GET )
	public JSONObject getAllCoupon(Integer currentPage,Integer pageSize){	
		return couponService.getAllCoupon(currentPage, pageSize);
	}
	
	/**
	 * 添加优惠券
	 */
	@ResponseBody
	@RequestMapping(value="insertCoupon",method = RequestMethod.POST )
	public JSONObject insertCoupon(@RequestBody Map<String,Object> map){
		if(map.containsKey("description") && map.containsKey("integral") && map.containsKey("day")) {
				return couponService.insertCoupon(map);			//添加优惠券	
		}		
		JSONObject js = new JSONObject();;
		js.put("code", 1);	
		return js;
	}
	
	/**
	 * 删除优惠券
	 */
	@ResponseBody
	@RequestMapping(value="deleteCoupon",method = RequestMethod.POST )
	public  JSONObject deleteCoupon(@RequestBody Map<String, int[]> map){
		if(map.containsKey("ids")) {
			int[]ids = map.get("ids");
			return couponService.deleteCoupon(ids);
		}
		JSONObject js = new JSONObject();;
		js.put("code", 1);	
		return js;
	}

	/**
	 * 修改优惠券信息
	 */
	@ResponseBody
	@RequestMapping(value="updateCoupon",method = RequestMethod.POST )
	public JSONObject updateCoupon(@RequestBody Map<String,Object> map){
		if(map.containsKey("description") && map.containsKey("integral") && map.containsKey("day") && map.containsKey("id")) {
			return couponService.updateCoupon(map);
		}		
		JSONObject js = new JSONObject();;
		js.put("code", 1);	
		return js;
	}
	
	/**
	 * 查询所有优惠券
	 */
	@ResponseBody
	@RequestMapping(value="getAllCoupons",method = RequestMethod.GET )
	public JSONObject getAllCoupons(){
		return  couponService.getAllCoupons();

	}
	
	@ResponseBody
	@RequestMapping(value="totalCoupons",method = RequestMethod.GET )
	public JSONObject totalCoupons(){
		return  couponService.totalCoupon();
	}
}
