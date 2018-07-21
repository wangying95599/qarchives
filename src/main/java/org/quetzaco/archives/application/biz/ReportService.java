package org.quetzaco.archives.application.biz;

import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/11.
 */
public interface ReportService {
	List<Map> reportForYearJuan(Long deptId,String start,String end);
	List<Map> reportForYearJian(Long deptId,String start,String end);
	
	List<Map> reportForDurationJuan(Long deptId,String start,String end);
	List<Map> reportForDurationJian(Long deptId,String start,String end);
	
	List<Map> reportForUseJuan(Long deptId,String start,String end);
	List<Map> reportForUseJian(Long deptId,String start,String end);
}
