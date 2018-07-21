package org.quetzaco.archives.application.dao.sync.oa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.quetzaco.archives.model.OaCodesheet;
import org.quetzaco.archives.model.OaDepartment;
import org.quetzaco.archives.model.OaPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description Created by dong on 2017/8/18.
 */
@Repository
//@Aspect
public class OAConnectionHelper {

  final static Logger logger = LoggerFactory.getLogger(OAConnectionHelper.class);
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  @Qualifier("oaDataSource")
  DataSource oaDataSource;

  @PostConstruct
  public void init(){
    jdbcTemplate.setDataSource(oaDataSource);
  }
/*  @Before("execution(* org.quetzaco.archives.application.dao.sync.oa.OAConnectionHelper.get*(..))")
  public void proccess(JoinPoint joinPoint) {
    jdbcTemplate.setDataSource(oaDataSource);
  }*/

  public List<OaDepartment> getAllOaOrganizationUnits(String parentOrgId ,OASyncRunner<OaDepartment> runner) {
    return jdbcTemplate.<OaDepartment>query(
        "select * from DEPARTMENT  where REGEXP_SUBSTR(ORGSEQ,?) !=-1",
        new Object[]{parentOrgId},(resultSet,i) -> {
          OaDepartment org = new OaDepartment();
          org.setOrgid(resultSet.getLong("ORGID"));
          org.setOrglevel(resultSet.getLong("ORGLEVEL"));
          org.setOrgname(resultSet.getString("ORGNAME"));
          org.setParentorgid(resultSet.getLong("PARENTORGID"));
          org.setOrgseq(resultSet.getString("ORGSEQ"));
          org.setOrgno(resultSet.getString("ORGNO"));
          runner.run(org);
          return org;
        });

  }

  public List<OaPost> getAllOaUsers(OASyncRunner<OaPost> runner) {
    return jdbcTemplate.<OaPost>query(
        "SELECT t.* FROM DCDB_PERINFOENTER.POST t ", (resultSet,i) -> {
            OaPost oaPost = new OaPost();
            oaPost.setOperatorid(resultSet.getLong("OPERATORID"));
            oaPost.setUserid(resultSet.getString("USERID"));
            oaPost.setOrgid(resultSet.getLong("ORGID"));
            oaPost.setOperatorname(resultSet.getString("OPERATORNAME"));
            oaPost.setOemail(resultSet.getString("OEMAIL"));
            oaPost.setMobileno(resultSet.getString("MOBILENO"));
            oaPost.setIsopen(resultSet.getString("ISOPEN"));
            oaPost.setGender(resultSet.getInt("GENDER"));
            oaPost.setEmpstatus(resultSet.getString("EMPSTATUS"));
            oaPost.setDuty(resultSet.getString("DUTY"));
            runner.run(oaPost);
            return oaPost;
        });
  }

  public List<OaCodesheet> getAllOaPassword(OASyncRunner<OaCodesheet> runner) {
    return jdbcTemplate.<OaCodesheet>query(
        "SELECT t.* FROM DCDB_PERINFOENTER.CODESHEET t",(resultSet,i) -> {
            OaCodesheet oaCodesheet = new OaCodesheet();
            oaCodesheet.setOperatorid(resultSet.getLong("OPERATORID"));
            oaCodesheet.setUserid(resultSet.getString("USERID"));
            oaCodesheet.setOperatorname(resultSet.getString("OPERATORNAME"));
            oaCodesheet.setPassword(resultSet.getString("PASSWORD"));
            oaCodesheet.setFlag(resultSet.getString("FLAG"));
            runner.run(oaCodesheet);
            return oaCodesheet;
        });
  }


  public interface OASyncRunner<T>{
    public int run(T t);
  }

}
