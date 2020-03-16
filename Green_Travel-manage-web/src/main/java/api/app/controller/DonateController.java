package api.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.DonateService;

/**
* 捐赠表控制层
*
* @author sky
*
*/
@RequestMapping("api")
@Controller
public class DonateController {
	
	@Autowired
	private DonateService donateService;	
	
	
	
	/**
	 * 用户捐赠
	 */
	@ResponseBody
	@RequestMapping(value="userDonate",method = RequestMethod.POST )
	public JSONObject userDonate(@RequestBody Map<String,Object> map){
		if(map.containsKey("id") && map.containsKey("user_id") && map.containsKey("integral") ) {
			return donateService.updateUserdonate(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	/**
	 * 查询捐赠信息
	 */
	@ResponseBody
	@RequestMapping(value="getDonate",method = RequestMethod.GET )
	public JSONObject getDonate(Integer id,Integer currentPage,Integer pageSize){
		return donateService.getDonate(id, currentPage, pageSize);
	}
	
	
	
}
