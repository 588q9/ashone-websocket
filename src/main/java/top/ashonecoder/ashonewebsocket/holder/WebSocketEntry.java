package top.ashonecoder.ashonewebsocket.holder;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * @author ashone
 * <p>
 * desc
 */

@Data
@ToString
public class WebSocketEntry {

    private String identity;
    private List<WebSocketSession>
            webSocketSessions;


}
