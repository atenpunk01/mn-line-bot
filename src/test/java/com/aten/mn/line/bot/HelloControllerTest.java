package com.aten.mn.line.bot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @Test
    public void hello_withoutName_mustDefaultWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(model().attribute("name", Matchers.equalTo("World")));
    }
 
    @Test
    public void hello_withName_mustEqualsParameter() throws Exception {
        String name = "Atenpunk";
 
        mockMvc.perform(MockMvcRequestBuilders.get("/hello?name=" + name).accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", Matchers.equalTo(name)));
 
    }
}