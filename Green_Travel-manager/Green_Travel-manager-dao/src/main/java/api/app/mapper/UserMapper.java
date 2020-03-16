package api.app.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 用户表持久化接口
 * @author sky
 *
 */
public interface UserMapper {
	/**
	 * 查询所有id
	 */
	public List<Integer> getAllUserId();
	
	/**
	 * 插入id
	 */
	public void insertUser(@Param("user_id")int user_id,@Param("name")String name);
	
	/**
	 * 根据id获取积分
	 */
	public Double getIntegralById(int user_id);
	
	/**
	 * 加减积分设置新积分
	 */
	public void updateIntegralById(@Param("user_id")int user_id,@Param("integral")double integral);
	
	/**
	 * 查询是否有对应的id和名字
	 */
	public int existUser(@Param("user_id")Integer user_id,@Param("name")String name);

	/**
	 * 查询用户信息
	 */
	public Map<String,Object> getUser(Integer user_id);
	
	/**
	 * 查询与用户相关的任务
	 */
	public List<Object> getUserTask(Integer user_id);
	
	/**
	 * 查询与任务相关的优惠券
	 */
	public Integer[] getUserTaskCoupon(Integer task_id);
	
	/**
	 * 是否存在该任务
	 */
	public Integer existTask(Integer task_id);
	
	/**
	 * 查询是否有正在进行的同类型任务
	 */
	
	public Integer existTasking(Integer task_id);
	
	/**
	 * 领取任务
	 */
	public void acquireTask(@Param("task_id")Integer task_id,@Param("user_id")Integer user_id);
	
	/**
	 * 用户公益信息
	 */
	public List<Object> UserpublicWelfareList(@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize,@Param("user_id")Integer user_id);
	
	/**
	 * 查询详细的公益信息
	 */
	public Object publicWelfareDetail(@Param("user_id")Integer user_id,@Param("pw_id")Integer pw_id);
	
	/**
	 * 查询我的优惠券
	 */
	public List<Object> userCoupon(@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize,@Param("user_id")Integer user_id);
	
	/**
	 * 任务完成
	 */
	public void finishTask(@Param("user_id")Integer user_id,@Param("task_id")Integer task_id);
	
	/**
	 * 任务完成获取对应积分和经验值
	 */
	public Map<String,Object> acquireAward(@Param("user_id")Integer user_id,@Param("task_id")Integer task_id);
	
	/**
	 * 添加我的优惠券
	 */
	public void addUserCoupon(@Param("user_id")Integer user_id,@Param("uc_id")Integer uc_id,@Param("co_id")Integer co_id,@Param("create_time")Date create_time);
	
	/**
	 * 清掉用户领取了任务却没完成的
	 */
	public void clearUserTask();
	
	/**
	 * 完成任务却还没领取奖励的用户
	 */
	public List<Object> findUserAward();
	

	
}
