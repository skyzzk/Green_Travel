package api.app.service;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.Commodity;
/**
 * s商品表业务接口
 * @author sky
 *
 */
public interface CommodityService {
	/**
	 * 根据商品id查询商品
	 */
	public JSONObject getCommodityByid(Integer com_id);
	
	
	/**
	 * 获取所有商品
	 */
	public JSONObject getAllCommodity();
	
	/**
	 * 保存商品
	 */
	public JSONObject insertCommodity( Map<String, Object> map);
	
	/**
	 * 删除商品
	 */
	public void deleteCommodity(int com_id);
	
	/**
	 * 批量删除商品
	 */
	public JSONObject deleteAllCommodity(Integer[] com_id);
	
	/**
	 * 更新商品
	 */
	public JSONObject updateCommodity(Map<String, Object> map);
	
	/**
	 * 模糊查询
	 */
	public JSONObject likeCommodity(String com_name,Integer currentPage,Integer pageSize);
	
	/**
	 * 统计数量
	 */
	public JSONObject totalCommodity(String com_name);
	
	/**
	 * 根据id查商品积分
	 */
	public Double getIntegralById(int com_id);
	
	/**
	 * 兑换后减去存储量
	 */
	public void subStore(int com_id);
	
	/**
	 * 根据商品id查询商品存储量
	 */
	public int getstrorcByid(int com_id);
	
}
