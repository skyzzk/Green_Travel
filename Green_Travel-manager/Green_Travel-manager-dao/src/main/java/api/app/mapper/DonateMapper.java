package api.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import api.app.pojo.Donate;

/**
 * 捐赠表持久化接口
 * @author sky
 *
 */
public interface DonateMapper {

	/**
	 * 查询是否已经捐赠过该公益活动
	 */
	public int everDonate(@Param("pw_id")Integer pw_id,@Param("user_id")Integer user_id);
	
	/**
	 * 减少用户积分
	 */
	public void subDonate(@Param("integral")Double integral,@Param("user_id")Integer user_id);
	
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
	public List<Object> getDonate(@Param("pw_id")Integer pw_id,@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	
	
}
