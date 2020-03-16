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

import api.app.mapper.PublicWelfareMapper;
import api.app.pojo.PublicWelfare;
import api.app.redis.JedisClient;
import api.app.service.PublicWelfareService;
import api.app.utils.RandomID;

/**
 * 公益表持业务层实现类
 * @author sky
 *
 */
@Service
public class PublicWelfareServiceimpl implements PublicWelfareService {

	@Autowired
	private PublicWelfareMapper publicWelfareMapper;
	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	/**
	 * 查询用户公益
	 */
	@Override
	public JSONObject getAllPublicWelfare(String name,Integer currentPage,Integer pageSize) {
		int code = 1;
		JSONObject js = new JSONObject();
		Integer cur = (currentPage - 1) * pageSize;	
		try {
			List<Object> list = publicWelfareMapper.getAllPublicWelfare(name,cur,pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", list);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
		}
		return js;
	}
	
	/**
	 * 统计数量
	 */
	@Override
	public JSONObject totalPublicWelfare(String name){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			Integer total = publicWelfareMapper.totalPublicWelfare(name);
			code = 0;
			js.put("code", code);
			js.put("data", total);
		}catch(Exception e) {
			e.printStackTrace();
			js.put("code", code);
			return js;
		}
		return js;
	}

	/**
	 * 删除
	 */
	public JSONObject deletePublicWelfare(Integer []ids) {
		int code = 1;
		JSONObject js = new JSONObject();
			try {
				publicWelfareMapper.deletePublicWelfare(ids);
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
	 * 修改状态
	 */
	public JSONObject updateStatus(Map<String, Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
			Integer id = (Integer) map.get("id");
			String reason = (String) map.get("reason");
			Integer status = (Integer) map.get("status");
			try {
				publicWelfareMapper.updateStatus(id, status, reason);
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
	 * 保存公益申请
	 */
	public JSONObject insertPublicWelfare(Map<String, Object> map) {
		int code = 1;
		JSONObject js = new JSONObject();
			Integer user_id = (Integer) map.get("user_id");
			String title = (String) map.get("title");
			Double integral = Double.valueOf(map.get("integral").toString());
			String description = (String) map.get("description");
			String endtime = (String) map.get("end_time");
			String path = (String) map.get("path");	
			try {
				//创建id
				RandomID Id = new RandomID();
				Integer pw_id = Id.randomID();
				//创建时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Date create_time = sdf.parse(sdf.format(new Date()));
				//截止时间String转为Date
				Date end_time = sdf.parse(endtime);
				//保存数据
				PublicWelfare publicWelfare = new PublicWelfare();
				publicWelfare.setPw_id(pw_id);
				publicWelfare.setUser_id(user_id);
				publicWelfare.setTitle(title);
				publicWelfare.setDescription(description);
				publicWelfare.setIntegral(integral);
				publicWelfare.setCreate_time(create_time);
				publicWelfare.setEnd_time(end_time);
				publicWelfare.setPath(path);
				publicWelfareMapper.insertPublicWelfare(publicWelfare);
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
	 * 查询发布的公益
	 */
	public JSONObject	getPublicWelfare(Integer currentPage,Integer pageSize){
		int code = 1;
		JSONObject js = new JSONObject();
		try {
			//判断 是否redis中有数据  如果有   直接从redis中获取数据 返回		
	/*		String redis_id = pw_id.toString();
			String jsonstr = client.hget(CONTENT_KEY, redis_id+"");//从redis数据库中获取内容分类下的所有的内容。
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)){
				return JSONObject.parseObject(jsonstr);
			}*/
			
			Integer cur = (currentPage - 1) * pageSize;
			List<Object> list = publicWelfareMapper.getPublicWelfare(cur,pageSize);
			code = 0;
			js.put("code", code);
			js.put("data", list);
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
	 * 根据id查询发布的公益
	 */
	public JSONObject getPublicWelfareByid(Integer pw_id) {
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
			Object object = publicWelfareMapper.getPublicWelfareByid(pw_id);
			code = 0;
			js.put("code", code);
			js.put("data", object);
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
