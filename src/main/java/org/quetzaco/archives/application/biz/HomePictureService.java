package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.HomePicture;

import java.util.List;

public interface HomePictureService {
    void uploadPicture(HomePicture homePicture);

    List<HomePicture> getPicture();
}
