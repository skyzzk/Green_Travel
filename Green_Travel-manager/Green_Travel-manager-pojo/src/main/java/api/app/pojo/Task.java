package api.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 任务表实体类
 * @author sky
 *
 */
public class Task implements Serializable{

	private Integer task_id;		//任务id
	private String title;			//任务标题
	private String description;		//简介
	private Double integral;		//积分
	private Integer growth;			//经验值
	private Integer type;			//任务类型
	private Integer aim;			//目标值
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date create_time;		//创建时间
	private Integer exist;			//是否删除
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getGrowth() {
		return growth;
	}
	public void setGrowth(Integer growth) {
		this.growth = growth;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getAim() {
		return aim;
	}
	public void setAim(Integer aim) {
		this.aim = aim;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getExist() {
		return exist;
	}
	public void setExist(Integer exist) {
		this.exist = exist;
	}
	@Override
	public String toString() {
		return "Task [task_id=" + task_id + ", title=" + title + ", description=" + description + ", integral="
				+ integral + ", growth=" + growth + ", type=" + type + ", aim=" + aim + ", create_time=" + create_time
				+ ", exist=" + exist + "]";
	}
	
	
	
}
