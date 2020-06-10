package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;


@Slf4j
@Configuration
@ConditionalOnClass(Web3j.class)
@EnableConfigurationProperties(Web3jProperties.class)
public class Web3jAutoConfiguration {

    private Web3jProperties properties;

    public Web3jAutoConfiguration(Web3jProperties properties) {
        this.properties = properties;
    }

//    public static class Web3jContractAutoInjectionConfiguration implements ImportBeanDefinitionRegistrar {
//        @Override
//        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ContractScannerConfigurer.class);
//            registry.registerBeanDefinition(ContractScannerConfigurer.class.getName(), builder.getBeanDefinition());
//        }
//
//
//    }

//    @Configuration
//    @ConditionalOnMissingBean(ContractScannerConfigurer.class)
//    @Import(Web3jContractAutoInjectionConfiguration.class)
//    public static class ContractScannerRegistrarNotFoundConfiguration {
//
//    }

    @Bean(name = "org.web3j.crypto.Credentials")
    @ConditionalOnProperty(prefix = Web3jProperties.WEB3J_PREFIX, name = "secrete")
    public Credentials credentials() {
        return Credentials.create(properties.getSecrete());
    }

    @Bean(name = "xyz.utools.DefaultGasProvider")
    @ConditionalOnProperty(prefix = Web3jProperties.WEB3J_PREFIX, name = "gas-provider", matchIfMissing = true)
    public ContractGasProvider contractGasProvider() {
        ContractGasProvider o = null;
        String s = properties.getGasProvider();
        if (s == null)
            s = "org.web3j.tx.gas.DefaultGasProvider";
        try {
            o = (ContractGasProvider) Class.forName(s).getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }


    @Bean(name = "org.web3j.protocol.Web3j")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = Web3jProperties.WEB3J_PREFIX, name = "server-address")
    public Web3j web3j() {
        String serverAddress = properties.getServerAddress();
        OkHttpClient okHttpClient = createOkHttpClient();
        if (serverAddress.startsWith("http")) {
            return Web3j.build(new HttpService(serverAddress, okHttpClient));
        }
        if (serverAddress.startsWith("ws")) {
            WebSocketService webSocketService = new WebSocketService(serverAddress, false);
            try {
                webSocketService.connect();
            } catch (ConnectException e) {
                log.error(e.getMessage());
            }
            return Web3j.build(webSocketService);
        }
        return null;
    }

    @Bean(name = "org.web3j.protocol.admin.Admin")
    @ConditionalOnProperty(
            prefix = Web3jProperties.WEB3J_PREFIX, name = "admin-client", havingValue = "true")
    public Admin admin() {
        Web3jService web3jService = buildService(properties.getServerAddress());
        log.debug("Building admin service for endpoint: " + properties.getServerAddress());
        return Admin.build(web3jService);
    }

    private Web3jService buildService(String clientAddress) {

        if (clientAddress == null || clientAddress.equals("")) {
            return new HttpService(createOkHttpClient());
        } else if (clientAddress.startsWith("http")) {
//            return new HttpService(clientAddress, createOkHttpClient(), properties.getIncludeRawResponse());
            return new HttpService(clientAddress);
        } else if (clientAddress.startsWith("ws")) {
            WebSocketService web3jService = new WebSocketService(clientAddress, properties.getIncludeRawResponse());
            return web3jService;

        } else if (clientAddress.startsWith("ipc") && System.getProperty("os.name").toLowerCase().startsWith("win")) {
            return new WindowsIpcService(clientAddress);
        } else {
//            log.debug(String.format("only support http(s) ws(s) ipc protocol,the url %s", clientAddress));
            return new UnixIpcService(clientAddress);
        }
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureLogging(builder);
        configureTimeouts(builder);
        return builder.build();
    }

    private void configureTimeouts(OkHttpClient.Builder builder) {
        Long tos = properties.getHttpTimeoutSeconds();
        if (tos != null) {
            builder.connectTimeout(tos, TimeUnit.SECONDS);
            builder.readTimeout(tos, TimeUnit.SECONDS);  // Sets the socket timeout too
            builder.writeTimeout(tos, TimeUnit.SECONDS);
        }
    }

    private static void configureLogging(OkHttpClient.Builder builder) {
        if (log.isDebugEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }
}
