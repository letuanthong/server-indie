package com.dragon.boss.yardat;

import static com.dragon.consts.BossType.YARDART;

import com.dragon.boss.BossesData;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.consts.BossID;

public class CHIENBINH1 extends Yardart {

    public CHIENBINH1() throws Exception {
        super(YARDART, BossID.CHIEN_BINH_1, BossesData.CHIEN_BINH_1);
    }

    @Override
    protected void init() {
        x = 376;
        x2 = 446;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 20000;
        rewardRatio = 3;
    }
}
