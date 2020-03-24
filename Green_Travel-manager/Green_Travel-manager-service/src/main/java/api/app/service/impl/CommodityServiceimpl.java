package api.app.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.*;

import api.app.mapper.CommodityMapper;
import api.app.pojo.Commodity;
import api.app.redis.JedisClient;
import api.app.service.CommodityService;
import api.app.utils.RandomID;


/**
 * s商品表业务接口实现类
 * @author sky
 *
 */


@Service
public class CommodityServiceimpl implements CommodityService {
	
	@Autowired
	private CommodityMapper commodityMapper;
	
	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	/**
	 * 根据订单id查询商品
	 */
	@Override
	public JSONObject getCommodityByid(Integer com_id) {
		int code = 1;
		JSONObject js = new JSONObject();
			try {
				//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
				String redis_id = com_id.toString();
				String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
				//如果存在，说明有缓存
				if(StringUtils.isNotBlank(jsonstr)){
					return JSONObject.parseObject(jsonstr);
				}	
				Commodity commodity = commodityMapper.getCommodityByid(com_id);	
				code = 0;
				js.put("code", code);
				js.put("data", commodity);				
				//将数据写入到redis数据库中   			
				// 注入jedisclient 
				// 调用方法写入redis   key - value
				client.hset(CONTENT_KEY, redis_id+"", js.toString());					
			}catch(Exception e){
				e.printStackTrace();
				js.put("code", code);
				return js;
			}
		return js;

	}

	
	/**
	 * 获取所有商品
	 */
	@Override
	public JSONObject getAllCommodity() {	
		
		JSONObject js = new JSONObject();
		int code = 1;	
		try {			
/*			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = "test_id";
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
				}*/						
			List<Commodity> commodity = commodityMapper.getAllCommodity();
			code = 0;
			js.put("code", code);
			js.put("data", commodity);					
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			//System.out.println("没有缓存！！！！！！");
			/*client.hset(CONTENT_KEY, redis_id+"", js.toString());			*/			
		}catch(Exception e){
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	
	
	/**
	 * 保存商品
	 */
	@Override
	public JSONObject insertCommodity(Map<String, Object> map) {
		JSONObject js = new JSONObject();
		int code = 1;
			//接收收据
			RandomID Id = new RandomID();
			String com_name = (String) map.get("name");
			String com_intro = (String) map.get("description");
			double com_value = Double.valueOf(map.get("integral").toString());
			String com_image = (String) map.get("path");
			int com_stock = (int) map.get("stock");
			int st = (int) map.get("status");

			try {
			//保存数据				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(sdf.format(new Date()));
				Integer id = Id.randomID();
		        Commodity commodity = new Commodity();
		        commodity.setCom_id(id);
		        commodity.setCom_image(com_image);
		        commodity.setCom_intro(com_intro);
		        commodity.setCom_name(com_name);
		        commodity.setCom_value(com_value);
		        commodity.setCreate_time(date);
		        commodity.setCom_stock(com_stock);
		        commodity.setStatus(st);
		        commodityMapper.insertCommodity(commodity);				
				code = 0;			
				js.put("code", code);
				
				//删除缓冲，发送信息
				jmsTemplate.send(topicDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(id.toString());
						return textMessage;
					}
				});
				
				
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code", code);
				return js;
			}		             
        return js;
		
	}
	
	/**
	 * 删除商品
	 */
	@Override
	public void deleteCommodity(int com_id) {
		commodityMapper.deleteCommodity(com_id);
	}
	
	/**
	 * 批量删除商品
	 */
	@Override
	public JSONObject deleteAllCommodity(Integer[] com_id) {
		int code = 1;
		JSONObject js = new JSONObject();
			try {
				commodityMapper.deleteAllCommodity(com_id);
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
	 * 更新商品
	 */
	@Override
	public JSONObject updateCommodity(Map<String, Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
			//接收收据
			String com_name = (String) map.get("name");
			String com_intro = (String) map.get("description");
			double com_value = Double.valueOf(map.get("integral").toString());
			Integer com_id = (Integer) map.get("id");
			String com_image = (String) map.get("path");
			int com_stock = (int) map.get("stock");
			int st = (int) map.get("status");
			
			try {
				//保存数据
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(sdf.format(new Date()));
		        Commodity commodity = new Commodity();    
		        commodity.setCom_id(com_id);
		        commodity.setCom_image(com_image);
		        commodity.setCom_intro(com_intro);
		        commodity.setCom_name(com_name);
		        commodity.setCom_value(com_value);
		        commodity.setCreate_time(date);
		        commodity.setCom_stock(com_stock);
		        commodity.setStatus(st);
		        commodityMapper.updateCommodity(commodity);					
				code = 0;
				js.put("code", code);
				
				//删除缓冲，发送信息
				jmsTemplate.send(topicDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(com_id.toString());
						return textMessage;
					}
				});
				
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code", code);
				return js;
			}	 
		
	    return js;
	}
	
	
	/**
	 * 模糊查询
	 */
	@Override
	public JSONObject likeCommodity(String com_name ,Integer currentPage,Integer pageSize){
		int code = 1;
		JSONObject js = new JSONObject();
		Integer cur = (currentPage - 1) * pageSize;
		try {
			/*//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = "test";
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}*/
			
			List<Commodity> commodity = commodityMapper.likeCommodity(com_name,cur,pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", commodity);
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			//System.out.println("没有缓存！！！！！！");
			/*client.hset(CONTENT_KEY, redis_id+"", js.toString());*/
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js; 
		}		
		//加缓存	
		return js;
	}

	
	/**
	 * 统计数量
	 */
	@Override
	public JSONObject totalCommodity(String com_name) {
		int code = 1,count;
		JSONObject js = new JSONObject();
		try {
			count = commodityMapper.totalCommodity(com_name);
			code = 0;
			js.put("code", code);
			js.put("data", count);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}	 			
		return js;
	}
	
	/**
	 * 根据id查商品积分
	 */
	@Override
	public Double getIntegralById(int com_id) {
		return commodityMapper.getIntegralById(com_id);
	}
	
	/**
	 * 兑换后减去存储量
	 */
	@Override
	public void subStore(int com_id) {
		commodityMapper.subStore(com_id);
	}
	
	/**
	 * 根据商品id查询商品存储量
	 */
	@Override
	public int getstrorcByid(int com_id) {
		return commodityMapper.getstrorcByid(com_id);
	}
}
