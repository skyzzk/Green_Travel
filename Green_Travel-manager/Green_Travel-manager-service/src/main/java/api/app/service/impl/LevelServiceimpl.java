package api.app.service.impl;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.LevelMapper;
import api.app.service.LevelService;
/**
 * 广告表业务接口实现类
 * @author sky
 *
 */
@Service
public class LevelServiceimpl implements LevelService {

	@Autowired
	private LevelMapper levelMapper;
	
	@Override
	public Map<String,Object> getLevel(int user_id) {
		return levelMapper.getLevel(user_id);
	}

	/**
	 * 查询各个等级人数
	 */
	@Override
	public JSONObject getAllLevel(){
		int code = 1;
		JSONObject Json = new JSONObject();
		try {
			List<Object> level = levelMapper.getAllLevel();		//获取各个等级人数和等级
			int Total = 0,i = 0,len = 15;											//总人数
			double []data = new double[len];
			for(Object obj : level ){
				JSONObject json = (JSONObject) JSONObject.toJSON(obj);
				Long total = (Long) json.get("total");				//获取人数
				//int lv = (int) json.get("lv");						
				data[i++] = total.doubleValue();							//获取对应等级人数
				Total = Total + total.intValue();					//long转int ，人数相加
			}
			for(int j = 0 ;j < i ;j++) {
				data[j] = new BigDecimal(( data[j] / Total ) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		//求得比例并保留2位小数点
			}
			for(int j = i ;j < len; j++) {
				data[j] = 0.0;										//补充等级人数比例为0的
			} 
			code = 0;
			Json.put("code", code);
			Json.put("data" ,data);		
		}catch(Exception e) {
			Json.put("code", code);
			e.printStackTrace();
		}
		return Json;
	}
	
	/**
	 * 插入新的数据
	 */
	@Override
	public void insertLevel(int user_id) {
		levelMapper.insertLevel(user_id);
	}
	
	/**
	 * 增加经验值
	 */
	@Override
	public void addGrowth(Integer user_id,Integer growth) {
		levelMapper.addGrowth(user_id, growth);
	}
	
	/**
	 * 获取经验值
	 */
	public Map<String,Object> getGrowth(Integer user_id) {
		return levelMapper.getGrowth(user_id);
	}
	
	/**
	 * 升级
	 */
	public void levelUpgrade(Integer growth,Integer user_id,Integer lv) {
		levelMapper.levelUpgrade(growth, user_id, lv);
	}
}
