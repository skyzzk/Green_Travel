package api.app.service;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.Donate;

/**
 * 捐赠表业务层接口
 * @author sky
 *
 */

public interface DonateService {
	
	/**
	 * 用户捐赠
	 */
	public JSONObject updateUserdonate(Map<String,Object> map);
	
	/**
	 * 查询是否已经捐赠过该公益活动
	 */
	public int everDonate(Integer pw_id,Integer user_id);
	
	/**
	 * 减少用户积分
	 */
	public void subDonate(Double integral,Integer user_id);
	
	/**
	 * 插入捐赠记录
	 */
	public void insertDonate(Donate donate);
	
	/**
	 * 修改捐赠记录
	 */
	public void updateDonate(Donate donate);
	
	/**
	 * 查询捐赠信息
	 */
	public JSONObject getDonate(Integer pw_id,Integer currentPage,Integer pageSize);
}
