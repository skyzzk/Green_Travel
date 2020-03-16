package api.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import api.app.pojo.Task;

/**
 * 任务表持久化接口
 * @author sky
 *
 */
public interface TaskMapper {

	/**
	 * 添加任务
	 */
	public void insertTask(Task task);
	
	/**
	 * 插入与任务相关的优惠券ID
	 */
	public void insertTaskCoupon(@Param("task_id")Integer task_id,@Param("co_id")Integer co_id);
	
	/**
	 * 查询任务数量
	 */
	public Integer totalTask(@Param("type")Integer type);
	
	/**
	 * 根据类型查询任务
	 */
	public List<Object>  listTaskBytype(@Param("type")Integer type,@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据任务id查询优惠券
	 */
	public List<Object> getCouponByid(Integer task_id);
	
	/**
	 * 删除任务
	 */
	public void deleteTask(Integer []task_id);
	
	/**
	 * 修改任务
	 */
	public void updateTask(Task task);
	
	/**
	 * 修改与任务相关的优惠券ID
	 */
	public void deleteTaskCoupon(Integer task_id);
	
	/**
	 * 前台查询任务
	 */
	public List<Object>  listTask(@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据任务id查任务
	 */
	public Map<String,Object> getTaskCouponByid(Integer task_id);
	
	/**
	 * 查询与任务相关的优惠券详细信息
	 */
	public List<Object> getAllCouponByid(Integer task_id);
	
	/**
	 * 获取任务相关要求值
	 */
	public Map<String,Object> getTaskAim(@Param("user_id")Integer user_id,@Param("type")Integer type);
	
	/**
	 * 更新用户任务数据
	 */
	public void updateUserTask(@Param("user_id")Integer user_id,@Param("task_id")Integer task_id);
	
	
	
}
