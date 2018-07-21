package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import org.quetzaco.archives.application.biz.HomePictureService;
import org.quetzaco.archives.application.dao.HomePictureMapper;
import org.quetzaco.archives.model.HomePicture;
import org.quetzaco.archives.model.HomePictureExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePictureServiceImpl implements HomePictureService {

    @Autowired
    HomePictureMapper homePictureMapper;

    @Override
    public void uploadPicture(HomePicture homePicture) {
        homePictureMapper.insertSelective(homePicture);
    }

    @Override
    public List<HomePicture> getPicture() {
        HomePictureExample homePictureExample = new HomePictureExample();
        homePictureExample.setOrderByClause("created_dt desc");
        PageHelper.startPage(1, 3);
        return homePictureMapper.selectByExample(homePictureExample);
    }
}
