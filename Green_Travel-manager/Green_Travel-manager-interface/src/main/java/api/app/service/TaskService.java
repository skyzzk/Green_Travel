package api.app.service;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 * 任务表业务接口
 * @author sky
 *
 */
public interface TaskService {
	
	
	/**
	 * 插入与任务相关的优惠券ID
	 */
	public JSONObject insertTaskCoupon(JSONObject object);
	
	/**
	 * 查询任务数量
	 */
	public JSONObject totalTask(Integer type);
	
	/**
	 * 根据类型查询任务
	 */
	public JSONObject  listTaskBytype(Integer type,Integer currentPage,Integer pageSize);
		
	/**
	 * 根据任务id查询优惠券
	 */
	public JSONObject getCouponByid(Integer task_id);
	
	/**
	 * 删除任务
	 */
	public JSONObject deleteTask(Integer []task_id);
	
	/**
	 * 修改任务
	 */
	public JSONObject updateTask(Map<String, Object> map);
	

	
	/**
	 * 前台查询任务
	 */
	public JSONObject  listTask(Integer currentPage,Integer pageSize);
	
	/**
	 * 根据任务id查任务
	 * 查询与任务相关的优惠券详细信息
	 */
	public JSONObject getTaskCouponByid(Integer task_id);

	/**
	 * 检查任务完成
	 */
	public JSONObject checkTask(Map<String,Object> map);

	
}
