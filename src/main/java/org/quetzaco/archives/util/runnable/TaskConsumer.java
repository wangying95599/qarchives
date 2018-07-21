package org.quetzaco.archives.util.runnable;

import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.dao.DocFilesMapper;
import org.quetzaco.archives.model.DocFiles;
import org.quetzaco.archives.model.DocFilesExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskConsumer extends Thread {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DocumentService documentService;
    @Value("${quetzaco.archive.config.file-storage}")
    private String prefixPath;
    @Autowired
    DocFilesMapper docFilesMapper;

    // @Async
    public void translateToPdf() {
        List<DocFiles> errorTranslate = new ArrayList();
        int pageCount = (int) redisTemplate.opsForValue().get("pageCount");
        for (int i = 0; i < pageCount; i++) {
            DocFiles file = (DocFiles) redisTemplate.opsForList().rightPop("task-queue");
            String taskPath = prefixPath + "/" + file.getRealPath() + "/" + file.getRealFilename();
            boolean status = documentService.ConvertFileToSwf1(taskPath);
            if (!status) {
                errorTranslate.add(file);
            }
            System.out.println("thread name" + Thread.currentThread().getName());
        }

        ToPdfAgain(errorTranslate);
    }

    private void ToPdfAgain(List<DocFiles> errorTranslate) {
        List<String> errorAgain = new ArrayList<>();
        for (DocFiles df : errorTranslate) {
            String taskPath = prefixPath + "/" + df.getRealPath() + "/" + df.getRealFilename();
            boolean status = documentService.ConvertFileToSwf1(taskPath);
            if (!status) {
                errorAgain.add(df.getFid());
            }
        }

        if (errorAgain != null && errorAgain.size() > 0) {
            DocFiles df1 = new DocFiles();
            df1.setUpdateFlag("errorToPdf");
            DocFilesExample docFilesExample = new DocFilesExample();
            docFilesExample.createCriteria().andFidIn(errorAgain);
            docFilesMapper.updateByExampleSelective(df1, docFilesExample);
            System.out.println("1111");
        }
    }

    @Override
    public void run() {
        List<DocFiles> errorTranslate = new ArrayList();
        while (true) {
            // boolean status=false;

            if (redisTemplate.opsForList().size("task-queue")==0 && (boolean) redisTemplate.opsForValue().get("isCopyComplete")) {
                ToPdfAgain(errorTranslate);
                System.out.println("pdf.swf 转换完毕");
                break;
            }

            DocFiles file = (DocFiles) redisTemplate.opsForList().rightPop("task-queue");
            String taskPath = prefixPath + "/" + file.getRealPath() + "/" + file.getRealFilename();
            if (taskPath == null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }


            boolean status = documentService.ConvertFileToSwf1(taskPath);
            if (!status) {
                errorTranslate.add(file);
                System.out.println("taskpath     :     " + taskPath);
                System.out.println(taskPath + "处理失败，被弹回任务队列" + this.getName());
            }
            System.out.println(this.getName() + "task-queue :     " + redisTemplate.opsForList().range("task-queue", 0, 5) + " 后5位 ：  " +
                    redisTemplate.opsForList().range("task-queue", redisTemplate.opsForList().size("task-queue") - 5, -1)
                    + "   len  :" + redisTemplate.opsForList().size("task-queue"));
        }
    }

}
