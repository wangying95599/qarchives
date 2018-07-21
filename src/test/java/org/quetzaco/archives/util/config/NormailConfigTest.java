package org.quetzaco.archives.util.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.quetzaco.archives.util.encoder.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class NormailConfigTest extends QarchivesApplicationTests {
    @Autowired
    PasswordEncoder passwordEncoder;
    final static Logger LOGGER = LoggerFactory.getLogger(NormailConfigTest.class);

    @Test
    public void passwordEncoder() throws Exception {
      assertEquals(true ,passwordEncoder.isPasswordValid(passwordEncoder.encode("111111"),"111111"));
    }

    @Test
    public void encode(){
        LOGGER.debug(passwordEncoder.encode("111111"));
    }

}