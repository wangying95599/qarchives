package org.quetzaco.archives.application.biz;


public interface SwiftService {

  String getSwiftNumber(String prefix, String fileNumberType);

    int saveSwiftNumber(String prefix);

}
