package top.ashonecoder.ashonewebsocket.processor;

import top.ashonecoder.ashonewebsocket.annotation.Processing;
import top.ashonecoder.ashonewebsocket.processor.container.ProcessContainer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ashone
 * <p>
 * desc
 */


public class AbstractProcessor implements Processor {
    private final Map<String, Method> processingMap = new ConcurrentHashMap<>();
    protected ProcessContainer container;


    public AbstractProcessor(ProcessContainer container) {
        this.container = container;
        this.postConstuct();
    }

    public Method getPrrocessing(String resourceIdentity) {
        return processingMap.get(resourceIdentity);

    }

    public void postConstuct() {
        addProcessing(this.getClass());
        container.addProcessor(this.getClass(), this);
    }


    public void addProcessing(Class<? extends Processor> handlerClass) {
        Method[] methods = handlerClass.getMethods();
        Arrays.stream(methods).filter((method) -> {
            Processing[] processing = method.getAnnotationsByType(Processing.class);
            return processing.length > 0;
        }).forEach((method) -> {
            if (processingMap.containsKey(method.getAnnotationsByType(Processing.class)[0].value())) {
                throw new RuntimeException(" 重复的processing注解标注");
            }
            processingMap.put(method.getAnnotationsByType(Processing.class)[0].value(), method);
        });
    }
}
