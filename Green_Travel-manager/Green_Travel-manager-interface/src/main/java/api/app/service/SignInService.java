package api.app.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.SignIn;

/**
 * 签到表业务接口
 * @author sky
 *
 */
public interface SignInService {
	/**
	 * 插入用户签到
	 */
	public JSONObject insertSignIn(Integer user_id);
	
	/**
	 * 查询当月是否存在该用户签到
	 */
	public int isExist(int user_id,int month,int year);
	
	/**
	 * 更新年月
	 */
	public void updateSignIn(SignIn signin);
	
	/**
	 * 获取id和签到的天
	 */
	public Map<String,Object> getDays(int user_id,int month,int year);
	
	/**
	 * 获取签到的天
	 */
	public String getday(int id,int month,int year);
	
	/**
	 * 签到
	 */
	public JSONObject insertsignin(Map<String,Object> map);
}
