package api.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import api.app.pojo.Order;

/**
 * 订单表持久化接口
 * @author sky
 *
 */
public interface OrderMapper {

	/**
	 * 根据订单id查询订单
	 */
	public Order getOrderByid(int order_id);
	
	/**
	 * 插入订单
	 */
	public void insertOrder(Order order);
	
	/**
	 * 连表查询所有订单和具体商品信息
	 */
	public List<Order> getAllorder();
	
	/**
	 * left join连表查询所有订单和具体商品信息
	 */
	public List<Object> getAllorders(@Param("name")String name,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);
	
	/**
	 * 获取订单数
	 */
	public int getTotalorder(@Param("name")String name); 
	
	/**
	 * 删除订单
	 */
	public void deleteOrders(Integer[] order_id);
	/**
	 * 获取用户订单
	 */
	public List<Object> getUserOrder(@Param("user_id")Integer user_id,@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 删除用户订单，修改destory字段
	 */
	public void deleteUserOrder(Integer order_id);
	
	/**
	 * 获取用户详细订单
	 */
	public Object getOrderdetail(Integer order_id);
}
