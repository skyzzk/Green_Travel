package api.app.controller;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.SignInService;


@Controller
@RequestMapping("api")
public class SignInController {
	
	@Autowired
	private SignInService signInService;
	

	
	/**
	 * 插入用户签到
	 */
	@ResponseBody
	@RequestMapping(value = "defaultSignin", method = RequestMethod.GET )
	public JSONObject defaultSignin(HttpServletRequest req,Integer user_id){
		return signInService.insertSignIn(user_id);
	}
	
	/**
	 * 签到兑换积分
	 * @param map
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "Signin", method = RequestMethod.POST )
	public Map<String,Object> signin(@RequestBody Map<String,Object> map) {
		if(map.containsKey("id") && map.containsKey("user_id")) {
			return signInService.insertsignin(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
}
