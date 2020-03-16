package api.app.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商品表持久化接口
 * @author sky
 *
 */
import api.app.pojo.Commodity;

/**
 * 商品表持久化接口
 * @author sky
 *
 */
public interface CommodityMapper {

	/**
	 * 根据商品id查询商品
	 */
	public Commodity getCommodityByid(Integer com_id);
	
	
	/**
	 * 获取所有商品
	 */
	public List<Commodity> getAllCommodity();
	
	/**
	 * 保存商品
	 */
	public void insertCommodity(Commodity commodity);
	
	/**
	 * 删除商品
	 */
	public void deleteCommodity(Integer com_id);
	/**
	 * 批量删除商品
	 */
	public void deleteAllCommodity(Integer[] com_id);
	
	/**
	 * 更新商品
	 */
	public void updateCommodity(Commodity commodity);
	
	/**
	 * 模糊查询
	 */
	public List<Commodity> likeCommodity(@Param("com_name")String com_name,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);
	
	/**
	 * 统计数量
	 */
	public int totalCommodity(@Param("com_name")String com_name);
	
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
