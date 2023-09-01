package top.ashonecoder.ashonewebsocket.processor.container;


import top.ashonecoder.ashonewebsocket.annotation.Processing;
import top.ashonecoder.ashonewebsocket.processor.Processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ProcessContainer {


    private final Map<String, Processor> processorMap = new ConcurrentHashMap<>();


    public Processor getProcessor(String processingType) {
        return processorMap.get(processingType);


    }


    public void addProcessor(Class<? extends Processor> handlerClass, Processor processor) {
        Processing processing = handlerClass.getAnnotation(Processing.class);
        this.processorMap.put(processing.value(), processor);
    }


}

