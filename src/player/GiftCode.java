package player;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import java.util.ArrayList;
import java.util.List;

public class GiftCode {

    public List<String> rewards;

    public GiftCode() {
        this.rewards = new ArrayList<>();
    }

    public void add(String code) {
        this.rewards.add(code);
    }

    public boolean isUsedGiftCode(String code) {
        return rewards.contains(code);
    }

    public void dispose() {
        if (rewards != null) {
            rewards.clear();
            rewards = null;
        }
    }

}

