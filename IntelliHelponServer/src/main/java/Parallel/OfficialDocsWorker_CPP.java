package Parallel;

import Model.ResultEntity;
import Service.OfficialDocs_CPP;
import Service.OfficialDocs_Java;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 王渝 on 2016-10-16.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class OfficialDocsWorker_CPP extends Thread {
    private OfficialDocs_CPP officialDocs_cpp;
    private CountDownLatch latch;
    private ResultEntity resultEntity;

    public OfficialDocsWorker_CPP(CountDownLatch latch, String keyword) {
        officialDocs_cpp = new OfficialDocs_CPP();
        this.latch = latch;
        this.keyword = keyword;
    }

    private String keyword;

    public void run() {
        System.out.println("[OfficialDocs]   begin");
        long currentTime = System.currentTimeMillis();
        try {
            resultEntity = officialDocs_cpp.getResult(keyword);
            long now = System.currentTimeMillis();
            System.out.println("[OfficialDocs]   end             "+(now - currentTime)+"ms");
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    public ResultEntity getResult() {
        return resultEntity;
    }
}
