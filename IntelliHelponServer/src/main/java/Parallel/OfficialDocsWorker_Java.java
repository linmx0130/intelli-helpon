package Parallel;

import Model.ResultEntity;
import Service.OfficialDocs_Java;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class OfficialDocsWorker_Java extends Thread {

    private OfficialDocs_Java officialDocs_java;
    private CountDownLatch latch;
    private ResultEntity resultEntity;

    public OfficialDocsWorker_Java(CountDownLatch latch, String keyword) {
        officialDocs_java = new OfficialDocs_Java();
        this.latch = latch;
        this.keyword = keyword;
    }

    private String keyword;

    public void run(){
        System.out.println("[OfficialDocs]  begin");
        long currentTime = System.currentTimeMillis();
        try {
            resultEntity = officialDocs_java.getResult(keyword);

            long now = System.currentTimeMillis();
            System.out.println("[OfficialDocs]  end                 " + (now - currentTime) + "ms");
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    public ResultEntity getResult(){
        return resultEntity;
    }
}
