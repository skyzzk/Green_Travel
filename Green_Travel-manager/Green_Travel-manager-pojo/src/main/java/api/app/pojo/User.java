package api.app.pojo;

import java.io.Serializable;

public class User implements Serializable{
	private Integer user_id;			//用户id
	private String name;				//用户名字
	private Double integral;			//用户积分
	private Integer bicycle_mileage;	//骑行公里
	private Integer bicycle_count;		//骑行次数
	private Integer subway_count;		//地铁次数
	private Integer bus_count;			//公车次数
	private Integer rail_count;			//高铁次数
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
	public Integer getBicycle_mileage() {
		return bicycle_mileage;
	}
	public void setBicycle_mileage(Integer bicycle_mileage) {
		this.bicycle_mileage = bicycle_mileage;
	}
	public Integer getBicycle_count() {
		return bicycle_count;
	}
	public void setBicycle_count(Integer bicycle_count) {
		this.bicycle_count = bicycle_count;
	}
	public Integer getSubway_count() {
		return subway_count;
	}
	public void setSubway_count(Integer subway_count) {
		this.subway_count = subway_count;
	}
	public Integer getBus_count() {
		return bus_count;
	}
	public void setBus_count(Integer bus_count) {
		this.bus_count = bus_count;
	}
	public Integer getRail_count() {
		return rail_count;
	}
	public void setRail_count(Integer rail_count) {
		this.rail_count = rail_count;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", integral=" + integral + ", bicycle_mileage="
				+ bicycle_mileage + ", bicycle_count=" + bicycle_count + ", subway_count=" + subway_count
				+ ", bus_count=" + bus_count + ", rail_count=" + rail_count + "]";
	}
	
}
