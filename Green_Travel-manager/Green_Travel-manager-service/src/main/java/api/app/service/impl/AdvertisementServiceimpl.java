package api.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.AdvertisementMapper;
import api.app.service.AdvertisementService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import api.app.pojo.Advertisement;

/**
 * 广告表业务接口实现类
 * @author sky
 *
 */

@Service
public class AdvertisementServiceimpl implements AdvertisementService {

	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	/**
	 * 列出所有广告
	 */
	@Override
	public JSONObject getAllAdvertisement() {
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
			
			List<Advertisement> advertisement= advertisementMapper.getAllAdvertisement();
			code = 0;
			js.put("code", code);
			js.put("data", advertisement);
			
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
	 * 根据条件列出所有广告 
	 */
	@Override
	public JSONObject  getAdvertisement(Integer currentPage,Integer pageSize,Integer status , String create_time){
		int code = 1;
		JSONObject js = new JSONObject();	
		JSONObject time =  (JSONObject) JSONObject.parse(create_time);
		String min = (String) time.get("min");
		String max = (String) time.get("max");
		int cur = (currentPage - 1) * pageSize;

			try {
				/*			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
				String redis_id = "test_id";
				String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
				//如果存在，说明有缓存
				if(StringUtils.isNotBlank(jsonstr)){
					return JSONObject.parseObject(jsonstr);
					}*/				
				
				List<Advertisement> advertisement = advertisementMapper.getAdvertisement(min,max,status,cur,pageSize);
				code = 0;
				js.put("code", code);
				js.put("data", advertisement);
				
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
	 * 添加广告
	 */
	@Override
	public void insertAdvertisement(Advertisement advertisement) {
		advertisementMapper.insertAdvertisement(advertisement);
	}
	
	/**
	 * 更新广告
	 */
	@Override
	public JSONObject updateAdvertisement(Map<String, Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();	
		
			//接收收据
			Integer ad_id = (int) map.get("id");
			String ad_path = (String) map.get("path");
			int ad_status = (int) map.get("status");
			String ad_link = (String) map.get("outside_link");			
			try {
			//保存数据
				Advertisement advertisement = new Advertisement();
				advertisement.setAd_id(ad_id);
				advertisement.setAd_path(ad_path);
				advertisement.setAd_status(ad_status);
				advertisement.setAd_link(ad_link);
				advertisementMapper.updateAdvertisement(advertisement);			
				code = 0;			
				
				//删除缓冲，发送信息
				jmsTemplate.send(topicDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(ad_id.toString());
						return textMessage;
					}
				});

				
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code", code);
				return js;
			}	     
		js.put("code", code);
        return js;
		
	}
	
	/**
	 * 删除广告
	 */
	@Override
	public void deleteAdvertisement(int ad_id) {
		advertisementMapper.deleteAdvertisement(ad_id);
	}
	
	/**
	 * 批量删除广告
	 */
	@Override
	public JSONObject  deleteAllAdvertisement(int[] ad_id) {
		int code = 1;
		JSONObject js = new JSONObject();	
			try {
				advertisementMapper.deleteAllAdvertisement(ad_id);
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
	 * 统计数量
	 */
	@Override
	public JSONObject totalAdvertisement(Integer status,String create_time) {
		int code = 1;
		JSONObject js = new JSONObject();	
		JSONObject time =  (JSONObject) JSONObject.parse(create_time);
		String min = (String) time.get("min");
		String max = (String) time.get("max");	
		try {
				Integer count = advertisementMapper.totalAdvertisement(min,max,status);			
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
	
}
