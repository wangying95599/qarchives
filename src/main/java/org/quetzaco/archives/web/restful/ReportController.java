package org.quetzaco.archives.web.restful;

import java.util.List;
import java.util.Map;

import org.quetzaco.archives.application.biz.ReportService;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by deya on 2017/7/11.
 */
@RestController
public class ReportController extends BaseRestContoller {
	@Autowired
  	ReportService reportService;


	@RequestMapping(value = "/report/year/juan/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForYearJuan(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForYearJuan(deptId, startYear, endYear)), HttpStatus.OK);
    }

	@RequestMapping(value = "/report/year/jian/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForYearJian(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForYearJian(deptId, startYear, endYear)), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/report/duration/juan/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForDurationJuan(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForDurationJuan(deptId, startYear, endYear)), HttpStatus.OK);
    }

	@RequestMapping(value = "/report/duration/jian/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForDurationJian(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForDurationJian(deptId, startYear, endYear)), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/report/use/juan/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForUseJuan(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForUseJuan(deptId, startYear, endYear)), HttpStatus.OK);
    }

	@RequestMapping(value = "/report/use/jian/{deptId}/{startYear}/{endYear}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> reportForUseJian(@PathVariable("deptId") Long deptId,
    		@PathVariable("startYear") String startYear,@PathVariable("endYear") String endYear) {
        return buildEntity(APIEntity.create(reportService.reportForUseJian(deptId, startYear, endYear)), HttpStatus.OK);
    }
}
