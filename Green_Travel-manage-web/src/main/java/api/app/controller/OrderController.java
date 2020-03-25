package api.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.OrderService;
/**
 * 订单表控制层
 * @author sky
 *
 */


@Controller
@RequestMapping("api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 兑换商品，写进订单
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "conversion_order", method = RequestMethod.POST)
	public JSONObject conversion_order(@RequestBody Map<String,Integer> map) {
		if(map.containsKey("user_id" )&& map.containsKey("uc_id") && map.containsKey("com_id")) {
			return orderService.insertOrder(map);
		}
		JSONObject js = new JSONObject();
		js.put("code",1);
		return js;
	}
	
	/**
	 * 获取所有订单
	 */
	
	@ResponseBody
	@RequestMapping(value = "getAllorder", method = RequestMethod.GET)
	public JSONObject getAllorder(String name,Integer currentPage,Integer pageSize) {
		return orderService.getAllorders(name,currentPage,pageSize);

	}
	
	/**
	 * 获取订单数
	 */
	@ResponseBody
	@RequestMapping(value = "getTotalorder", method = RequestMethod.GET)
	public JSONObject getTotalorder(String name){
		return orderService.getTotalorder(name);
	}
	
	/**
	 * 删除订单
	 */
	@ResponseBody
	@RequestMapping(value = "deleteOrders", method = RequestMethod.POST)
	public JSONObject deleteOrders(@RequestBody Map<String, Integer[]> map) {
		if(map.containsKey("ids")){
			Integer[] order_id = map.get("ids");
			return orderService.deleteOrders(order_id);
		}
		JSONObject js = new JSONObject();
		js.put("code",1);
		return js;
	}
	
	/**
	 * 获取用户订单
	 */
	@ResponseBody
	@RequestMapping(value = "getUserOrder", method = RequestMethod.GET)
	public JSONObject getUserOrder(Integer user_id,Integer currentPage,Integer pageSize) {
		return orderService.getUserOrder(user_id, currentPage, pageSize);
	}
	
	/**
	 * 用户删除订单
	 */
	@ResponseBody
	@RequestMapping(value = "deleteUserOrder", method = RequestMethod.POST)
	public JSONObject deleteUserOrder(@RequestBody Map<String,Integer> map) {
		if(map.containsKey("id")) {
			return orderService.deleteUserOrder(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	/**
	 * 获取用户详细订单
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderdetail", method = RequestMethod.GET)
	public JSONObject getOrderdetail(Integer id) {
		return orderService.getOrderdetail(id);
	}
}
