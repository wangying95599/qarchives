package org.quetzaco.archives.util.boot;

public enum RoleType {
    MANAGER(1l,"档案管理员"),ARRANGE(2l,"档案整理员"),DEPT_MANAGER(3l,"部门档案管理员"),
    DEPT_ARRANGE(4l,"部门档案整理员"),UPLOAD(5l,"上传人员"),EVERYMAN(7l,"普通用户");
    RoleType(Long type,String desc){
        this.type= type;
        this.desc = desc;
    }
    private Long type;
    private String desc;
    public Long getType(){
        return type;
    }
    public String getDesc(){
        return desc;
    }
}
