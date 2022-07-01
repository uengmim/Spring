package me.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HolomanProperties.class)
public class SpringbootConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Springboot springboot(SpringbootProperties properties) {
        Springboot springboot = new Springboot();
        springboot.setHowLong(properties.getHowLong());
        springboot.setName(properties.getName());
        return springboot;
    }
}
