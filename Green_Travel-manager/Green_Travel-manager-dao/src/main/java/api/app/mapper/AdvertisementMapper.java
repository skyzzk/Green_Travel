package api.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import api.app.pojo.Advertisement;

/**
 * 广告表持久化接口
 * @author sky
 *
 */
public interface AdvertisementMapper {
	/**
	 * 列出所有广告 
	 */
	public List<Advertisement>  getAllAdvertisement();
	/**
	 * 根据条件列出所有广告 
	 */
	public List<Advertisement>  getAdvertisement(@Param("min_time")String min_time,@Param("max_time")String max_time,
			@Param("ad_status")int status,@Param("current")int current,@Param("pageSize")int pageSize);
	
	/**
	 * 添加广告
	 */
	public void insertAdvertisement(Advertisement advertisement);
	/**
	 * 更新广告
	 */
	public void updateAdvertisement(Advertisement advertisement);
	
	/**
	 * 删除广告
	 */
	public void deleteAdvertisement(int ad_id);
	/**
	 * 批量删除广告
	 */
	public void deleteAllAdvertisement(int []ad_id);
	
	
	/**
	 * 统计数量
	 */

	public int totalAdvertisement(@Param("min")String  min,@Param("max")String max,@Param("ad_status")int ad_status);

}
