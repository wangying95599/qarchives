package org.quetzaco.archives.util.config;

import org.junit.Assert;
import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * @Description Created by dong on 2017/7/20.
 */
public class ArchivePropertiesTest extends QarchivesApplicationTests {
    final static Logger logger = LoggerFactory.getLogger(ArchivePropertiesTest.class);
    @Autowired
    ArchiveProperties archiveProperties;
    @Test
    public void getFileStorage() throws Exception {
        assertEquals("I:/docs",archiveProperties.getFileStorage());
    }

}