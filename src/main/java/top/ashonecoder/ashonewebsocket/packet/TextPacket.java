package top.ashonecoder.ashonewebsocket.packet;

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

    public static TextPacket message(String identity,String message) {


       return TextPacket.builder().identity(identity).data(message).build();

    }

}
