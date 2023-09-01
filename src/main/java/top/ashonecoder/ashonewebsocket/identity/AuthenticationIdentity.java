package top.ashonecoder.ashonewebsocket.identity;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author ashone
 * <p>
 * 获取在线用户在服务端的标识的lambda函数，需要在应用当中进行配置，此依赖会自动注入该类的实现
 */
public interface AuthenticationIdentity {

    String getIdentity(WebSocketSession webSession);

}
