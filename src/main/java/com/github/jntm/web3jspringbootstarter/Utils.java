package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

@Slf4j
public class Utils {
    public static String split(String s, String sep) {
        int i = s.lastIndexOf(sep);
        return s.substring(i + 1);
    }

    public static Properties getPropsFromEnv(Environment environment, String prefix) {
        Properties props = new Properties();
        MutablePropertySources propSrcs = ((AbstractEnvironment) environment).getPropertySources();
        StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof OriginTrackedMapPropertySource)
                .map(ps -> ((OriginTrackedMapPropertySource) ps).getPropertyNames())
                .flatMap(Arrays::<String>stream)
                .forEach(propName -> {
                    if (propName.startsWith(prefix))
                        props.setProperty(Utils.split(propName, "."), environment.getProperty(propName));
                });
        return props;
    }
}
