package org.quetzaco.archives.util.config;

import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Description Created by dong on 2017/7/20.
 */
@Configuration
public class ArchiveProperties implements EnvironmentAware {

  public static final String ARCHIVEPROPERTIES_PREFIX = "quetzaco.archive.config";
  private RelaxedPropertyResolver propertyResolver;
  private Properties properties = new Properties();

  /**
   * Set the {@code Environment} that this component runs in.
   */
  @Override
  public void setEnvironment(Environment environment) {
    this.propertyResolver = new RelaxedPropertyResolver(environment,
        ARCHIVEPROPERTIES_PREFIX + ".");
  }

  public Properties getProperties() {
    return properties;
  }

  public String getFileStorage() {
    return properties.getProperty("fileStorage");
  }

  public String getOABaseOrgId() {
    return properties.getProperty("oa.base_org_id");
  }
  public String getPasswordValiditor() {
    return properties.getProperty("passwordValiditor");
  }

  @PostConstruct
  public void init() {
    properties.setProperty("fileStorage", propertyResolver.getProperty("file-storage"));
    properties.setProperty("oa.base_org_id", propertyResolver.getProperty("oa.base_org_id"));
    properties.setProperty("passwordValiditor", propertyResolver.getProperty("password_validitor"));
  }
}
