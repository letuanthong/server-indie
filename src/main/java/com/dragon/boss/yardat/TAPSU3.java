package com.dragon.boss.yardat;

import static com.dragon.consts.BossType.YARDART;

import com.dragon.boss.BossesData;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.consts.BossID;

public class TAPSU3 extends Yardart {

    public TAPSU3() throws Exception {
        super(YARDART, BossID.TAP_SU_3, BossesData.TAP_SU_3);
    }

    @Override
    protected void init() {
        x = 787;
        x2 = 857;
        y = 456;
        y2 = 408;
        range = 1000;
        range2 = 150;
        timeHoiHP = 30000;
        rewardRatio = 5;
    }
}
