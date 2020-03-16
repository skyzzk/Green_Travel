package api.app.service;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;



/**
 * 等级表业务接口
 * @author sky
 *
 */
public interface LevelService {

	/**
	 * 根据id查用户等级经验
	 */
	public Map<String,Object> getLevel(int user_id);
	
	/**
	 * 查询各个等级人数
	 */
	public JSONObject getAllLevel();
	
	/**
	 * 插入新的数据
	 */
	public void insertLevel(int user_id);
	
	/**
	 * 增加经验值
	 */
	public void addGrowth(Integer user_id,Integer growth);
	
	/**
	 * 获取经验值
	 */
	public Map<String,Object> getGrowth(Integer user_id);
	
	/**
	 * 升级
	 */
	public void levelUpgrade(Integer growth,Integer user_id,Integer lv);
}
