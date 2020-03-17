package api.app.filter;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;



public class PreventRepeat implements HandlerInterceptor {
	 
	 /**
     * 验证同一个url数据是否相同提交,相同返回true
     * @param request
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 
    	 try {
    		 
    		 
             // 1. 将请求参数转换为json字符串 需要在pom内引用jackson-databind
             ObjectMapper objectMapper = new ObjectMapper();
             String params = objectMapper.writeValueAsString(request.getParameterMap());
  
             // 2. 获取当前请求的url地址 并以url为key 参数为值存在map内
             String url=request.getRequestURI();
             Map<String,String> map=new HashMap<String, String>(4);
             map.put(url, params);
             String nowUrlParams=map.toString();
  
             // 3. 获取session中上一次请求存储的url和参数字符串
             Object preUrlParams=request.getSession().getAttribute("oldUrlParams");
  
             // 4. 如果上一个数据为null,表示还没有访问页面 将当前方位的url和请求参数存储到session中
             if(preUrlParams == null) {
            	 request.getSession().setAttribute("oldUrlParams", nowUrlParams); 
                 return true;
             } else {  
                 // 5. 判断上一次访问的url和参数与本次是否相同 如相同则表示重复数据
                 if(preUrlParams.toString().equals(nowUrlParams))
                 {
                     return false;
                 }else{
                	 request.getSession().setAttribute("oldUrlParams", nowUrlParams);
                     return true;
                 }  
             }
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
    }
 
   
  
    
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {	
		request.getSession().removeAttribute("oldUrlParams");
	}




	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}

