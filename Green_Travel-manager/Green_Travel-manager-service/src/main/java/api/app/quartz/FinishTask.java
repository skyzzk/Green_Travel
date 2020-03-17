package api.app.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.LevelMapper;
import api.app.mapper.UserMapper;
import api.app.utils.LoadFile;
import api.app.utils.RandomID;

public class FinishTask {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LevelMapper levelMapper;
	
	// 首先在controller类里注入事务管理器，name的值为配置文件里的事务管理器的名称
	@Resource(name = "transactionManager")
	private DataSourceTransactionManager transactionManager;
	
	public void execute() throws Exception {
 
		DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();				// 开启新事务
		transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);	// 事物隔离级别，开启新事务
		TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);				// 获得事务状态
		
        try {
        	
        	/**
        	 * 发放奖励，发放完成任务却还没领取奖励的用户
        	 */
        	//先找出完成任务却没领取奖励的用户id和任务id
        	List<Object> list = userMapper.findUserAward();
        	for(int i = 0 ; i < list.size(); i++) {
        		JSONObject obj = (JSONObject) JSONObject.toJSON(list.get(i));
        		Integer user_id = Integer.parseInt(obj.get("user_id").toString());				//获取用户id	
        		Integer task_id = Integer.parseInt(obj.get("task_id").toString());  			//获取任务id
        		//完成任务修改字段
        		userMapper.finishTask(user_id, task_id);
    			//获取对应积分和经验值
    			Map<String,Object> award = userMapper.acquireAward(user_id, task_id);
    			Integer growth = Integer.parseInt(award.get("growth").toString());	//经验值
    			Double integral =  Double.valueOf(award.get("integral").toString());//积分
    			//增加积分
    			Double cur_integral = userMapper.getIntegralById(user_id);
    			Double new_integral = ((cur_integral*100) + (integral*100))/100;
    			userMapper.updateIntegralById(user_id, new_integral);		
    			//增加经验值
    			levelMapper.addGrowth(user_id, growth);
    			/**
    			 * 是否可以升级
    			 */				
    			Map<String,Object> level = levelMapper.getGrowth(user_id);//查询当前等级和经验
    			Integer cur_growth = Integer.parseInt(level.get("growth").toString());		//当前经验值
    			Integer lv = Integer.parseInt(level.get("lv").toString());				//当前等级											//当前等级
    			//加载文件得到对应等级经验值界限
    			String result = LoadFile.getProperty("attribute.properties", "growths");
    			String[] array = result.split(",");					//已逗号切割开成数组			
    			Integer []growths = new Integer[array.length];
    			for (int j = 0 ; j < array.length ; j++) {
    				growths[j] = 	Integer.parseInt(array[j]);		//获取15个等级升级所需经验
    		     }
    			//如果当前经验大于界限，升级
    			if(growths[lv + 1] < cur_growth) {
    				if(lv < 15) {
    					lv = lv + 1;
    				}
    				levelMapper.levelUpgrade(growth - growths[lv + 1], user_id, lv);
    			}
    			
    			//添加我的优惠券
    			Integer []coupons = userMapper.getUserTaskCoupon(task_id);	
    			//生成时间
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
    			Date create_time = sdf.parse(sdf.format(new Date()));
    			for(int k = 0;k < coupons.length;k++) {
    				//生成ID
    				RandomID Id = new RandomID();
    				int uc_id = Id.randomID();
    				//添加我的优惠券
    				userMapper.addUserCoupon(user_id, uc_id, coupons[k], create_time);
    			}
        		
        	}
        	
        	/**
        	 * 清掉用户任务
        	 */     	
        	userMapper.clearUserTask();
        	
			transactionManager.commit(transStatus);								//提交事务
        }catch(Exception e) {
        	e.printStackTrace();
        	transactionManager.rollback(transStatus);	// 异常回滚
        }		
    }

}
