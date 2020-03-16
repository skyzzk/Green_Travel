package api.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品表实体
 * @author sky
 *
 */
public class Commodity implements Serializable{
	private int com_id;		//商品ID
	private String com_name;	//商品名字
	private String com_intro;	//商品介绍
	private double com_value;		//商品价格
	private String com_image;	//商品图片路径
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date create_time;	//创建时间
	private int exist;			//是否删除
	private int com_stock;		//存储数量
	private int status;			//状态
	public int getCom_id() {
		return com_id;
	}
	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getCom_intro() {
		return com_intro;
	}
	public void setCom_intro(String com_intro) {
		this.com_intro = com_intro;
	}
	public double getCom_value() {
		return com_value;
	}
	public void setCom_value(double com_value) {
		this.com_value = com_value;
	}
	public String getCom_image() {
		return com_image;
	}
	public void setCom_image(String com_image) {
		this.com_image = com_image;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getExist() {
		return exist;
	}
	public void setExist(int exist) {
		this.exist = exist;
	}
	public int getCom_stock() {
		return com_stock;
	}
	public void setCom_stock(int com_stock) {
		this.com_stock = com_stock;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Commodity [com_id=" + com_id + ", com_name=" + com_name + ", com_intro=" + com_intro + ", com_value="
				+ com_value + ", com_image=" + com_image + ", create_time=" + create_time + ", exist=" + exist
				+ ", com_stock=" + com_stock + ", status=" + status + "]";
	}

	
	
	
}
