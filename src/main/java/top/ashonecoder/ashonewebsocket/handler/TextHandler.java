package top.ashonecoder.ashonewebsocket.handler;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import top.ashonecoder.ashonewebsocket.packet.TextPacket;
import top.ashonecoder.ashonewebsocket.exception.WSException;
import top.ashonecoder.ashonewebsocket.holder.WebSocketEntry;
import top.ashonecoder.ashonewebsocket.holder.WebSocketHolder;
import top.ashonecoder.ashonewebsocket.identity.AuthenticationIdentity;
import top.ashonecoder.ashonewebsocket.processor.container.ProcessContainer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author ashone
 * <p>
 * desc
 */

public class TextHandler extends TextWebSocketHandler {
    private static final WebSocketHolder webSocketHolder = new WebSocketHolder();
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ApplicationContext context;
    @Autowired
    ProcessContainer container;

    @Autowired
    AuthenticationIdentity identity;


    public static void writeTextToSession(String identity, String text) {
        webSocketHolder.doSomethingAboutSessions(identity, (session) -> {
            try {
                session.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                throw new WSException(e);
            }

        });
    }

    public String getSessionIdentity(WebSocketSession session) {
        String id = identity.getIdentity(session);
        if (id == null) {
            id = session.getId();
        }
        return id;
    }

    public void broadcast(TextPacket packet) {
        Collection<WebSocketEntry> collection = webSocketHolder.currentWebSocketEntries();
        for (var entry : collection) {
            if (packet.getIdentity().equals(entry.getIdentity())) {
                continue;
            }
            try {
                writeTextToSession(entry.getIdentity(), objectMapper.writeValueAsString(packet));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void broadcastIncludeMySelf(TextPacket textPacket) {
        Collection<WebSocketEntry> collection = webSocketHolder.currentWebSocketEntries();
        for (var entry : collection) {
            try {
                writeTextToSession(entry.getIdentity(), objectMapper.writeValueAsString(textPacket));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String identity = getSessionIdentity(session);
        WebSocketEntry webSocketEntry = webSocketHolder.removeWebSession(identity, session);
        if (webSocketHolder.getLiveNum(identity) <= 0) {
            TextPacket closePacket = TextPacket.builder().identity(identity).sign(MetaEvent.WEB_SOCKET_CLOSE.ordinal()).build();
            broadcast(closePacket);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = getSessionIdentity(session);
        WebSocketEntry webSocketEntry = webSocketHolder.putWebSocketSession(id, session);
        if (webSocketHolder.getLiveNum(id) <= 1) {
            TextPacket openPacket = TextPacket.builder().identity(id).sign(MetaEvent.WEB_SOCKET_OPEN.ordinal()).build();
            broadcastIncludeMySelf(openPacket);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        TextPacket packet = null;
        try {
            packet = objectMapper.readValue(message.getPayload(), TextPacket.class);

            MetaEvent metaEvent = MetaEvent.values()[packet.getSign()];
            if (!MetaEvent.SERVICE.equals(metaEvent)) {
                //TODO ping pong逻辑
                return;
            }
            String[] resourcePath = packet.getUri().split("/");//目前只支持两层路径
            Method method = container.getProcessor(resourcePath[0]).getPrrocessing(resourcePath[1]);

            Object res = method.invoke(

                    context.getBean(container.getProcessor(resourcePath[0]).getClass()), session, packet);
            if (res != null) {
                String jsonText = objectMapper.writeValueAsString(res);
                session.sendMessage(new TextMessage(jsonText));

            }

        } catch (JacksonException e) {
            session.sendMessage(new TextMessage("别往这发东西，臭傻逼"));
            throw new WSException(e);
        }


    }

    public Collection<WebSocketEntry> onlineWebSocketEntry() {

        return webSocketHolder.webSocketEntries();

    }


}
