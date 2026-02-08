package boss.yardat;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.BossID;
import boss.BossesData;
import static consts.BossType.YARDART;

public class CHIENBINH2 extends Yardart {

    public CHIENBINH2() throws Exception {
        super(YARDART, BossID.CHIEN_BINH_2, BossesData.CHIEN_BINH_2);
    }

    @Override
    protected void init() {
        x = 582;
        x2 = 652;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 20000;
        rewardRatio = 3;
    }
}

