package Service;

import Model.ResultEntity;

import java.io.IOException;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public interface IService {
    ResultEntity getResult(String keyword) throws IOException;
}
