package combine;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import lombok.Setter;
import item.Item;
import java.util.ArrayList;
import java.util.List;

public class Combine {

    public long lastTimeCombine;

    public List<Item> itemsCombine;
    @Setter
    public int typeCombine;

    public int goldCombine;
    public int gemCombine;
    public float ratioCombine;
    public int countDaNangCap;
    public short countDaBaoVe;

    public Combine() {
        this.itemsCombine = new ArrayList<>();
    }

    public void clearItemCombine() {
        this.itemsCombine.clear();
    }

    public void clearParamCombine() {
        this.goldCombine = 0;
        this.gemCombine = 0;
        this.ratioCombine = 0;
        this.countDaNangCap = 0;
        this.countDaBaoVe = 0;

    }

    public void dispose() {
        this.itemsCombine = null;
    }
}

