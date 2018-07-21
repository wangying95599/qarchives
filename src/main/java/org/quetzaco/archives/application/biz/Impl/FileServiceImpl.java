package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.FileService;
import org.quetzaco.archives.application.dao.FileMapper;
import org.quetzaco.archives.model.FileExample;
import org.quetzaco.archives.model.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    FileMapper fileMapper;

    @Override
    public Map getFilesById(Long fileId) {
        Files files = fileMapper.selectByPrimaryKey(fileId);
        UUID uuid = UUID.randomUUID();
        redisTemplate.opsForValue().set(uuid, files);
        Map map = new HashMap();
        map.put("uuid", uuid);
        map.put("location", files.getLocation());
        map.put("title", files.getFileName());
        map.put("id", files.getId());
        return map;
    }

    @Override
    public Map getFilesByFileId(String fileId) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileIdEqualTo(fileId);
        Files files = fileMapper.selectByExample(fileExample).get(0);
        UUID uuid = UUID.randomUUID();
        redisTemplate.opsForValue().set(uuid.toString(), files);
        Map map = new HashMap();
        map.put("uuid", uuid);
        map.put("location", files.getLocation());
        map.put("title", files.getFileName());
        map.put("id", files.getId());
        return map;
    }
}
