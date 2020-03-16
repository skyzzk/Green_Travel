package api.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import api.app.pojo.PublicWelfare;

/**
 * 公益表持久化接口
 * @author sky
 *
 */
public interface PublicWelfareMapper {
	
	/**
	 * 查询用户公益
	 */
	public List<Object> getAllPublicWelfare(@Param("name")String name,@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 统计数量
	 */
	public int totalPublicWelfare(@Param("name")String name);
	
	/**
	 * 删除
	 */
	public void deletePublicWelfare(Integer []ids);
	
	/**
	 * 修改状态
	 */
	public void updateStatus(@Param("id")Integer id,@Param("status")Integer status,@Param("reason")String reason);
	
	/**
	 * 保存公益申请
	 */
	public void insertPublicWelfare(PublicWelfare publicWelfare);
	
	/**
	 * 查询发布的公益
	 */
	public List<Object>	getPublicWelfare(@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据id查询发布的公益
	 */
	public Object getPublicWelfareByid(Integer pw_id);
}
