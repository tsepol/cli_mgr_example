package org.tsepol.cli_mgr_ex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.tsepol.cli_mgr_ex.controllers.ClientController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CliMgrExApplicationTests {

    @Autowired
    private ClientController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
