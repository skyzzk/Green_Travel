package api.app.controller;




import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import api.app.service.CommodityService;
/**
 * 商品表控制层
 * @author sky
 *
 */
//@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
@RequestMapping("api")
public class CommodityController {
	@Autowired
	private CommodityService commodityService;
	
	/**
	 * 列出商品
	 * @return
	 */
	@RequestMapping(value="getCommodity",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCommodity() {	
		return commodityService.getAllCommodity();
	}
	
	/**
	 * 根据订单id查询商品
	 */
	@RequestMapping(value="getCommodityByid",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getCommodityByid(Integer id) {
		return commodityService.getCommodityByid(id);		

	}
	
	
	/**
	 * 添加保存商品
	 */
	
	@RequestMapping(value="insertCommodity",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertCommodity(@RequestBody Map<String, Object> map) {
		if( map.containsKey("name") && map.containsKey("description") && map.containsKey("integral") && map.containsKey("path") 
				&& map.containsKey("stock") && map.containsKey("status")){
			return commodityService.insertCommodity(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return commodityService.insertCommodity(js);
	}
	
	
	
	
	/**
	 * 批量删除商品
	 */
	@RequestMapping(value="deleteAllCommodity",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteAllCommodity(@RequestBody Map<String, Integer[]> map) {
		if(map.containsKey("ids")){
			Integer []ids = map.get("ids");
			return commodityService.deleteAllCommodity(ids);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	/**
	 * 更新商品
	 */
	@RequestMapping(value="updateCommodity",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateCommodity(@RequestBody Map<String, Object> map) {
		if(map.containsKey("id") && map.containsKey("name") && map.containsKey("description") && map.containsKey("stock")
				&& map.containsKey("integral") && map.containsKey("path") && map.containsKey("status")){			
		        return commodityService.updateCommodity(map);						
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	
	/**
	 * 模糊查询
	 */
	@RequestMapping(value="likeCommodity",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject likelist(Integer currentPage,Integer pageSize,String name) {
			return commodityService.likeCommodity(name,currentPage,pageSize);
	}
	/**
	 * 统计数量
	 */
	@RequestMapping(value="totalCommodity",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject totalCommodity(String name) {		
		return commodityService.totalCommodity(name);
	}
}