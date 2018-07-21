package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.ReportService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.model.Role;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.boot.ArchiveType;
import org.quetzaco.archives.util.boot.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by deya on 2017/7/11.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier("archivesJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    protected static Map<String, String> archiveTypeMap_jian = new HashMap<String, String>();
    protected static Map<String, String> archiveTypeMap_juan = new HashMap<String, String>();

    static {
        //documents
        for (ArchiveType.DocumentType dt : ArchiveType.DocumentType.values()) {
            archiveTypeMap_jian.put(dt.getAlias(), dt.getValue());
        }
          /*archiveTypeMap_jian.put("ALL","全部");
	      archiveTypeMap_jian.put("WS","文书档案");
	      archiveTypeMap_jian.put("KJ","会计档案");
	      archiveTypeMap_jian.put("RS","人事档案");
	      archiveTypeMap_jian.put("SW","实物档案");
	      archiveTypeMap_jian.put("HT","合同档案");
	      archiveTypeMap_jian.put("KW","刊物档案");
	      archiveTypeMap_jian.put("YX","印信档案");
	      archiveTypeMap_jian.put("FC","房产档案");
	      archiveTypeMap_jian.put("ZJ","证件档案");
	      archiveTypeMap_jian.put("DJ","单机档案");*/

        for (ArchiveType.RecordType rt : ArchiveType.RecordType.values()) {
            archiveTypeMap_juan.put(rt.getAlias(), rt.getValue());
        }

        //record
	      /*archiveTypeMap_juan.put("ALL","全部");
	      archiveTypeMap_juan.put("JJ","基建档案");
	      archiveTypeMap_juan.put("SX","声像档案");
	      archiveTypeMap_juan.put("SJJC","审计监察档案");
	      archiveTypeMap_juan.put("AJ","案件档案");
	      archiveTypeMap_juan.put("SG","生产安全事故档案");
	      archiveTypeMap_juan.put("XX","信息化建设档案");
	      archiveTypeMap_juan.put("ZTB","招投标档案");*/
    }

    @Override
    public List<Map> reportForYearJian(Long deptId, String start, String end) {
        return reportForYear(deptId, start, end, "documents");
    }

    @Override
    public List<Map> reportForYearJuan(Long deptId, String start, String end) {
        return reportForYear(deptId, start, end, "records");
    }

    public List<Map> reportForYear(Long deptId, String start, String end, String tableName) {

        String cntSql = "select archive_year,count(archive_type) as cnt from " + tableName + " where "
                + " archive_type is not null and archive_year is not null ";

        cntSql = getString(cntSql);
		  /*if(deptId != -1) {
			  cntSql +=" and dept_id="+deptId;
		  }*/
        if (!"_".equals(start)) {
            cntSql += " and archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            cntSql += " and archive_year<='" + end + "'";
        }
        cntSql += " group by archive_year";
        System.out.println("reportForYear         " + cntSql);

        Map<String, String> cntMap = new HashMap<String, String>();

        jdbcTemplate.query(cntSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String year = rs.getString("archive_year");

                int cnt = rs.getInt("cnt");

                cntMap.put(year, cnt + "");

                return null;
            }
        });


        String totalSql = "select archive_year,archive_type,count(archive_type) as cnt from " + tableName + " where "
                + " archive_type is not null and archive_year is not null ";
	  /*if(deptId != -1) {
		  totalSql +=" and dept_id="+deptId;
	  }*/
        totalSql = getString(totalSql);

        if (!"_".equals(start)) {
            totalSql += " and archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            totalSql += " and archive_year<='" + end + "'";
        }
        totalSql += " group by archive_year,archive_type";
        System.out.println("reportForYear         " + totalSql);

        Map<String, Map> yearMap = new TreeMap<String, Map>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj1.compareTo(obj2);
            }
        }
        );

        jdbcTemplate.query(totalSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String year = rs.getString("archive_year");
                String type = rs.getString("archive_type");
//				type = archiveTypeMap_juan.get(type);
                int cnt = rs.getInt("cnt");

                Map<String, String> typeMap = yearMap.get(year);
                if (typeMap == null) {
                    typeMap = new HashMap<String, String>();
                    yearMap.put(year, typeMap);
                }
                typeMap.put(type, cnt + "");

                return null;
            }
        });
        List<Map> list = new ArrayList<Map>();
        Set<String> set = yearMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Map map = yearMap.get(key);
            map.put("year", key);
            map.put("cnt", cntMap.get(key));
            list.add(map);
        }
        System.out.println(list);

        return list;
    }

    public String getString(String cntSql) {
        User contextUser = userService.getContextUser();
        Role role = contextUser.getRole();
        if (RoleType.DEPT_MANAGER.getType().equals(role.getId()) || RoleType.DEPT_ARRANGE.getType().equals(role.getId())) {
            cntSql += " and dept_id= " + contextUser.getDept().getId();
        }
        return cntSql;
    }


    @Override
    public List<Map> reportForDurationJian(Long deptId, String start, String end) {
        return reportForDuration(deptId, start, end, "documents");
    }

    @Override
    public List<Map> reportForDurationJuan(Long deptId, String start, String end) {
        return reportForDuration(deptId, start, end, "records");
    }

    public List<Map> reportForDuration(Long deptId, String start, String end, String tableName) {
        String cntSql = "select archive_type,count(reserve_duration) as cnt from " + tableName + " where "
                + " archive_type is not null and reserve_duration is not null ";
		  /*if(deptId != -1) {
			  cntSql +=" and dept_id="+deptId;
		  }*/
        cntSql = getString(cntSql);
        if (!"_".equals(start)) {
            cntSql += " and archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            cntSql += " and archive_year<='" + end + "'";
        }
        cntSql += " group by archive_type ";
        System.out.println("cnt         " + cntSql);

        Map<String, String> cntMap = new HashMap<String, String>();
        jdbcTemplate.query(cntSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String type = rs.getString("archive_type");
                int cnt = rs.getInt("cnt");

                cntMap.put(type, cnt + "");

                return null;
            }
        });


        String totalSql = "select archive_type,reserve_duration,count(reserve_duration) as cnt from " + tableName + " where "
                + " archive_type is not null and reserve_duration is not null ";
        /*if (deptId != -1) {
            totalSql += " and dept_id=" + deptId;
        }*/
        totalSql = getString(totalSql);
        if (!"_".equals(start)) {
            totalSql += " and archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            totalSql += " and archive_year<='" + end + "'";
        }
        totalSql += " group by archive_type,reserve_duration ";
        System.out.println("reportForDuration         " + totalSql);


        Map<String, Map> typeMap = new TreeMap<String, Map>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj1.compareTo(obj2);
            }
        }
        );

        jdbcTemplate.query(totalSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String type = rs.getString("archive_type");
                String duration = rs.getString("reserve_duration");

                int cnt = rs.getInt("cnt");

                Map<String, String> durationMap = typeMap.get(type);
                if (durationMap == null) {
                    durationMap = new HashMap<String, String>();
                    typeMap.put(type, durationMap);
                }
                durationMap.put(duration, cnt + "");

                return null;
            }
        });
        List<Map> list = new ArrayList<Map>();
        Set<String> set = typeMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Map map = typeMap.get(key);
            if ("records".equals(tableName)) {
                map.put("type", archiveTypeMap_juan.get(key) == null ? key : archiveTypeMap_juan.get(key));
            } else {
                map.put("type", archiveTypeMap_jian.get(key) == null ? key : archiveTypeMap_jian.get(key));
            }
            map.put("cnt", cntMap.get(key));
            list.add(map);
        }
        System.out.println(list);

        return list;
    }


    @Override
    public List<Map> reportForUseJian(Long deptId, String start, String end) {
        // return reportForUse( deptId, start, end,"documents");
        return reportForUse_1(deptId, start, end, "documents");
    }

    @Override
    public List<Map> reportForUseJuan(Long deptId, String start, String end) {
        // return reportForUse( deptId, start, end,"records");
        return reportForUse_1(deptId, start, end, "records");
    }

    public List<Map> reportForUse(Long deptId, String start, String end, String tableName) {

        String totalSql = "select archive_type,count(archive_type) as cnt from " + tableName + " where "
                + " archive_type is not null  ";

        if (!"_".equals(start)) {
            totalSql += " and archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            totalSql += " and archive_year<='" + end + "'";
        }
        totalSql += " group by archive_type ";
        System.out.println("reportForDuration         " + totalSql);

        List<Map> list = new ArrayList<Map>();
        jdbcTemplate.query(totalSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String type = rs.getString("archive_type");

                int cnt = rs.getInt("cnt");

                Map<String, String> typeMap = new TreeMap<String, String>();
                typeMap.put("cnt", cnt + "");
                if ("records".equals(tableName)) {
                    typeMap.put("type", archiveTypeMap_juan.get(type) == null ? type : archiveTypeMap_juan.get(type));
                } else {
                    typeMap.put("type", archiveTypeMap_jian.get(type) == null ? type : archiveTypeMap_jian.get(type));
                }
                typeMap.put(type, cnt + "");
                list.add(typeMap);

                return null;
            }
        });

        System.out.println(list);

        return list;
    }

    public List<Map> reportForUse_1(Long deptId, String start, String end, String tableName) {
        String fileType = "";
        if ("documents".equals(tableName)) {
            fileType = "documents";
        } else if ("records".equals(tableName)) {
            fileType = "record";
        }

        String cntSql = "SELECT c.archive_type,count(1) as cnt FROM flow_form_lending a INNER JOIN link_flow_doc b" +
                " ON a.flow_id=b.flow_id AND b.file_type='" + fileType + "' JOIN " + tableName + "  c ON b.doc_id=c.id AND c.archive_type NOTNULL ";

        cntSql = getString(cntSql);
        if (!"_".equals(start)) {
            cntSql += " and c.archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            cntSql += " and c.archive_year<='" + end + "'";
        }
        cntSql += " group by c.archive_type ";
        Map<String, String> cntMap = new HashMap<String, String>();

        jdbcTemplate.query(cntSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String type = rs.getString("archive_type");

                int cnt = rs.getInt("cnt");

                cntMap.put(type, cnt + "");

                return null;
            }
        });

        String totalSql = "SELECT a.lending_permission,c.archive_type,count(1) as cnt FROM flow_form_lending a INNER JOIN link_flow_doc b" +
                " ON a.flow_id=b.flow_id AND b.file_type='" + fileType + "' JOIN " + tableName + "  c ON b.doc_id=c.id AND c.archive_type NOTNULL ";
        totalSql = getString(totalSql);
        if (!"_".equals(start)) {
            totalSql += " and c.archive_year>= '" + start + "'";
        }
        if (!"_".equals(end)) {
            totalSql += " and c.archive_year<='" + end + "'";
        }
        totalSql += " group by c.archive_type,a.lending_permission order  by c.archive_type ";
        System.out.println("reportForUse_1         " + totalSql);

        Map<String, Map> typeMap = new TreeMap<String, Map>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj1.compareTo(obj2);
            }
        }
        );

        jdbcTemplate.query(totalSql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String permission = rs.getString("lending_permission");
                String type = rs.getString("archive_type");
//				type = archiveTypeMap_juan.get(type);
                int cnt = rs.getInt("cnt");

                Map<String, String> permissionMap = typeMap.get(type);
                if (permissionMap == null) {
                    permissionMap = new HashMap<String, String>();
                    typeMap.put(type, permissionMap);
                }
                permissionMap.put(permission, cnt + "");

                return null;
            }
        });

        List<Map> list = new ArrayList<Map>();
        Set<String> set = typeMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Map map = typeMap.get(key);
            if ("records".equals(tableName)) {
                map.put("type", archiveTypeMap_juan.get(key) == null ? key : archiveTypeMap_juan.get(key));
            } else {
                map.put("type", archiveTypeMap_jian.get(key) == null ? key : archiveTypeMap_jian.get(key));
            }
            map.put("cnt", cntMap.get(key));
            list.add(map);
        }

        System.out.println(list);
        return list;
    }


}
