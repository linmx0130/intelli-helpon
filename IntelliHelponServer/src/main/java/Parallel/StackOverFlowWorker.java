package Parallel;

import Model.ResultEntity;
import Service.StackOverFlow;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class StackOverFlowWorker extends Thread {
    private CountDownLatch latch;
    private StackOverFlow stackOverFlow;
    private String keyword;
    private String language;
    private ResultEntity resultEntity;

    public StackOverFlowWorker(CountDownLatch latch, String keyword, String language) {
        this.latch = latch;
        this.keyword = keyword;
        this.language = language;
        this.stackOverFlow = new StackOverFlow();
    }

    public StackOverFlowWorker(CountDownLatch latch, String keyword) {
        this.latch = latch;
        this.keyword = keyword;
        this.stackOverFlow = new StackOverFlow();
    }

    public void run() {
        try {
            System.out.println("[StackOverFlow]  begin");
            long e = System.currentTimeMillis();
            if(this.language == null) {
                this.resultEntity = this.stackOverFlow.getResult(this.keyword);
            } else {
                this.resultEntity = this.stackOverFlow.getResult(this.keyword, this.language);
            }

            long now = System.currentTimeMillis();
            System.out.println("[StackOverFlow]  end             "+(now - e)+"ms");
            this.latch.countDown();
        } catch (IOException var5) {
            var5.printStackTrace();
            latch.countDown();
        }

    }

    public ResultEntity getResult() {
        return this.resultEntity;
    }
}
