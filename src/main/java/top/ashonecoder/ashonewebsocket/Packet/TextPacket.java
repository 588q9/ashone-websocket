package top.ashonecoder.ashonewebsocket.Packet;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author ashone
 * <p>
 * desc
 */
@Data
@ToString
@Builder
public class TextPacket {

    private String identity;
    private int sign;
    private String data;

    private String uri;

}
