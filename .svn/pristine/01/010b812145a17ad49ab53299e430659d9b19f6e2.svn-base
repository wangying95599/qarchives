package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quetzaco.archives.application.biz.HistoryDocumentService;
import org.quetzaco.archives.application.dao.HistoryDocumentMapper;
import org.quetzaco.archives.model.HistoryDocument;
import org.quetzaco.archives.model.HistoryDocumentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryDocumentServiceImpl implements HistoryDocumentService {

  @Autowired
  HistoryDocumentMapper historyDocumentMapper;

  @Override
  public HistoryDocument insertHistoryDocument(HistoryDocument historyDocument) {
    historyDocumentMapper.insert(historyDocument);
    return historyDocument;
  }


  @Override
  public List<HistoryDocument> selectAll() {
    HistoryDocumentExample example = new HistoryDocumentExample();
    return historyDocumentMapper.selectByExample(example);
  }

  @Override
  public PageInfo selectWSDocument(String tm, String dh, int offset, int limit) {
    PageHelper.startPage(offset, limit);
    HistoryDocumentExample example = new HistoryDocumentExample();
    if (tm == null & dh == null) {
      example.createCriteria().andLbbhEqualTo("文书档案").andDhIsNotNull();
    } else {
      example.createCriteria().andLbbhEqualTo("文书档案").andDhIsNotNull().andTmLike("%" + tm + "%")
          .andDhLike("%" + dh + "%");
    }
    List<HistoryDocument> list = historyDocumentMapper.selectByExampleWenShu(example);
    //用PageInfo对结果进行包装
    PageInfo page = new PageInfo(list);
    return page;
  }

  @Override
  public PageInfo selectWGDDocument(String tm, String dh, int offset, int limit) {
    PageHelper.startPage(offset, limit);
    HistoryDocumentExample example = new HistoryDocumentExample();
    if (tm == null & dh == null) {
      example.createCriteria().andDhIsNull();
    } else {
      example.createCriteria().andDhIsNull().andTmLike("%" + tm + "%").andDhLike("%" + dh + "%");
    }
    List<HistoryDocument> list = historyDocumentMapper.selectByExampleWeiGuiDang(example);
    //用PageInfo对结果进行包装
    PageInfo page = new PageInfo(list);
    return page;
  }

  @Override
  public PageInfo selectSXDocument(String tm, String dh, int offset, int limit) {
    PageHelper.startPage(offset, limit);
    HistoryDocumentExample example = new HistoryDocumentExample();
    if (tm == null & dh == null) {
      example.createCriteria().andLbbhEqualTo("声像档案").andDhIsNotNull();
    } else {
      example.createCriteria().andLbbhEqualTo("声像档案").andDhIsNotNull().andTmLike("%" + tm + "%")
          .andDhLike("%" + dh + "%");
    }
    List<HistoryDocument> list = historyDocumentMapper.selectByExampleShengXiang(example);
    //用PageInfo对结果进行包装
    PageInfo page = new PageInfo(list);
    return page;
  }

  @Override
  public PageInfo selectSWDocument(String tm, String dh, int offset, int limit) {
    PageHelper.startPage(offset, limit);
    HistoryDocumentExample example = new HistoryDocumentExample();
    if (tm == null & dh == null) {
      example.createCriteria().andLbbhEqualTo("实物档案").andDhIsNotNull();
    } else {
      example.createCriteria().andLbbhEqualTo("实物档案").andDhIsNotNull().andSwsmLike("%" + tm + "%")
          .andDhLike("%" + dh + "%");
    }
    List<HistoryDocument> list = historyDocumentMapper.selectByExampleShiWu(example);
    //用PageInfo对结果进行包装
    PageInfo page = new PageInfo(list);
    return page;
  }

  @Override
  public List<Map> selectTMDocument(String tmid) {
    HistoryDocumentExample example = new HistoryDocumentExample();
    example.createCriteria().andIdEqualTo(tmid);
    HistoryDocument list = historyDocumentMapper.selectByExampleTiaoMu(example);
    Map<String, String> map = new HashMap<String, String>();
    map.put("全宗号", list.getQzh());
    map.put("档号", list.getDh());
    map.put("责任者", list.getZrz());
    map.put("保管期限", list.getBgqx());
    map.put("密级", list.getMj());
    map.put("主题词", list.getZtc());
    map.put("备注", list.getBz());
    map.put("年度", list.getNd());
    map.put("件号", list.getJh());
    map.put("页数", list.getYs());
    map.put("文号", list.getWh());
    map.put("题名", list.getTm());
    map.put("实体份数", list.getStfs());
    map.put("档案类别", list.getLbbh());
    map.put("拟稿部门", list.getNgbm());
    map.put("文件形成时间", list.getWjxcsj());
    map.put("实体分类号", list.getStflh());
    map.put("归档日期", list.getGdrq());
    map.put("所属部门", list.getSsbm());
    map.put("文件属性", list.getWjsx());
    map.put("录入人", list.getLrr());
    map.put("文件类型", list.getWjlx());
    map.put("移交人", list.getYjr());
    map.put("登记号", list.getDjh());
    map.put("条目的电子文件数量", list.getSysFileCount());

    List tm = new ArrayList();

    for (String key : map.keySet()) {
      Map model = new HashMap();
      model.put("type", key);
      model.put("content", map.get(key));
      tm.add(model);
    }
    return tm;
  }


}
