package api.app.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.Order;

/**
 * s订单表业务接口
 * @author sky
 *
 */
public interface OrderService {
	/**
	 * 根据订单ID查询订单
	 */
	public Order getOrderByid(Integer order_id);
	
	/**
	 * 插入订单
	 */
	public JSONObject insertOrder(Map<String,Integer> map);
	
	/**
	 * 连表查询所有订单和具体商品信息
	 */
	public List<Order> getAllorder();
	
	/**
	 * left join连表查询所有订单和具体商品信息
	 */
	public JSONObject getAllorders(String name,Integer currentPage,Integer pageSize);
	
	/**
	 * 获取订单数
	 */
	public JSONObject getTotalorder(String name); 
	
	/**
	 * 删除订单
	 */
	public JSONObject deleteOrders(Integer[] order_id);
	
	
	/**
	 * 获取用户订单
	 */
	public JSONObject getUserOrder(Integer user_id,Integer currentPage,Integer pageSize);
	/**
	 * 删除用户订单，修改destory字段
	 */
	public JSONObject deleteUserOrder(Map<String,Integer> map);
	
	/**
	 * 获取用户详细订单
	 */
	public JSONObject getOrderdetail(Integer order_id);
}
