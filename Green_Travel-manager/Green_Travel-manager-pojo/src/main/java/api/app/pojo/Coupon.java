package api.app.pojo;

import java.io.Serializable;

/**
 * 优惠券实体类
 * @author sky
 *
 */
public class Coupon implements Serializable{
	
	private Integer co_id;		//优惠券
	private Integer day;		//有效天数
	private String description;	//简介
	private Double integral;	//积分
	private Integer exist;		//是否删除
	public Integer getCo_id() {
		return co_id;
	}
	public void setCo_id(Integer co_id) {
		this.co_id = co_id;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
	public Integer getExist() {
		return exist;
	}
	public void setExist(Integer exist) {
		this.exist = exist;
	}
	@Override
	public String toString() {
		return "Coupon [co_id=" + co_id + ", day=" + day + ", description=" + description + ", integral=" + integral
				+ ", exist=" + exist + "]";
	}
	
	
}
