package org.quetzaco.archives.web.restful;

import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description Created by dong on 2017/8/29.
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseRestContoller{

  @Autowired
  ArchiveProperties archiveProperties;

  @RequestMapping
  public HttpEntity<APIEntity<?>> getAllConfig(){
    return buildEntity(APIEntity.create(archiveProperties.getProperties().values()), HttpStatus.OK);
  }

  @RequestMapping("/checkoaconfigenable")
  public HttpEntity<APIEntity<?>> getOAConfig(){
    return buildEntity(APIEntity.create(archiveProperties.getPasswordValiditor()), HttpStatus.OK);
  }
}
