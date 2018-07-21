package org.quetzaco.archives.web.restful;

import org.quetzaco.archives.application.biz.SwiftService;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwiftController extends BaseRestContoller {

  @Autowired
  SwiftService swiftService;

  @RequestMapping(value = "/fileNumber", method = RequestMethod.GET)
  public HttpEntity<APIEntity<String>> getSwiftNumber(@RequestParam("prefix") String prefix,
      @RequestParam("fileNumberType") String fileNumberType) {
    return buildEntity(APIEntity.create(swiftService.getSwiftNumber(prefix, fileNumberType)),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/saveFileNumber", method = RequestMethod.GET)
  public HttpEntity<APIEntity<?>> saveSwiftNumber(@RequestParam("prefix") String prefix) {
    swiftService.saveSwiftNumber(prefix);
    return buildEntity(APIEntity.create(null), HttpStatus.OK);
  }


}

