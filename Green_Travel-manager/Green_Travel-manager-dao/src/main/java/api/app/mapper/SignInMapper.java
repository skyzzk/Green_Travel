package api.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import api.app.pojo.SignIn;

/**
 * 签到表持久化接口
 * @author sky
 *
 */

public interface SignInMapper {
	/**
	 * 插入用户签到
	 */
	public void insertSignIn(SignIn signin);
	
	/**
	 * 更新年月
	 */
	public void updateSignIn(SignIn signin);
	
	/**
	 * 查询当月是否存在该用户签到
	 */
	public int isExist(@Param("user_id")int user_id,@Param("month")int month,@Param("year")int year);
	
	/**
	 * 获取id和签到的天
	 */
	public Map<String,Object> getDays(@Param("user_id")int user_id,@Param("month")int month,@Param("year")int year);
	
	/**
	 * 获取签到的天
	 */
	public String getday(@Param("id")int id,@Param("month")int month,@Param("year")int year);
	
	/**
	 * 签到
	 */
	public void updateDay(@Param("day")String day,@Param("id")int id);
}
