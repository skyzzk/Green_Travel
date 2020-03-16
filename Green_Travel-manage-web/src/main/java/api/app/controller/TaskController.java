package api.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import api.app.service.TaskService;

/**
 * 任务表控制层
 * @author sky
 *
 */
@Controller
@RequestMapping("api")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	
	
	/**
	 * 插入与任务相关优惠券
	 */
	@RequestMapping(value="insertTask",method = RequestMethod.POST )
	@ResponseBody
	public JSONObject insertTask(@RequestBody JSONObject object) {	
		if(object.containsKey("title") && object.containsKey("aim") && object.containsKey("type") && object.containsKey("description") && object.containsKey("integral") 
				&& object.containsKey("growth") && object.containsKey("coupons")) {
			return taskService.insertTaskCoupon(object);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
	}
	
	/**
	 * 查询任务数量
	 */
	@RequestMapping(value="totalTask",method = RequestMethod.GET )
	@ResponseBody
	public JSONObject totalTask(Integer type){	
		return taskService.totalTask(type);
	}
	
	/**
	 * 根据类型查询任务
	 */
	@RequestMapping(value="listTaskBytype",method = RequestMethod.GET )
	@ResponseBody
	public JSONObject listTaskBytype(Integer type,Integer currentPage,Integer pageSize){		
		return taskService.listTaskBytype(type, currentPage, pageSize);
	}
	
	/**
	 * 查询与任务相关优惠券
	 */
	@RequestMapping(value="getCouponByid",method = RequestMethod.GET )
	@ResponseBody
	public JSONObject getCouponByid(Integer id){
		return taskService.getCouponByid(id);

	}
	
	/**
	 * 删除任务
	 */
	@RequestMapping(value="deleteTask",method = RequestMethod.POST )
	@ResponseBody
	public JSONObject deleteTask(@RequestBody Map<String, Integer[]> map){
		if(map.containsKey("ids")) {
			Integer []task_id = map.get("ids");
			return taskService.deleteTask(task_id);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
		
	}
	
	/**
	 * 修改任务
	 */
	@RequestMapping(value="updateTask",method = RequestMethod.POST )
	@ResponseBody
	public JSONObject updateTask(@RequestBody Map<String, Object> map){		
		if(map.containsKey("title") && map.containsKey("aim") && map.containsKey("type") && map.containsKey("description") && map.containsKey("integral") 
				&& map.containsKey("growth") && map.containsKey("coupons") && map.containsKey("id")) {
			return taskService.updateTask(map);
		}
		JSONObject js = new JSONObject();
		js.put("code", 1);
		return js;
			
	}
	
	/**
	 * 前台查询任务
	 */
	@RequestMapping(value="listTask",method = RequestMethod.GET )
	@ResponseBody
	public JSONObject  listTask(Integer currentPage,Integer pageSize){
		return taskService.listTask(currentPage, pageSize);
	}
	
	/**
	 * 根据任务id查任务和优惠券
	 */
	@RequestMapping(value="getTaskCouponByid",method = RequestMethod.GET )
	@ResponseBody
	public JSONObject  getTaskCouponByid(Integer id){
		return taskService.getTaskCouponByid(id);	
	}
	
	/**
	 * 检查任务是否达到目标
	 */
	@ResponseBody
	@RequestMapping(value = "checkTask", method = RequestMethod.POST )
	public JSONObject checkTask(@RequestBody Map<String,Object> map){
		return taskService.checkTask(map);
	}
	
	
}
