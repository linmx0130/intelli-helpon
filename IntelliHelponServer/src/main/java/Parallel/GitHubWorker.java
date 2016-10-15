package Parallel;

import Model.ResultEntity;
import Service.GitHub;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class GitHubWorker extends Thread {
    private static ResultEntity mresult;
    private GitHub gitHub = new GitHub();
    private String keyword;
    private String language;
    private CountDownLatch latch;

    public GitHubWorker(String keyword, CountDownLatch latch) {
        this.keyword = keyword;
        this.latch = latch;
    }

    public GitHubWorker(String keyword, String language, CountDownLatch latch) {
        this.keyword = keyword;
        this.language = language;
        this.latch = latch;
    }

    public void run() {
        try {
            System.out.println("[GitHub]  begin");
            long e = System.currentTimeMillis();
            if(this.language == null) {
                mresult = this.gitHub.getResult(this.keyword);
            } else {
                mresult = this.gitHub.getResult(this.keyword, this.language);
            }

            long now = System.currentTimeMillis();
            System.out.println("[GitHub]  end                 " + (now - e) + "ms");
            this.latch.countDown();
        } catch (IOException var5) {
            var5.printStackTrace();
            latch.countDown();
        }

    }

    public ResultEntity getResult() {
        return mresult;
    }
}
