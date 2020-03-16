package api.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单表实体
 * @author sky
 *
 */
public class Order implements Serializable{
	private int order_id;				//订单id
	private int user_id;				//用户id
	private int com_id;					//商品id
	private String serial_number;		//序列号
	private int quantity;				//数量
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date create_time;			//创建时间
	private int exist;					//是否删除	
	private Commodity commodity; 		//商品实体
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCom_id() {
		return com_id;
	}
	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}	
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	
	
	
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", user_id=" + user_id + ", com_id=" + com_id + ", serial_number="
				+ serial_number + ", quantity=" + quantity + ", create_time=" + create_time + ", exist=" + exist + "]";
	}
	public Commodity getCommodity() {
		return commodity;
	}
	
	
	
}
