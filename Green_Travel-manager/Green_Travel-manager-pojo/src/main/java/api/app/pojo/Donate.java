package api.app.pojo;

import java.io.Serializable;

/**
 * 捐赠表实体类
 * @author sky
 *
 */
public class Donate implements Serializable{
	private Integer id;			//捐赠id
	private Integer user_id;	//用户id
	private Integer pw_id;		//公益id
	private Double 	Integral;	//捐赠积分
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getPw_id() {
		return pw_id;
	}
	public void setPw_id(Integer pw_id) {
		this.pw_id = pw_id;
	}
	public Double getIntegral() {
		return Integral;
	}
	public void setIntegral(Double integral) {
		Integral = integral;
	}
	@Override
	public String toString() {
		return "Donate [id=" + id + ", user_id=" + user_id + ", pw_id=" + pw_id + ", Integral=" + Integral + "]";
	}
	
	
}
