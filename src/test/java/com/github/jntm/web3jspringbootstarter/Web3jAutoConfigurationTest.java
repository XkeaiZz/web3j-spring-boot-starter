package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Web3jAutoConfiguration.class, Web3jContractAutoInjectionConfiguration.class})
@ActiveProfiles("test")
class Web3jAutoConfigurationTest extends AbstractJUnit4SpringContextTests {
    public static ConfigurableApplicationContext configurableApplicationContext;

    @BeforeEach
    void beforeEach() {
        configurableApplicationContext = (ConfigurableApplicationContext) this.applicationContext;
    }

    @AfterEach
    void afterEach() {
        if (configurableApplicationContext != null)
            configurableApplicationContext = null;
    }

    @Test
    void web3j() {
//        assertThat(configurableApplicationContext.getBean("org.web3j.protocol.Web3j")).isNotNull();
    }

    @Test
    void admin() {

//        assertThat(configurableApplicationContext.getBean("org.web3j.protocol.admin.Admin")).isNotNull();
    }

    @Test
    void normal() throws Exception {
//        Web3j web3j = (Web3j) applicationContext.getBean("org.web3j.protocol.Web3j");
//        assertThat(web3j).isNotNull();
//        Object o = applicationContext.getBean("registerAndManager");
//        if (o instanceof RemoteCall) {
//            RemoteCall<RegisterAndManager> callable = (RemoteCall<RegisterAndManager>) o;
//            RegisterAndManager registerAndManager = callable.send();
//        }
    }
}