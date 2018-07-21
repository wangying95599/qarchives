package org.quetzaco.archives.application.biz.Impl;

import org.apache.commons.lang3.StringUtils;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.application.dao.*;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.FlowsType;
import org.quetzaco.archives.util.config.PropelProperties;
import org.quetzaco.archives.util.propelToOA.WSTodoOABeanProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

@Service
public class FlowNodesServiceImpl implements FlowNodesService {

    private final Logger LOGGER = LoggerFactory.getLogger(FlowNodesServiceImpl.class);
    @Autowired
    FlowNodesMapper flowNodesMapper;
    @Autowired
    FlowNodeHistoriesMapper flowNodeHistoriesMapper;
    @Autowired
    PropelProperties propelProperties;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FlowFormLendingMapper flowFormLendingMapper;
    @Autowired
    FlowFormDestoryMapper flowFormDestoryMapper;
    @Autowired
    FlowFormDeliverMapper flowFormDeliverMapper;
    @Autowired
    FlowFormAssistMapper flowFormAssistMapper;

    @Override
    public List<FlowNodes> getNodesByFlowAndUser(Long flowId, Long usrId) {
        FlowNodesExample flowNodesExample = new FlowNodesExample();
        FlowNodesExample.Criteria criteria = flowNodesExample.or();
        criteria.andFlowIdEqualTo(flowId).andAssigneeIdEqualTo(usrId);
        return  flowNodesMapper.selectByExample(flowNodesExample);
    }

    @Override
    public List<Map> getFlowStatus(Long flowId) {
        return flowNodeHistoriesMapper.getFlowStatus(flowId);
    }

    @Override
    public int insert(FlowNodes flowNodes) {
        String title = "";
        //推送 至待办
        if (propelProperties.isPropel()) {
            String type = flowNodes.getType();
            if(StringUtils.isEmpty(type)) return 0 ;
            Long flowId = flowNodes.getFlowId();
            FlowsType ft = FlowsType.val(type);
            FlowsParent fp;
            switch (ft){
                case LENDING:
                    fp = flowFormLendingMapper.selectByPrimaryKey(flowId);
                    break;
                case DESTROY:
                    FlowFormDestoryExample fde = new FlowFormDestoryExample();
                    fde.createCriteria().andFlowIdEqualTo(flowId);
                    fp = flowFormDestoryMapper.selectByExample(fde).get(0);
                    break;
                case DELIVER:
                    FlowFormDeliverExample fte = new FlowFormDeliverExample();
                    fte.createCriteria().andFlowIdEqualTo(flowId);
                    fp = flowFormDeliverMapper.selectByExample(fte).get(0);
                    break;
                default:
                    User user = userMapper.selectByPrimaryKey(flowNodes.getAssigneeBy());
                    String name = user.getName();
                    String tt = name +" 给您分配了一个协查流程，请您及时处理";
                    FlowFormAssist fa = new FlowFormAssist();
                    fa.setTitle(tt);
                    /*FlowFormAssistExample fae = new FlowFormAssistExample();
                    fae.createCriteria().andFlowIdEqualTo(flowId);
                    fp = flowFormAssistMapper.selectByExample(fae).get(0);*/
                    fp = fa;
                    break;
            }
            title = fp.getTitle();
        }

        return insert(flowNodes,title);
    }

    @Override
    public int insert(FlowNodes flowNodes, String title) {
        int a = flowNodesMapper.insert(flowNodes);
        System.out.println("-------------------------------------");
        System.out.println(propelProperties.isPropel());

        if(propelProperties.isPropel()){
            Long assigneeId = flowNodes.getAssigneeId();
            ScheduleToOa sdto = new ScheduleToOa();
            sdto.setUsermode(propelProperties.getUsermode());
            sdto.setPassword(propelProperties.getPassword());
            sdto.setBizsystem(propelProperties.getBizsystem());
            sdto.setActionUrl(propelProperties.getActionurl());
            sdto.setRefrenceId(flowNodes.getId().toString());
            sdto.setProcessinstid(flowNodes.getFlowId().toString());
            sdto.setProcessinstname(title);

            User user = userMapper.selectByPrimaryKey(assigneeId);
            String loginName = user.getLoginName();
            sdto.setExecutor(loginName);

            propelToOa(sdto);
        }
        return a;
    }

    /**
     * @param sdto
     */
    //推送至待办
    public void propelToOa(ScheduleToOa sdto) {
        System.out.println("propel sdto  start  ---------------------------------------------");
        LOGGER.debug("propel   sdto:"+sdto);

        try {
            WSTodoOABeanProxy wsTodoOABeanProxy = new WSTodoOABeanProxy();
            int a = wsTodoOABeanProxy.saveTodoData(
                    sdto.getUsermode(),
                    sdto.getPassword(),
                    "",
                    sdto.getRefrenceId(),
                    "",
                    sdto.getProcessinstname(),
                    "",
                    sdto.getExecutor(),
                    "",
                    "",
                    "",
                    sdto.getProcessinstid(),
                    "",
                    sdto.getBizsystem(),
                    "",
                    "1",
                    sdto.getActionUrl());
            System.out.println("propel stdo  end  ---------------------------------------------"+a);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
