package top.ashonecoder.ashonewebsocket.processor;

import java.lang.reflect.Method;

/**
 * @author ashone
 * <p>
 * desc
 */
public interface Processor {
    Method getPrrocessing(String resourceIdentity);

}
