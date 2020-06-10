package com.github.jntm.web3jspringbootstarter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

@Slf4j
@Configuration
@ConditionalOnBean({Web3j.class,Credentials.class,ContractGasProvider.class})
public class Web3jContractAutoInjectionConfiguration {
    @Configuration
    @ConditionalOnMissingBean(ContractScannerConfigurer.class)
    @Import(Web3jContractAutoInjectionConfig.class)
    public static class ContractScannerRegistrarNotFoundConfiguration {

    }
    public static class Web3jContractAutoInjectionConfig implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ContractScannerConfigurer.class);
            registry.registerBeanDefinition(ContractScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }


    }

    @Slf4j
    @Configuration
    public static class Web3jContractInstanceBeanPostProcess implements InstantiationAwareBeanPostProcessor, EnvironmentAware {
        private Environment environment;
        private Properties props;
        private Web3j web3j;
        private Credentials credentials;
        private ContractGasProvider contractGasProvider;

        public Web3jContractInstanceBeanPostProcess(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
            this.web3j = web3j;
            this.credentials = credentials;
            this.contractGasProvider = contractGasProvider;
        }

        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            Class<?> superclass = beanClass.getSuperclass();
            if (superclass == null)
                return null;
            if (Contract.class.isAssignableFrom(superclass)) {
                if (props.containsKey(beanClass.getSimpleName())) {
                    return processContract(beanClass, props.getProperty(beanClass.getSimpleName()));
                } else {
                    log.warn(String.format("found class %s but did not find config related with it", beanClass.getSimpleName()));
                }
            }

            return null;
        }

        private Object processContract(Class<?> beanClass, String act) {
            Object o = null;
            if (act.equals("new")) {
                try {
                    Method deploy = beanClass.getMethod("deploy", Web3j.class, Credentials.class, ContractGasProvider.class);
                    o = ((RemoteCall) deploy.invoke(null, web3j, credentials, contractGasProvider)).send();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    Method load = beanClass.getMethod("load", String.class, Web3j.class, Credentials.class, ContractGasProvider.class);
                    o = load.invoke(null, act, web3j, credentials, contractGasProvider);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return o;
            }
            return o;
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            return false;
        }

        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
            return null;
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
            this.props = Utils.getPropsFromEnv(environment, "web3j.contract");
        }
    }


}
