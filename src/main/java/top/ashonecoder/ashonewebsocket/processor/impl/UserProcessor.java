package top.ashonecoder.ashonewebsocket.processor.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import top.ashonecoder.ashonewebsocket.annotation.Processing;
import top.ashonecoder.ashonewebsocket.packet.TextPacket;
import top.ashonecoder.ashonewebsocket.processor.AbstractProcessor;
import top.ashonecoder.ashonewebsocket.processor.container.ProcessContainer;

/**
 * @author ashone
 * <p>
 * desc
 */

@Component
@Processing(value = "user")
public class UserProcessor extends AbstractProcessor {


    public TextPacket pingSend(WebSocketSession session,TextPacket textPacket

    ){

//业务逻辑
return TextPacket.message("逻辑标识（例如当用户登录了，那么便为用户的id）","hello");
    }

}
