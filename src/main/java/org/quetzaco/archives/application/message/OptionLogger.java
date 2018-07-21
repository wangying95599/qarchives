package org.quetzaco.archives.application.message;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉
public @interface OptionLogger {

  OpType type()  default OpType.SEARCH ;

  public String objectType() default "";

  public String description() default "";

  public enum OpType {
    ADD("新增"), UPDATE("更新"), DEL("删除"),
    SEARCH("查询"), DOWNLOAD("下载"), ZIPDOWNLOAD("ZIP下载"),
    DETAIL("查看内容");
    private String name;

    OpType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}