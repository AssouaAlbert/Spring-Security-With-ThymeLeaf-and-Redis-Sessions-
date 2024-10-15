package com.assoua.thyme_security;

import static org.junit.jupiter.api.Assertions.*;

import com.assoua.thyme_security.service.CaptchaService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mockito;

import java.io.IOException;

public class CaptchaServiceTest {

    public final CaptchaService captchaService = new CaptchaService();

    @Test
    public void testGenerateCaptchaTest() {
        String captchaText = captchaService.generateCaptchaText();
        assertNotNull(captchaText);
        assertEquals(6, captchaText.length());
        assertTrue(captchaText.matches("[A-Z0-9]{6}"));
    }

}
