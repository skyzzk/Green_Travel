package api.app.listen;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import api.app.redis.JedisClient;


/**
 * 接收消息的监听器
 */
public class ItemChangeMessageListener implements MessageListener {

	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	
	@Override
	public void onMessage(Message message) {
		//判断消息的类型是否为textmessage
		if(message instanceof TextMessage){
			
			try {			
				//如果是 获取商品的id 
				TextMessage msg = (TextMessage)message;
				String Id = msg.getText();		
				//删除缓存
				if(StringUtils.isNotBlank(Id)){
					client.hdel(Id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

