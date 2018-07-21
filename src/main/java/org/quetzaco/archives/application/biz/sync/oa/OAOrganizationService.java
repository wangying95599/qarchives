package org.quetzaco.archives.application.biz.sync.oa;

/**
 * @Description Created by dong on 2017/8/18.
 */
public interface OAOrganizationService {
  /**
   * 将获取的OA数据
   */
  void getAllOAData();

  /**
   * 清除所有OA数据
   */
  void clearOAData();

/**
   * 将获取的OA数据与档案数据做同步
   */
  public void syncOAData();
}
