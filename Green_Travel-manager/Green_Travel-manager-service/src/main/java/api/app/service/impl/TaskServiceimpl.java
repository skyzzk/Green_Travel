package api.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.TaskMapper;
import api.app.pojo.Task;
import api.app.redis.JedisClient;
import api.app.service.TaskService;
import api.app.utils.HttpUtil;
import api.app.utils.RandomID;

/**
 * 任务表业务接口实现类
 * @author sky
 *
 */
@Service
public class TaskServiceimpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	

	
	/**
	 * 插入与任务相关的优惠券ID
	 */
	@Override
	public JSONObject insertTaskCoupon(JSONObject object) {
		int code = 1;
		JSONObject js = new JSONObject();
		
		//获取对应数据
		String title = (String) object.get("title");
		Integer aim = (Integer) object.get("aim");
		Integer type = (Integer) object.get("type");
		String description = (String) object.get("description");
		Double integral = Double.valueOf(object.get("integral").toString()) ;
		Integer growth = (Integer) object.get("growth");		
		List<Integer> coupon =  (ArrayList<Integer>) object.get("coupons");
		Integer len = coupon.size();
	
		//生成任务ID
		RandomID randomId = new RandomID();
		Integer task_id = randomId.randomID();
		
		try {
			//生成时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
			Date create_time = sdf.parse(sdf.format(new Date()));
			//保存数据
			Task task = new Task();
			task.setTask_id(task_id);
			task.setAim(aim);
			task.setCreate_time(create_time);
			task.setDescription(description);
			task.setGrowth(growth);
			task.setTitle(title);
			task.setType(type);
			task.setIntegral(integral);
			//插入任务
			taskMapper.insertTask(task);
			if(len>0) {
				//插入与任务相关的优惠券
				for (int i = 0; i < len ;i++) {
					taskMapper.insertTaskCoupon(task_id,coupon.get(i));
		        }								
			}
			code = 0;
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}
		js.put("code", code);
		return js;	
	}
	
	
	/**
	 * 查询任务数量
	 */
	@Override
	public JSONObject totalTask(Integer type) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer count = taskMapper.totalTask(type);
			code = 0;
			js.put("code", code);
			js.put("data", count);
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}		
		return js;

	}

	/**
	 *根据类型查询任务
	 * @return 
	 */
	@Override
	public JSONObject listTaskBytype(Integer type,Integer currentPage,Integer pageSize){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer cur = (currentPage - 1) * pageSize;
			List<Object>  task = taskMapper.listTaskBytype(type, cur, pageSize);
			Integer []coupon = new Integer[0];
			//遍历添加字段
			for(int i = 0;i < task.size();i++) {
				Map<String,Object> obj = (Map<String, Object>) task.get(i);
				obj.put("coupon",coupon);
			}			
			code = 0;
			js.put("code", code);
			js.put("data", task);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}		
		return js;

	}
	
	
	/**
	 * 根据任务id查询优惠券
	 */
	@Override
	public JSONObject getCouponByid(Integer task_id) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = task_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}	
			
			List<Object> list = taskMapper.getCouponByid(task_id);
			code = 0;
			js.put("code", code);
			js.put("data", list);
			
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			client.hset(CONTENT_KEY, redis_id+"", js.toString());			
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	
	
	/**
	 * 删除任务
	 */
	@Override
	public JSONObject deleteTask(Integer []task_id) {
		int code = 1;
		JSONObject js = new JSONObject();
			try {
				taskMapper.deleteTask(task_id);
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
	 * 修改任务
	 */
	@Override
	public JSONObject updateTask(Map<String, Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
		//获取对应数据
		String title = (String) map.get("title");
		Integer aim = (Integer) map.get("aim");
		Integer type = (Integer) map.get("type");
		String description = (String) map.get("description");
		Double integral = Double.valueOf(map.get("integral").toString()) ;
		Integer growth = (Integer) map.get("growth");
		Integer task_id = (Integer) map.get("id");
		List<Integer> coupon =  (ArrayList<Integer>) map.get("coupons");
		Integer len = coupon.size();
		
		try {
			//生成时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
			Date create_time = sdf.parse(sdf.format(new Date()));
			//保存数据
			Task task = new Task();
			task.setTask_id(task_id);
			task.setAim(aim);
			task.setCreate_time(create_time);
			task.setDescription(description);
			task.setGrowth(growth);
			task.setTitle(title);
			task.setType(type);
			task.setIntegral(integral);
			//修改任务
			taskMapper.updateTask(task);
			if(len>0) {		
				//删除与任务相关的优惠券
				taskMapper.deleteTaskCoupon(task_id);;					//删除
				//重新插入与任务相关的优惠券
				for (int i = 0; i < len ;i++) {
					taskMapper.insertTaskCoupon(task_id, coupon.get(i));
		        }	
			}
			code = 0;
			
			//删除缓冲，发送信息
			jmsTemplate.send(topicDestination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(task_id.toString());
					return textMessage;
				}
			});
			
			
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}
		js.put("code", code);
		return js;
	}
	

	
	/**
	 * 前台查询任务
	 */
	@Override
	public JSONObject listTask(Integer currentPage,Integer pageSize){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer cur = (currentPage - 1) * pageSize;
			List<Object>  task = taskMapper.listTask(cur, pageSize);
			Integer []coupon = new Integer[0];
			//遍历添加字段
			for(int i = 0;i < task.size();i++) {
				Map<String,Object> obj = (Map<String, Object>) task.get(i);
				obj.put("coupon",coupon);
			}						
			code = 0;
			js.put("code", code);
			js.put("data", task);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}		
		return js;
	}
	
	/**
	 * 根据任务id查任务和优惠券
	 */
	@Override
	public JSONObject  getTaskCouponByid(Integer task_id) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = task_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}	
					
			Map<String,Object> obj = taskMapper.getTaskCouponByid(task_id);						//获取到任务			
			List<Object> coupons = taskMapper.getAllCouponByid(task_id);				//获取优惠卷
			obj.put("coupons", coupons);
			code = 0;
			js.put("code", code);
			js.put("data", obj);
			
			//将数据写入到redis数据库中   			
			// 注入jedisclient 
			// 调用方法写入redis   key - value
			client.hset(CONTENT_KEY, redis_id+"", js.toString());			
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
	
		return js;
	}

	/**
	 * 检查任务完成
	 */
	public JSONObject checkTask(Map<String,Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
		
		Integer user_id = (Integer) map.get("user_id");
		Integer type = (Integer) map.get("type");
		if(type == 6) {
			type = 4;
		}
		String url = "http://59.110.174.204:7280/v1.0/api/app/trip/baseTriplist";
		JSONObject JsonObject = new JSONObject();
		JsonObject.put("service_id", type);			
		JsonObject.put("trip_flag", 02);
		JsonObject.put("page_no", 1);
		JsonObject.put("page_size", 1000);
		
		try {
			JSONObject object = HttpUtil.sendPost(url, JsonObject, "1");		//调用八维通接口
			JSONObject result = (JSONObject) object.get("result");
			JSONObject page = (JSONObject) result.get("page");
			List<JSONObject> rows =   (List<JSONObject>) page.get("rows");
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date cur_time = sdf.parse(sdf.format(new Date()));
			boolean flag = false;
			Integer sum = 0;							//总公里
			Integer count = 0;							//次数
			for(int i = 0 ; i < rows.size() ;i++) {
				JSONObject obj = rows.get(i);
				Date time = sdf.parse(obj.get("pay_time").toString().replaceAll("/", "-"));
				//比较时间
				if(cur_time.equals(time)) {
					Integer mileage = (Integer) obj.get("mileage");
					if(type == 0) {
						sum = sum + mileage;
					}else {
						count++;
					}
					
					flag = true;
				}
			}			
			if(flag) {
				//获取对应任务要求值
				Map<String,Object> task = taskMapper.getTaskAim(user_id, type);
				Integer aim = (Integer) task.get("aim");
				Integer task_id = Integer.parseInt(task.get("task_id").toString());
				//大于等于达标,更新数据
				if(type !=0) {
					if(sum >= aim) {
						taskMapper.updateUserTask(user_id, task_id);
					}
				}else {
					if(count >= aim) {
						taskMapper.updateUserTask(user_id, task_id);
					}
				}
				
			}
			code = 0;
		} catch (Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		js.put("code", code);
		return js;
	}
	
	
}
