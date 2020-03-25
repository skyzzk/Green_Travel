package api.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.CommodityMapper;
import api.app.mapper.CouponMapper;
import api.app.mapper.OrderMapper;
import api.app.mapper.UserMapper;
import api.app.pojo.Order;
import api.app.service.OrderService;
import api.app.utils.RandomID;

/**
 * s订单业务接口实现类
 * @author sky
 *
 */
@Service
public class OrderServiceimpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private CommodityMapper commodityMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CouponMapper couponMapper;
	
	/**
	 * 根据订单id查询订单
	 */
	@Override
	public Order getOrderByid(Integer order_id) {
		return orderMapper.getOrderByid(order_id);
	}
	
	/**
	 * 插入订单
	 */
	@Override
	public JSONObject insertOrder(Map<String,Integer> map) {
		int code = 1;
		JSONObject js = new JSONObject();
		//获取用户id和商品id
		Integer user_id = map.get("user_id");
		Integer com_id = map.get("com_id");
		Integer uc_id = map.get("uc_id");	
		try {
			
			Integer num = commodityMapper.getstrorcByid(com_id);					//当前商品存储量
			if(num < 1) {														//不够返回
				js.put("code", code);
				return js;	
			}		
			//获取当前用户积分和商品积分，判断是否足够积分兑换
			Double user_integral = userMapper.getIntegralById(user_id);
			Double com_integral = commodityMapper.getIntegralById(com_id);
			
			//随机形成订单id
			RandomID randomId = new RandomID();
			Integer id = randomId.randomID();
			//随机生成序列号
			String serial_number = "green_travel-" +  RandomStringUtils.randomAlphanumeric(10);
			//生成订单时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			Date date = sdf.parse(sdf.format(new Date()));
			//数量
			int quantity = 1;
			//保存信息
			Order order = new Order();
			order.setCom_id(com_id);
			order.setOrder_id(id);
			order.setUser_id(user_id);
			order.setQuantity(quantity);
			order.setCreate_time(date);
			order.setSerial_number(serial_number);
		
			//判断是否使用优惠券
			if(uc_id != -1) {
				//判断优惠券是否用(已用，过期)
				Map<String,Object> coupon = couponMapper.getCouponByid(uc_id);
				Integer status = Integer.parseInt(coupon.get("status").toString());				//优惠券是否使用过
				if(status == 1) {
					js.put("code", code);
					return js;																//使用过，返回s
				}
				Integer day = Integer.parseInt(coupon.get("day").toString());					//优惠券有效天数		
				Date create_time = (Date) coupon.get("create_time");							//优惠券获得时间
				Double con_integral = Double.valueOf(coupon.get("integral").toString());		//优惠券积分	
				Date cur_time = sdf.parse(sdf.format(new Date()));								//当前时间
				Calendar cal = Calendar.getInstance();
				cal.setTime(create_time);														//设置起时间
				cal.add(Calendar.DATE, day);													//增加天数,创建时间加有效天数
				if(cal.getTime().after(cur_time)) {
					if(user_integral + con_integral <= com_integral) {											//用上优惠券还不够
						js.put("code", code);
						return js;
					}else {
						orderMapper.insertOrder(order);									//插入订单
						Double curr_integral = (user_integral *100 - com_integral *100 + con_integral *100) / 100;//减去用户积分
						userMapper.updateIntegralById(user_id, curr_integral);				//设置新积分
						commodityMapper.subStore(com_id); 									//商品存储量减少		
						couponMapper.updateCouponStatus(uc_id);									//改变优惠券字段
						code = 0;					
						js.put("code", code);
						js.put("data", curr_integral);						
					}	
				}else {
					js.put("code", code);					//过期返回
					return js;	
				}
			}else {
				//不使用优惠券
				if(user_integral < com_integral) {
					js.put("code", code);
					return js;
				}else {				
						orderMapper.insertOrder(order);									//插入订单
						Double curr_integral = (user_integral *100 - com_integral *100) / 100;//减去用户积分
						userMapper.updateIntegralById(user_id, curr_integral);	//设置新积分
						commodityMapper.subStore(com_id); 									//商品存储量减少				
						code = 0;					
						js.put("code", code);
						js.put("data", curr_integral);
				}						
			}		
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}

		return js;

	}

	
	/**
	 * 连表查询所有订单和具体商品信息
	 */
	@Override
	public List<Order> getAllorder(){
		return orderMapper.getAllorder();
	}
	
	/**
	 * left join连表查询所有订单和具体商品信息
	 */
	@Override
	public JSONObject getAllorders(String name,Integer currentPage,Integer pageSize){
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
			
			
			int cur = (currentPage - 1) * pageSize;
			List<Object> orders = orderMapper.getAllorders(name,cur,pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", orders);
			
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			//System.out.println("没有缓存！！！！！！");
			/*client.hset(CONTENT_KEY, redis_id+"", js.toString());			*/	
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}

		return js;
	}
	
	/**
	 * 获取订单数
	 */
	@Override
	public JSONObject getTotalorder(String name) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer total =  orderMapper.getTotalorder(name);		//获取订单数量
			code = 0;
			js.put("code", code);
			js.put("data", total);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
		}
		return js;

	}
	
	/**
	 * 删除订单
	 */
	@Override
	public JSONObject deleteOrders(Integer[] order_id) {
		int code = 1;
		JSONObject js = new JSONObject();			
			try {
				orderMapper.deleteOrders(order_id);
				code = 0;
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code", code);
				return js;
			}

			js.put("code", code);
		return js;
	}
	
	/**
	 * 获取用户订单
	 */
	@Override
	public JSONObject getUserOrder(Integer user_id,Integer currentPage,Integer pageSize){
		Integer code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer cur = (currentPage - 1) * pageSize;
			List<Object> object = orderMapper.getUserOrder(user_id, cur, pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", object);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	
	/**
	 * 删除用户订单，修改destory字段
	 */
	@Override
	public JSONObject deleteUserOrder(Map<String,Integer> map) {
		Integer code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer order_id = map.get("id");
			orderMapper.deleteUserOrder(order_id);
			code = 0;
			js.put("code", code);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	
	/**
	 * 获取用户详细订单
	 */
	@Override
	public JSONObject getOrderdetail(Integer order_id) {
		Integer code = 1;
		JSONObject js = new JSONObject();
		try {
			Object order = orderMapper.getOrderdetail(order_id);
			code = 0;
			js.put("code", code);
			js.put("data", order);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}

}