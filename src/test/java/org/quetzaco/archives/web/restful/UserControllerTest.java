package org.quetzaco.archives.web.restful;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quetzaco.archives.util.boot.QarchivesApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Description Created by dong on 2017/7/11.
 */
@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration(classes= QarchivesApplication.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @Test
    public void login() throws Exception {
        this.mvc.perform(post("/login")
                .contentType(MediaType.TEXT_PLAIN)
                .param("loginname","admin")
                .param("password","111111")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}