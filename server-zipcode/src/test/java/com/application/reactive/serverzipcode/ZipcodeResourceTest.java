package com.application.reactive.serverzipcode;

import com.application.reactive.serverzipcode.resource.ZipcodeResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ZipcodeResourceTest {
          private MockMvc mockMvc;
    
          @InjectMocks
          private ZipcodeResource zipcodeResource;

          @Before
          public void setUp() throws Exception {
           mockMvc = MockMvcBuilders.standaloneSetup(zipcodeResource)
                   .build();
    }

        @Test
        public void testZipcode() throws Exception {
                mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/zipcode")
                )
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string("Hello world"));

        }
}