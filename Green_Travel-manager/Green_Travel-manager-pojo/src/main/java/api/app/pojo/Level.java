package api.app.pojo;

import java.io.Serializable;

/**
 * 等级表实体类
 * @author sky
 *
 */
public class Level implements Serializable{
	private int user_id;	//用户id
	private int lv;			//用户等级
	private int growth;		//用户经验
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getGrowth() {
		return growth;
	}
	public void setGrowth(int growth) {
		this.growth = growth;
	}
	@Override
	public String toString() {
		return "Level [user_id=" + user_id + ", lv=" + lv + ", growth=" + growth + "]";
	}
	
	
}
