package api.app.pojo;

import java.io.Serializable;

/**
 * 签到表实体类
 * @author sky
 *
 */

public class SignIn implements Serializable{
	private int user_id;		//用户id
	private int id;				//签到id
	private String day;			//签到天
	private int month;			//签到月
	private int year;			//签到年
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "SignIn [user_id=" + user_id + ", id=" + id + ", day=" + day + ", month=" + month + ", year=" + year
				+ "]";
	}
	
	
}
