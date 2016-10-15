package Model;

import java.util.List;

/**
 * Created by 王渝 on 2016-10-15.
 * Email : wwangyuu@outlook.com
 * University : University of Electronic Science and Technology of Zhangjiang
 */
public class ResultEntity {
    public ResultEntity(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    List<Item> items;

    public void Append(ResultEntity another){
        this.items.addAll(another.getItems());
    }
}

