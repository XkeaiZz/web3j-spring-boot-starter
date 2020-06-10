package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Properties;
import java.util.Set;

@Slf4j
public class ClassPathContractScanner extends ClassPathBeanDefinitionScanner implements ApplicationContextAware {
    private ApplicationContext context;
    private Properties props;

    public ClassPathContractScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public void registerFilters() {
//        addIncludeFilter(new AssignableTypeFilter(Contract.class));
        addIncludeFilter(new ContainsTypeFilter(this.props.stringPropertyNames(), "org.web3j.tx.Contract"));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
