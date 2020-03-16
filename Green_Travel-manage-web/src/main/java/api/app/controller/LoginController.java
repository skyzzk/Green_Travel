package api.app.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.UserService;





/**
 * 第三方登录接口控制器
 * @author sky
 *
 */

	
	@CrossOrigin(origins = {"http://localhost:3000"})	//跨域网络端口3000才能访问
	@Controller
	@RequestMapping("api")
	public class LoginController {
		
		@Autowired
		private UserService userService;

	
		
		@ResponseBody
		@RequestMapping(value = "Authentication",method = RequestMethod.POST)
		public Map<String,Integer> Authentication(@RequestBody Map<String,Object> map , HttpServletRequest request , HttpServletResponse response) {
			int code = 1;
			Map<String,Integer> result = new HashMap<String,Integer>(); 
			if(map.containsKey("id") && map.containsKey("name")) {	
				Integer id = (int) map.get("id");
				String name = (String) map.get("name");
				System.out.println(id+name);
				JSONObject js = userService.Authentication(map);
				System.out.println(js);
				Integer flag = (Integer) js.get("code");
				if(flag==0) {
					//设置session属性
			       	HttpSession session = request.getSession();
					//Integer id = (int) map.get("id");
					//String name = (String) map.get("name");
			       	session.setAttribute("id", id);
			       	session.setAttribute("name", name);
			       	String sessionId = session.getId();		//sessionId 从你点击开页面(会话一开始)的时候就已经确定了或者说是存在了
			       	//设置cookie名字
			        Cookie tokenCookie = new Cookie("CookieId",sessionId);
			       	tokenCookie.setMaxAge(60*30);			//设置时间
			        tokenCookie.setPath("/");
			        response.addCookie(tokenCookie);        //添加cookie			        
					code = 0;
				}
					
			}			
			result.put("code", code);		
			return result;
		}
		
		
		
		
		
	    /*String client_id = "Iv1.eb1ae6e33d0c1846";											//第三方接口服务id
	    String redirect_uri = "http://localhost:8080/green_travel/api/callback.action";		//回调路径
	
	    
	    *//**
	     * 跳转github获取code&state
	     * @return
	     *//*
		@RequestMapping("githubLogin")
		public String githubLogin() {
		    String githubState = RandomStringUtils.randomAlphanumeric(10);	//随机字符串
		    String url = "https://github.com/login/oauth/authorize";		//第三方登录github路径
	        return "redirect:" + url + "?client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&state=" + githubState;
	    }
		
		
		
		
		
		*//**
		 * 获取用户登录信息
		 *//*
		
		@RequestMapping(value = "callback", method = RequestMethod.GET)
	    public String githubCallback(String code, String state) {


	        // Step2：通过 Authorization Code 获取 AccessToken
	        String githubAccessTokenResult = HttpUtil.sendGet("https://github.com/login/oauth/access_token",
	                "client_id=" + client_id + "&client_secret=c3199eab8e3bf0028862a0ce02fb82e7f0a5937c&code=" + code +
	                        "&redirect_uri=" + redirect_uri);

	        //System.out.println("==>githubAccessTokenResult: " + githubAccessTokenResult);

	        *//**
	         * 以 & 为分割字符分割 result
	         *//*
	        String[] githubResultList = githubAccessTokenResult.split("&");
	        List<String> params = new ArrayList<>();

	        // paramMap 是分割后得到的参数对, 比说 access_token=ad5f4as6gfads4as98fg
	        for (String paramMap : githubResultList) {
	            if (!"scope".equals(paramMap.split("=")[0])){
	                // 再以 = 为分割字符分割, 并加到 params 中
	                params.add(paramMap.split("=")[1]);
	            }
	        }

	        //此时 params.get(0) 为 access_token;  params.get(1) 为 token_type

	        // Step2：通过 access_token 获取用户信息
	        String githubInfoResult = HttpUtil.sendGet("https://api.github.com/user","access_token="+params.get(0));
	        System.out.println(githubInfoResult);
	        Map<String,Object> json =  (Map<String,Object>) JSONObject.parse(githubInfoResult);
	        //获取id和名字
	        Integer id = (Integer) json.get("id");
	        String name = (String) json.get("name");

	        //判断是否存在用户
	        List<Integer> ids = userService.getAllUserId();
	        //设置条件
	        boolean flag = true;		
	        //循环查找
	        for(int user_id : ids) {
	        	if(id == user_id) {
	        		flag = false;
	        	}	        	
	        }
	        if(flag) {
        		//向数据库用户表插入id
        		userService.insertUser(id,name);
        	}
	        return "redirect:http://localhost:8080/green_travel/api/Login.action?id=" + id + "&name=" + name;
	    }
		
		*//**
		 * 登录成功重定向
		 * @param request
		 * @param id
		 * @param name
		 * @param response
		 * @return
		 *//*
		
		@ResponseBody
		@RequestMapping(value = "Login",method = RequestMethod.GET)
	    public String login(HttpServletRequest request ,Integer id,String name, HttpServletResponse response){
			
			//设置session属性
	       	HttpSession session = request.getSession();
	       	session.setAttribute("id", id);
	       	session.setAttribute("name", name);
	       	String sessionId = session.getId();		//sessionId 从你点击开页面(会话一开始)的时候就已经确定了或者说是存在了
	       	//设置cookie名字
	        Cookie tokenCookie = new Cookie("CookieId",sessionId);
	       	tokenCookie.setMaxAge(60*30);			//设置时间
	        tokenCookie.setPath("/");
	        response.addCookie(tokenCookie);        //添加cookie

	        //return "redirect:http://localhost:3000";
	        return "success";
		}
		
		*/
		
		/**
		 * 退出登录、
		 * @param request
		 * @param response
		 * @return
		 */
		
		@RequestMapping(value = "Loginout",method = RequestMethod.GET)
		@ResponseBody
	    public Map<String, Integer> logout(HttpServletRequest request,HttpServletResponse response){
	        //删除cookie
			
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie :cookies){						//遍历所有Cookie
	            if(cookie.getName().equals("CookieId")){		//找到对应的cookie
	                cookie.setMaxAge(0);						//Cookie并不能根本意义上删除，只需要这样设置为0即可
	                cookie.setPath("/");						//很关键，设置成跟写入cookies一样的，全路径共享Cookie
	                response.addCookie(cookie);					//重新响应
	            }
	        }
	        request.getSession().removeAttribute("id");
	        request.getSession().invalidate();					//清除属性
	        Map<String,Integer> map = new HashMap<String,Integer>();
	        map.put("code", 0);
	        return map;
	    }
		
		

		
}
