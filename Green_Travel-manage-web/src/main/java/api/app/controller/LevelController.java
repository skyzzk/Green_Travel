package api.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.LevelService;

/**
 * 等级控制层
 * @author sky
 *
 */
@Controller
@RequestMapping("api")
public class LevelController {
	
	@Autowired
	private LevelService levelService;
	
	/**
	 * 返回等级比例
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "getAllLevel", method = RequestMethod.GET)
	public Map<String,Object> getAllLevel(){
		return levelService.getAllLevel();
	}
}
