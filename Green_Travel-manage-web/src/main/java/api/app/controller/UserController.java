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

import api.app.service.LevelService;
import api.app.service.TaskService;
import api.app.service.UserService;

/**
 * 用户信息控制层
 * @author sky
 *
 */
@CrossOrigin(origins = {"http://localhost:3000"})	//跨域网络端口3000才能访问
@Controller
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private LevelService levelService;
	
	@Autowired
	private TaskService taskService;

	
	/**
	 * 返回user信息
	 */
	/*
	@RequestMapping(value = "LoginUser",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> LoginUser(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();	
		String  cookieValue = null;
		Integer id = null;
		String name = null;
		int code = 1;
		//以下判断cookie
		if(cookies != null ){
           for (Cookie cookie : cookies) {
               if(cookie.getName().equals("CookieId")){
                   cookieValue = cookie.getValue();
                   
                 //以下判断session
                   HttpSession session = request.getSession(true);
       			String sessionId = session.getId();
       			
       			if(cookieValue != null && cookieValue.equals(sessionId)) {
       				String ID =  session.getAttribute("id").toString();		
       				id = Integer.parseInt(ID);
       				List<Integer> ids = userService.getAllUserId();
       				for(int Id:ids) {
       					if(id == Id) {
       						name = session.getAttribute("name").toString();
       						code = 0;

       					}
       				}
       			}
               }
           }
       }
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> user = new HashMap<String,Object>();		
		if(code == 0) {
			Map<String,Object> level =  levelService.getLevel(id);
			int lv = (int)level.get("lv");
			int growth = (int) level.get("growth");
			map.put("code", code);
			user.put("id", id);
		    user.put("name", name);
		    user.put("lv", lv);
		    user.put("growth", growth);
		    map.put("user", user);
							
		}else {
			map.put("code", code);
		}        
		
		return map;
	}*/
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value = "getUser",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUser(Integer id){
		return userService.getUser(id);	
	}
	
	
	/**
	 * 用户领取任务
	 */
	@RequestMapping(value = "acquireTask",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject acquireTask(@RequestBody Map<String, Object> map){
		if(map.containsKey("id") && map.containsKey("user_id")){
			return userService.acquireTask(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
		
	}
	
	/**
	 * 用户公益数据  destory为0的数据，无论exist为何值
	 */
	
	@RequestMapping(value = "UserpublicWelfareList",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject UserpublicWelfareList(Integer currentPage,Integer pageSize,Integer user_id){
		return userService.UserpublicWelfareList(currentPage, pageSize, user_id);
	}
		
	/**
	 * 查询详细的公益信息
	 */
	@RequestMapping(value = "publicWelfareDetail",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject publicWelfareDetail(Integer id,Integer user_id){
		return  userService.publicWelfareDetail(user_id,id);	
	}
	
	/**
	 * 查询我的优惠券
	 */
	@RequestMapping(value = "userCoupon",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject userCoupon(Integer currentPage,Integer pageSize,Integer user_id){
		return userService.userCoupon(currentPage, pageSize, user_id);
	}
	
	/**
	 * 查询已接受的任务
	 */
	@RequestMapping(value = "getUserTask",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserTask(Integer user_id){
		return userService.getUserTask(user_id);
	}
	
	/**
	 * 领取任务奖励
	 */
	@RequestMapping(value = "TaskAward",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject TaskAward(@RequestBody Map<String, Integer> map){
		if(map.containsKey("task_id") && map.containsKey("id")) {
			return userService.acquireAward(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;	
	}
	
}
