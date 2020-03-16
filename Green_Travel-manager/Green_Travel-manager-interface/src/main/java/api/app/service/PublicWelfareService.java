package api.app.service;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 * 公益表持业务层接口
 * @author sky
 *
 */
public interface PublicWelfareService {
	/**
	 * 查询用户公益
	 */
	public JSONObject getAllPublicWelfare(String name,Integer currentPage,Integer pageSize);
	
	/**
	 * 统计数量
	 */
	public JSONObject totalPublicWelfare(String name);
	
	/**
	 * 删除
	 */
	public JSONObject deletePublicWelfare(Integer []ids);
	
	/**
	 * 修改状态
	 */
	public JSONObject updateStatus(Map<String, Object> map);
	
	/**
	 * 保存公益申请
	 */
	public JSONObject insertPublicWelfare(Map<String, Object> map);
	
	
	/**
	 * 查询发布的公益
	 */
	public JSONObject	getPublicWelfare(Integer currentPage,Integer pageSize);
	
	/**
	 * 根据id查询发布的公益
	 */
	public JSONObject getPublicWelfareByid(Integer pw_id);
	
}
