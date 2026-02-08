package boss.yardat;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.BossID;
import boss.BossesData;
import static consts.BossType.YARDART;

public class TAPSU0 extends Yardart {

    public TAPSU0() throws Exception {
        super(YARDART, BossID.TAP_SU_0, BossesData.TAP_SU_0);
    }

    @Override
    protected void init() {
        x = 170;
        x2 = 240;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 30000;
        rewardRatio = 5;
    }
}

