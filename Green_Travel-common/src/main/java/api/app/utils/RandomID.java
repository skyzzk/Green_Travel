package api.app.utils;

import java.util.UUID;

public class RandomID {
	
	public int randomID() {
		//随机生成一位整数
        //int random = (int) (Math.random()*9+1);
        //String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if(hashCode<0){
            hashCode = -hashCode;
        }
        //String value = valueOf + String.format("%015d", hashCode);
        return hashCode;
	}
	
}
