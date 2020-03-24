package api.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.LevelMapper;
import api.app.mapper.TaskMapper;
import api.app.mapper.UserMapper;
import api.app.redis.JedisClient;
import api.app.service.UserService;
import api.app.utils.LoadFile;
import api.app.utils.RandomID;

/**
 * 用户表业务实现类
 * @author sky
 *
 */
@Service
public class UserServiceimpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private LevelMapper levelMapper;
	
	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	

	
	/**
	 * 查询用户信息
	 */
	@Override
	public JSONObject getUser(Integer user_id) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = user_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}	
					
			//查询用户和等级
			Map<String,Object>  obj = userMapper.getUser(user_id);
			//获取与用户相关任务
			List<Object> tasks = userMapper.getUserTask(user_id); 
			if(tasks.size()>0) {
				for(int i = 0;i < tasks.size();i++) {						
					Map<String,Object> task = (Map<String, Object>) tasks.get(i);
					//获取任务id
					Integer task_id = Integer.valueOf(task.get("task_id").toString());
					//获取优惠卷
					Integer []coupon = userMapper.getUserTaskCoupon(task_id);
					//添加优惠卷信息
					task.put("coupons",coupon);
				}				
			}
			obj.put("tasks", tasks);
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
	 * 查询与用户相关的任务
	 */
	@Override
	public JSONObject getUserTask(Integer user_id){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			List<Object> tasks = userMapper.getUserTask(user_id);
			for(int i = 0;i < tasks.size();i++) {						
				Map<String,Object> task = (Map<String, Object>) tasks.get(i);
				Integer []coupon = new Integer[0];
				//添加优惠卷信息
				task.put("coupons",coupon);
			}
			code = 0;
			js.put("code", code);
			js.put("data", tasks);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}
	

	
	/**
	 * 领取任务
	 */
	@Override
	public JSONObject acquireTask(Map<String, Object> map) {
		//获取用户id与任务id
		Integer task_id = (Integer) map.get("id");
		Integer user_id = (Integer) map.get("user_id");
		JSONObject js = new JSONObject();
		int code = 1;
		try {
			//先判断是否存在该任务
			Integer flag1 = userMapper.existTask(task_id);		//0不存在			
			//再是否有正在进行的同类型任务
			Integer flag2 = userMapper.existTasking(task_id);	//1有正在进行的
			if(flag1== 0 || flag2 == 1) {
				js.put("code", code);
			}else {
				//如果当前任务未领取或者已经完成，领取任务
				userMapper.acquireTask(task_id, user_id);
				//查询与任务相关的数据
				Map<String,Object> obj = taskMapper.getTaskCouponByid(task_id);						//获取到任务	信息		
				List<Object> coupons = taskMapper.getAllCouponByid(task_id);								//获取优惠卷信息
				obj.put("coupons", coupons);
				code = 0;
				js.put("code", code);
				js.put("data", obj);
			}		
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}		
		return js;
		
		
	}
	

	
	/**
	 * 用户公益信息
	 */
	@Override
	public JSONObject UserpublicWelfareList(Integer currentPage,Integer pageSize,Integer user_id){
		int code = 1;
		JSONObject js = new JSONObject();
		Integer cur = (currentPage - 1) * pageSize;			//页数
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = user_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}
			
			List<Object> object = userMapper.UserpublicWelfareList(cur, pageSize, user_id);
			code = 0;
			js.put("code", code);
			js.put("data", object);
			
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
	 * 查询详细的公益信息
	 */
	@Override
	public JSONObject publicWelfareDetail(Integer user_id,Integer pw_id) {
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = user_id.toString() + pw_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}
			
			Object object = userMapper.publicWelfareDetail(user_id, pw_id);
			js.put("code", code);
			js.put("data", object);
			
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
	 * 查询我的优惠券
	 */
	@Override
	public JSONObject userCoupon(Integer currentPage,Integer pageSize,Integer user_id){
		int code = 1;
		JSONObject js = new JSONObject();	
		Integer cur = (currentPage - 1) * pageSize;			//页数
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
			String redis_id = user_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}
			
			List<Object> object = userMapper.userCoupon(cur, pageSize, user_id);
			code = 0;
			js.put("code", code);
			js.put("data", object);
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
	 * 任务完成获取对应奖励
	 */
	@Override
	public JSONObject acquireAward(Map<String, Integer> map){
		Integer task_id = map.get("id");
		Integer user_id = map.get("user_id");
		JSONObject js = new JSONObject();
		int code = 1;
		try {
			//完成任务修改字段
			userMapper.finishTask(user_id, task_id);
			//获取对应积分和经验值
			Map<String,Object> award = userMapper.acquireAward(user_id, task_id);
			Integer growth = Integer.parseInt(award.get("growth").toString());	//经验值
			Double integral =  Double.valueOf(award.get("integral").toString());//积分
			//增加积分
			Double cur_integral = userMapper.getIntegralById(user_id);
			Double new_integral = ((cur_integral*100) + (integral*100))/100;
			userMapper.updateIntegralById(user_id, new_integral);		
			//增加经验值
			levelMapper.addGrowth(user_id, growth);
			/**
			 * 是否可以升级
			 */				
			Map<String,Object> level = levelMapper.getGrowth(user_id);//查询当前等级和经验
			Integer cur_growth = Integer.parseInt(level.get("growth").toString());		//当前经验值
			Integer lv = Integer.parseInt(level.get("lv").toString());				//当前等级											//当前等级
			//加载文件得到对应等级经验值界限
			String result = LoadFile.getProperty("attribute.properties", "growths");
			String[] array = result.split(",");					//已逗号切割开成数组			
			Integer []growths = new Integer[array.length];
			for (int i = 0 ; i < array.length ; i++) {
				growths[i] = 	Integer.parseInt(array[i]);		//获取15个等级升级所需经验
		     }
			//如果当前经验大于界限，升级
			if(growths[lv + 1] < cur_growth) {
				if(lv < 15) {
					lv = lv + 1;
				}
				levelMapper.levelUpgrade(growth - growths[lv + 1], user_id, lv);
			}
			
			//添加我的优惠券
			Integer []coupons = userMapper.getUserTaskCoupon(task_id);	
			//生成时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
			Date create_time = sdf.parse(sdf.format(new Date()));
			for(int i = 0;i < coupons.length;i++) {
				//生成ID
				RandomID Id = new RandomID();
				int uc_id = Id.randomID();
				//添加我的优惠券
				userMapper.addUserCoupon(user_id, uc_id, coupons[i], create_time);
			}
			//返回用户最终积分，经验和等级
			Map<String,Object> Result = levelMapper.getGrowth(user_id);//查询当前等级和经验
			Double fina_integral = userMapper.getIntegralById(user_id);
			Result.put("integral", fina_integral);
			code = 0;
			js.put("code", code);
			js.put("data", Result);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}

	/**
	 * 用户认证
	 */
	
	@Override
	public JSONObject Authentication(Map<String,Object> map ) {
		JSONObject js = new JSONObject();
		int code = 1;
		Integer id = (int) map.get("id");
		String name = (String) map.get("name");
		try {
			Integer flag = userMapper.existUser(id, name);		//查询是否存在用户
			if(flag == 0) {
				//不存在则插入用户，插入等级
				userMapper.insertUser(id, name);
				levelMapper.insertLevel(id);
			}
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
	 * 获取所有用户id
	 */
	public List<Integer> getAllUserId(){
		return userMapper.getAllUserId();
	}
	
}
