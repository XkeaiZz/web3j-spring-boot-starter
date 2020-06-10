package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Slf4j
public class ContractScannerConfigurer implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, BeanNameAware, BeanFactoryPostProcessor, EnvironmentAware {
    private ApplicationContext applicationContext;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.props = Utils.getPropsFromEnv(environment, "web3j");
        this.scanPackage = this.props.getProperty("scan-package");
    }

    private Properties props;
    private Environment environment;
    private String beanName;
    private String scanPackage;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (this.scanPackage == null) {
            if (log.isDebugEnabled()) {
                log.warn("did not config scan-package, scanner process is ignored");
            }
            return;
        }
        ClassPathContractScanner scanner = new ClassPathContractScanner(registry, false);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setProps(props);
        scanner.registerFilters();
        scanner.setApplicationContext(this.applicationContext);
        scanner.scan(this.scanPackage);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
