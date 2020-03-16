package api.app.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 等级表持久化接口
 * @author sky
 *
 */
public interface LevelMapper {

	/**
	 * 根据id查用户等级经验
	 */
	public Map<String,Object> getLevel(int user_id);
	
	/**
	 * 查询各个等级人数
	 */
	public List<Object> getAllLevel();
	
	/**
	 * 插入新的数据
	 */
	public void insertLevel(Integer user_id);
	
	/**
	 * 增加经验值
	 */
	public void addGrowth(@Param("user_id")Integer user_id,@Param("growth")Integer growth);
	
	/**
	 * 获取经验值
	 */
	public Map<String,Object> getGrowth(Integer user_id);
	
	/**
	 * 升级
	 */
	public void levelUpgrade(@Param("growth")Integer growth,@Param("user_id")Integer user_id,@Param("lv")Integer lv);
	
}
