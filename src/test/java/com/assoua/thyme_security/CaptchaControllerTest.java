package com.assoua.thyme_security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CaptchaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCaptchaImage() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/captcha").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(result -> {
                    HttpSession httpSession = result.getRequest().getSession();
                    String captchaText = (String) httpSession.getAttribute("captcha");
//                    assertNotNull(captchaText);
//                    assertEquals(6, captchaText.length());
                });
    }
}
