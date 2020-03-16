package api.app.service.impl;


import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import api.app.mapper.SignInMapper;
import api.app.mapper.UserMapper;
import api.app.pojo.SignIn;
import api.app.service.SignInService;
import api.app.utils.LoadFile;
import api.app.utils.RandomID;
/**
 * 签到表业务接口实现类
 * @author sky
 *
 */
@Service
public class SignInServiceimpl implements SignInService {
	
	@Autowired
	private SignInMapper signInMapper;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 插入用户签到
	 */
	@Override
	public JSONObject insertSignIn(Integer user_id) {
		//获取当月第几个月,年
		Calendar cal = Calendar.getInstance();
		Integer month = cal.get(Calendar.MONTH) + 1;
		Integer year = cal.get(Calendar.YEAR);
		JSONObject js = new JSONObject();
		JSONObject json = new JSONObject();	
		int code = 1;			
		try {
			RandomID randomID = new RandomID();
			SignIn signin = new SignIn();
			signin.setMonth(month);					//保存月
			signin.setYear(year);					//保存年
			signin.setUser_id(user_id);				//保存用户id
			Integer flag = signInMapper.isExist(user_id,month,year);	//查询是否存在user_id
			if(flag == 0) {
				signin.setId(randomID.randomID()); 		//随机获取ID保存id
				signInMapper.insertSignIn(signin);
			}	
			JSONObject data = (JSONObject) signInMapper.getDays(user_id,month,year);
			Integer id = Integer.parseInt(data.get("id").toString());						//获取id
			String str = (String) data.get("day");				//获取签到的天，数字		
			if(str.equals("")) {
				Integer []day = {};
				json.put("days",day);
			}else {
				//数组不为空
				String[] valueArr = str.split(",");					//已逗号切割开成数组		
				Integer []day = new Integer[valueArr.length];			
				for (Integer i = 0; i < valueArr.length; i++) {
				           day[i] = Integer.parseInt(valueArr[i]);		//转int;
				}			 
				json.put("days", day);
			}			
			json.put("id", id);									//对JSON对象放id
			code = 0;
			js.put("code",code);
			js.put("data", json);
			}catch(Exception e) {
				e.printStackTrace();
				js.put("code",code);
				return js;
			}
			return js;
	}
	
	/**
	 * 更新年月
	 */
	@Override
	public void updateSignIn(SignIn signin) {
		signInMapper.updateSignIn(signin);
	}
	
	
	/**
	 * 查询是否存在该用户
	 */
	@Override
	public int isExist(int user_id,int month,int year) {
		return signInMapper.isExist(user_id,month,year);
	}

	/**
	 * 获取id和签到的天
	 */
	@Override
	public Map<String,Object> getDays(int user_id,int month,int year){
		return signInMapper.getDays(user_id,month,year);
	}
	
	/**
	 * 获取签到的天
	 */
	@Override
	public String getday(int id,int month,int year) {
		return signInMapper.getday(id,month,year);
	}
	
	/**
	 * 签到
	 */
	@Override
	public JSONObject signin(Map<String,Object> map) {
		int code = 1;		
		//获取年，月，日
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int Day = cal.get(Calendar.DAY_OF_MONTH);
		String day = Integer.toString(Day);
		//获取签到id和积分,用户id
		int id = (int) map.get("id");
		int user_id = (int) map.get("user_id");
		//加载文件获取积分		
		String result = LoadFile.getProperty("properties/attribute.properties", "integrals");
		String[] array = result.split(",");					//已逗号切割开成数组			
		Double []Integral = new Double[array.length];
		for (int i = 0 ; i < array.length ; i++) {
			Integral[i] = 	Double.parseDouble(array[i]);	//获取31天的积分
	     }
		Double integral = null ;								//获得对应签到的积分	
		JSONObject js = new JSONObject();		
		try {		
			boolean flag = false;
			String str = signInMapper.getday(id,month,year);					//获取签到的天		
			if(str.equals("")) {									//判断是否为空
				flag = false;										
				integral = Integral[0];								//为空加的是第一天的积分
			}else {
				String[] valueArr = str.split(",");					//以逗号切割开成数组
				int len = valueArr.length;							//获取长度
				int []days = new int[len];
				for (int i = 0 ; i < len ; i++) {
					//重复签到判断
					days[i] = Integer.parseInt(valueArr[i]);
		            if(days[i] == Day){								//当前天数已经签到过了
		            	flag = true;
		            }		                      
				}
				//判断应该加多少积分,当签到断了时，重新开始计算天数
				int k = 0;
				for(int i = 0 ;i < len - 1 ;i++) {
					if(days[i] == days[i+1] - 1) {
						k++;
					}else {
						k = 0;
					}			
				}
				integral = Integral[++k];
				//如果断了，则第一次为Integral[0]积分
				if(days[len-1] != Day-1) {
					integral = Integral[0];
				}			
			}
			//验证成功，不会与当天重复签到
			if(!flag) {
				String newday = null;
				if(str.equals("")) {		//如果是空字符串，前面不用加逗号
					newday = day;
				}else {
					newday = str + "," + day;
				}			
				signInMapper.updateDay(newday, id);								//签到
				Double curr_integral = userMapper.getIntegralById(user_id);		//获取当前积分
				Double newintegral =  (curr_integral * 100 + integral * 100) / 100 ;
				userMapper.updateIntegralById(user_id, newintegral);	//积分相加修改积分
				code = 0;
				js.put("code", code);	
				js.put("data", newintegral);
			}			
		}catch(Exception e) {
			js.put("code", code);
			e.printStackTrace();
			return js;
		}				
		return js;
	}
	
}
