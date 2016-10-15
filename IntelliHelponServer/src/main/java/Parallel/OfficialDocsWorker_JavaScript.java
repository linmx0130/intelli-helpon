package Parallel;

import Model.ResultEntity;
import Service.OfficialDocs_Java;
import Service.OfficialDocs_JavaScript;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class OfficialDocsWorker_JavaScript extends Thread{
    private OfficialDocs_JavaScript officialDocs_javascript;
    private CountDownLatch latch;
    private ResultEntity resultEntity;

    public OfficialDocsWorker_JavaScript(CountDownLatch latch, String keyword) {
        officialDocs_javascript = new OfficialDocs_JavaScript();
        this.latch = latch;
        this.keyword = keyword;
    }

    private String keyword;

    public void run(){
        System.out.println("[OfficialDocs]  begin");
        long currentTime = System.currentTimeMillis();
        try {
            resultEntity = officialDocs_javascript.getResult(keyword);
            long now = System.currentTimeMillis();
            System.out.println("[OfficialDocs]  end                 " + (now - currentTime) + "ms");
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultEntity getResult(){
        return resultEntity;
    }

}
