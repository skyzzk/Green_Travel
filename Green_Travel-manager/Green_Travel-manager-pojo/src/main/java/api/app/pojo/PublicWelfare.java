package api.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公益表实体类
 */
public class PublicWelfare implements Serializable{

	private Integer pw_id;					//公益id
	private Integer user_id;				//用户id
	private String title;					//公益标题
	private Double integral;				//公益积分
	private String description;				//公益简简介
	private String reason;					//公益审核结果
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date create_time;				//创建时间
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date end_time;					//截止时间
	private String path;					//图片路径
	private Integer destory;				//用户删除
	private Integer exist;					//管理员删除
	private Integer status;					//公益状态
	public Integer getPw_id() {
		return pw_id;
	}
	public void setPw_id(Integer pw_id) {
		this.pw_id = pw_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getDestory() {
		return destory;
	}
	public void setDestory(Integer destory) {
		this.destory = destory;
	}
	public Integer getExist() {
		return exist;
	}
	public void setExist(Integer exist) {
		this.exist = exist;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PublicWelfare [pw_id=" + pw_id + ", user_id=" + user_id + ", title=" + title + ", integral=" + integral
				+ ", description=" + description + ", reason=" + reason + ", create_time=" + create_time + ", end_time="
				+ end_time + ", path=" + path + ", destory=" + destory + ", exist=" + exist + ", status=" + status
				+ "]";
	}
	
}
