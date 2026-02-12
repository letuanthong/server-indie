package boss.yardat;

import static consts.BossType.YARDART;

import boss.BossesData;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import consts.BossID;

public class TANBINH0 extends Yardart {

    public TANBINH0() throws Exception {
        super(YARDART, BossID.TAN_BINH_0, BossesData.TAN_BINH_0);
    }

    @Override
    protected void init() {
        x = 170;
        x2 = 240;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 25000;
        rewardRatio = 4;
    }
}
