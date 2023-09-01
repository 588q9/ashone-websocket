package top.ashonecoder.ashonewebsocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ashonecoder.ashonewebsocket.handler.TextHandler;
import top.ashonecoder.ashonewebsocket.processor.container.ProcessContainer;

/**
 * @author ashone
 * <p>
 * desc
 */
@Configuration
public class WebSocketConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public TextHandler textHandler() {
        return new TextHandler();

    }

    @Bean
    public ProcessContainer processContainer() {

        return new ProcessContainer();
    }


}
