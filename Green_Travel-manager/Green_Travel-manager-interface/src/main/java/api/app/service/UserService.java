package api.app.service;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;






/**
 * 用户表业务接口
 * @author sky
 *
 */
public interface UserService {
	

	
	/**
	 * 查询用户信息
	 */
	public JSONObject  getUser(Integer user_id);
	
	/**
	 * 查询与用户相关的任务
	 */
	public JSONObject getUserTask(Integer user_id);
	

	/**
	 * 用户认证
	 */
	public JSONObject Authentication(Map<String,Object> map );
	
	/**
	 * 领取任务
	 */
	public JSONObject acquireTask(Map<String, Object> map);
	
	/**
	 * 用户公益信息
	 */
	public JSONObject UserpublicWelfareList(Integer currentPage,Integer pageSize,Integer user_id);
	
	/**
	 * 查询详细的公益信息
	 */
	public JSONObject publicWelfareDetail(Integer user_id,Integer pw_id);
	
	/**
	 * 查询我的优惠券
	 */
	public JSONObject userCoupon(Integer currentPage,Integer pageSize,Integer user_id);

	
	/**
	 * 任务完成获取对应积分和经验值
	 */
	public JSONObject acquireAward(Map<String, Integer> map);
	
	/**
	 * 获取所有用户id
	 */
	public List<Integer> getAllUserId();

}
