package api.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.pojo.PublicWelfare;
import api.app.service.PublicWelfareService;
import api.app.utils.RandomID;

/**
* 公益表控制层
* 
* @author sky
*
*/
@RequestMapping("api")
@Controller
public class PublicWelfareController {
	
	@Autowired
	private PublicWelfareService publicWelfareService;
	
	
	/**
	 * 获取所有未删除公益信息
	 */
	@ResponseBody
	@RequestMapping(value="getAllPublicWelfare",method = RequestMethod.GET)
	public JSONObject getAllPublicWelfare(String name,Integer currentPage,Integer pageSize){
		return publicWelfareService.getAllPublicWelfare(name,currentPage,pageSize);
	}
	
	
	/**
	 * 统计数量
	 */
	@ResponseBody
	@RequestMapping(value="totalPublicWelfare",method = RequestMethod.GET)
	public JSONObject totalPublicWelfare(String name){
		return publicWelfareService.totalPublicWelfare(name);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping(value="deletePublicWelfare",method = RequestMethod.POST)
	public JSONObject deletePublicWelfare(@RequestBody Map<String,Integer[]> map){
		if(map.containsKey("ids")){
			Integer[] ids = map.get("ids");
				return publicWelfareService.deletePublicWelfare(ids);

		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	/**
	 *修改状态 
	 */
	@ResponseBody
	@RequestMapping(value="updateStatus",method = RequestMethod.POST)
	public JSONObject updateStatus(@RequestBody Map<String, Object> map){
		if(map.containsKey("status") && map.containsKey("reason") && map.containsKey("id")){
			return publicWelfareService.updateStatus(map);			
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	/**
	 * 保存公益申请
	 */
	@ResponseBody
	@RequestMapping(value="insertPublicWelfare",method = RequestMethod.POST)
	public JSONObject insertPublicWelfare(@RequestBody Map<String, Object> map){
		if(map.containsKey("user_id") && map.containsKey("title") && map.containsKey("integral") && map.containsKey("description") 
				&& map.containsKey("end_time") && map.containsKey("path")){
			return publicWelfareService.insertPublicWelfare(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;					
	}
	
	/**
	 * 获取所有发布的公益信息
	 */
	@ResponseBody
	@RequestMapping(value="getPublicWelfare",method = RequestMethod.GET)
	public JSONObject getPublicWelfare(Integer currentPage,Integer pageSize){		
		return publicWelfareService.getPublicWelfare(currentPage,pageSize);
	}
	
	@ResponseBody
	@RequestMapping(value="getPublicWelfareByid",method = RequestMethod.GET)
	public JSONObject getPublicWelfareByid(Integer id){		
		return publicWelfareService.getPublicWelfareByid(id);
	}
	
	
}
