package api.app.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.DonateMapper;
import api.app.mapper.UserMapper;
import api.app.pojo.Donate;
import api.app.redis.JedisClient;
import api.app.service.DonateService;
import api.app.utils.RandomID;
/**
 * 捐赠表业务层接口实现类
 * @author sky
 *
 */
@Service
public class DonateServiceimpl implements DonateService {
	
	@Autowired
	private DonateMapper donateMapper;
	@Autowired
	private UserMapper userMapper;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	@Autowired
	private JedisClient client;
	
	/**
	 * 用户捐赠
	 */
	
	@Override
	public JSONObject updateUserdonate(Map<String,Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
		
		Integer pw_id = (Integer) map.get("id");
		Integer user_id = (Integer) map.get("user_id");
		Double integral = Double.valueOf(map.get("integral").toString());
		
		try {
			//查询积分是否足够
			Double user_integral = userMapper.getIntegralById(user_id);
			if(user_integral >= integral) {
				//足够则减少用户的碳积分
				donateMapper.subDonate(integral, user_id);
				//查询是否已经捐赠过该公益活动
				Integer flag = donateMapper.everDonate(pw_id, user_id);
				//保存数据
				Donate donate = new Donate();
				donate.setIntegral(integral);
				donate.setPw_id(pw_id);
				donate.setUser_id(user_id);
				if(flag == 0) {
					//不存在，插入				
					RandomID randomID = new RandomID();
					Integer id = randomID.randomID();
					donate.setId(id);
					donateMapper.insertDonate(donate);
				}else {
					//存在，修改
					donateMapper.updateDonate(donate);
				}
				Double curr_integral = userMapper.getIntegralById(user_id);
				code = 0;
				js.put("code", code);
				js.put("data", curr_integral);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}	
		return js;
	}
	
	/**
	 * 查询是否已经捐赠过该公益活动
	 */
	@Override
	public int everDonate(Integer pw_id,Integer user_id) {
		return donateMapper.everDonate(pw_id, user_id);
	}
	
	/**
	 * 减少用户积分
	 */
	@Override
	public void subDonate(Double integral,Integer user_id) {
		donateMapper.subDonate(integral, user_id);
	}
	
	/**
	 * 插入捐赠记录
	 */
	@Override
	public void insertDonate(Donate donate) {
		donateMapper.insertDonate(donate);
	}
	
	/**
	 * 修改捐赠记录
	 */
	@Override
	public void updateDonate(Donate donate) {
		donateMapper.updateDonate(donate);
	}
	
	/**
	 * 查询捐赠信息
	 */
	public JSONObject getDonate(Integer pw_id,Integer currentPage,Integer pageSize){
		int code = 1;
		JSONObject js = new JSONObject();
			try {
							//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
				String redis_id = pw_id.toString();
				String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
				//如果存在，说明有缓存
				if(StringUtils.isNotBlank(jsonstr)){
					return JSONObject.parseObject(jsonstr);
					}
				
				
				Integer cur = (currentPage - 1) * pageSize;
				List<Object> list = donateMapper.getDonate(pw_id, cur, pageSize);
				code = 0;
				js.put("code", code);
				js.put("data", list);
				
				//将数据写入到redis数据库中   			
				// 注入jedisclient 
				// 调用方法写入redis   key - value
				//System.out.println("没有缓存！！！！！！");
				client.hset(CONTENT_KEY, redis_id+"", js.toString());
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code", code);
				return js;
			}
		return js;
	}


}
