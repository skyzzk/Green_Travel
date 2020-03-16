package api.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Advertisement implements Serializable{
	private int ad_id;
	private String ad_path;
	@JsonFormat(pattern="yyyy-MM-dd:hh:mm:ss",timezone="GMT+8")
	private Date ad_date;
	private String ad_link;
	private int ad_status;
	private int ad_exist;
	public int getAd_id() {
		return ad_id;
	}
	public void setAd_id(int ad_id) {
		this.ad_id = ad_id;
	}
	public String getAd_path() {
		return ad_path;
	}
	public void setAd_path(String ad_path) {
		this.ad_path = ad_path;
	}
	public Date getAd_date() {
		return ad_date;
	}
	public void setAd_data(Date ad_date) {
		this.ad_date = ad_date;
	}
	public String getAd_link() {
		return ad_link;
	}
	public void setAd_link(String ad_link) {
		this.ad_link = ad_link;
	}
	public int getAd_status() {
		return ad_status;
	}
	public void setAd_status(int ad_status) {
		this.ad_status = ad_status;
	}
	public int getAd_exist() {
		return ad_exist;
	}
	public void setAd_exist(int ad_exist) {
		this.ad_exist = ad_exist;
	}
	@Override
	public String toString() {
		return "Advertisement [ad_id=" + ad_id + ", ad_path=" + ad_path + ", ad_data=" + ad_date + ", ad_link="
				+ ad_link + ", ad_status=" + ad_status + ", ad_exist=" + ad_exist + "]";
	}
	
}
