package api.app.service;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.Advertisement;

/**
 * 广告表业务接口
 * @author sky
 *
 */
public interface AdvertisementService {
	/**
	 * 添加广告
	 */
	public JSONObject  getAllAdvertisement();
	/**
	 * 根据条件列出所有广告 
	 */
	public JSONObject  getAdvertisement(Integer currentPage,Integer pageSize,Integer status , String create_time);
	/**
	 * 添加广告
	 */
	public void insertAdvertisement(Advertisement advertisement);
	/**
	 * 更新广告
	 */
	public JSONObject updateAdvertisement(Map<String, Object> map);
	/**
	 * 删除广告
	 */
	public void deleteAdvertisement(int ad_id);
	/**
	 * 批量删除广告
	 */
	public JSONObject deleteAllAdvertisement(int[] ad_id);
	
	/**
	 * 统计数量
	 */

	public JSONObject totalAdvertisement(Integer status,String create_time);
	
}
