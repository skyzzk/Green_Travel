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

import api.app.service.AdvertisementService;


/**
 * 广告表控制层
 * @author sky
 *
 */
@CrossOrigin
@Controller
@RequestMapping("api")
public class AdvertisementController {
	@Autowired
	private AdvertisementService advertisementService;
	
	/**
	 * 列出所有广告
	 */
	@RequestMapping(value="getAllAdvertisement",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllAdvertisement() {
		return advertisementService.getAllAdvertisement();
	}
	
	/**
	 * 根据条件列出所有广告
	 */
	@RequestMapping(value="getAdvertisement",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAdvertisement(Integer currentPage,Integer pageSize,Integer status , String create_time) {
		return advertisementService.getAdvertisement(currentPage, pageSize, status, create_time);
	}
	
	
	/**
	 * 更新广告
	 */
	@RequestMapping(value="updateAdvertisement",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateAdvertisement(@RequestBody Map<String, Object> map){		
		if(map.containsKey("id") && map.containsKey("path") && map.containsKey("status") && map.containsKey("outside_link")){
			return advertisementService.updateAdvertisement(map);					
			}
		JSONObject js = new JSONObject();	
		js.put("code", 1);
        return js;
	}
	
	/**
	 * 批量删除广告
	 */
	@RequestMapping(value="deleteAllAdvertisement",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteAllAdvertisement(@RequestBody Map<String, int[]> map){
		JSONObject js = new JSONObject();	
		if(map.containsKey("ids")){
			int[] id = map.get("ids");
				return advertisementService.deleteAllAdvertisement(id);
		}
		js.put("code", 1);
		return js;		
	}
	
	/**
	 * 统计数量
	 */
	@RequestMapping(value="totalAdvertisement",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject totalAdvertisement(Integer status,String create_time){
		return advertisementService.totalAdvertisement(status, create_time);	
	}
		
}
