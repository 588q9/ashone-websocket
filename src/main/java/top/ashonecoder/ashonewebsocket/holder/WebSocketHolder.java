package top.ashonecoder.ashonewebsocket.holder;

import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author ashone
 * <p>
 * desc
 */
public class WebSocketHolder {


    private final static ConcurrentHashMap<String,

            WebSocketEntry

            > webSocketSessions = new ConcurrentHashMap<>();

    public int getLiveNum(String identity) {
        var temp = getIdentityMapSessions(identity);
        return temp != null ? temp.size() : 0
                ;
    }

    public int getTotalClinetNum() {
        return webSocketSessions.size();
    }

    public WebSocketEntry putWebSocketSession(String identity,

                                              WebSocketSession session

    ) {

        List<WebSocketSession> results = getIdentityMapSessions(identity);
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
            WebSocketEntry webSocketEntry = new WebSocketEntry();
            webSocketEntry.setWebSocketSessions(results);
            webSocketSessions.put(identity, webSocketEntry);
        }
        results.add(session);
        return webSocketSessions.get(identity);

    }

    public Collection<WebSocketEntry> currentWebSocketEntries() {
        return webSocketSessions.values();
    }

    public WebSocketEntry removeWebSession(String identity, WebSocketSession session) throws Exception {

        List<WebSocketSession> existSessions = getIdentityMapSessions(identity);
        WebSocketEntry webSocketEntry = webSocketSessions.get(identity);
        for (int i = 0; i < existSessions.size(); i++) {

            if (existSessions.get(i).getId().equals(session.getId())) {

                existSessions.get(i).close();
                existSessions.remove(i);
                break;
            }

        }
        if (existSessions.size() == 0) {
            webSocketSessions.remove(identity);
        }
        return webSocketEntry;

    }

    public List<WebSocketSession> getIdentityMapSessions(String identity) {
        return webSocketSessions.get(identity) != null ? webSocketSessions.get(identity).
                getWebSocketSessions() : null;
    }

    public void doSomethingAboutSessions(String identity, Consumer<WebSocketSession> doing) {

        List<WebSocketSession> sessions = getIdentityMapSessions(identity);
        if (sessions != null) {
            sessions.forEach(doing);
        }
    }

    public Collection<WebSocketEntry> webSocketEntries() {
        return webSocketSessions.values();

    }
}
