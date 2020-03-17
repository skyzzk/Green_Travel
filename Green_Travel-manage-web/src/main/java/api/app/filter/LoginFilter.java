package api.app.filter;

import java.io.PrintWriter;
import java.util.List;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import api.app.service.UserService;

public class LoginFilter implements HandlerInterceptor{

	/**
      preHandle方法是进行处理器拦截用的，顾名思义，该方法在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在，
     多个Interceptor，然后SpringMVC会根据声明的顺序一个接一个的执行，而且所有的Interceptor中的preHandler方法都会在Controller方法之前调用
     如果返回的是false的话就能够中断这个请求
	*/
	
	@Autowired
	private UserService userService;
	
	//不拦截"/loginForm"和"/login"请求
	private static final String[] IGNORE_URI = {"/githubLogin","/callback","/Login","/Loginout","Authentication"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
		//获取请求的路径进行判断
		String servletPath = request.getServletPath();

		
		//判断请求是否需要拦截
		for(String s : IGNORE_URI){
			//通过遍历IGNORE_URI数组中的元素，判断请求的路径是否包含IGNORE_URI数组中的值，如若有，则表示不需要进行拦截
			if(servletPath.contains(s)) {
				return true;
			}
		}
		
		//flag变量用于判断写返回失败信息
		boolean flag = false;		
		if(!flag) {
			Cookie[] cookies = request.getCookies();	
			String  cookieValue = null;
			//以下判断cookie
			if(cookies != null ){
	            for (Cookie cookie : cookies) {
	                if(cookie.getName().equals("CookieId")){
	                    cookieValue = cookie.getValue();
	                }
	            }
	        }else {
	        	flag = false;
	        }
			//以下判断session
			HttpSession session = request.getSession(true);
			String sessionId = session.getId();
			if(cookieValue != null && cookieValue.equals(sessionId)) {
				String ID =  session.getAttribute("id").toString();				
				Integer id = Integer.parseInt(ID);
				List<Integer> ids = userService.getAllUserId();
				for(int Id:ids) {
					if(id == Id) {
						flag = true;
						return flag;
					}
				}
			}
		}
		
		if(!flag) {
			//一旦被拦截，返回失败信息code = 1
			//重置response
	        response.reset();
	        //设置编码格式
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json;charset=UTF-8");
	        JSONObject js = new JSONObject();
			js.put("code",1);
	        PrintWriter pw = response.getWriter();	      
	        pw.append(js.toJSONString());
	        pw.flush();
	        pw.close();

		}

		System.out.println(flag);
		return flag;
	}
	
	/*public Map<String,Integer> Result(){
		Map<String,Integer> map =new HashMap<String,Integer>();
		map.put("code",1);
		return map;
	}
	*/
	
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {	
		response.setHeader("Access-Control-Allow-Origin", "*");		//设置跨域响应头
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
	
}
