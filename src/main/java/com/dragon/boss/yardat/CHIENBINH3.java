package com.dragon.boss.yardat;

import static com.dragon.consts.BossType.YARDART;

import com.dragon.boss.BossesData;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.consts.BossID;

public class CHIENBINH3 extends Yardart {

    public CHIENBINH3() throws Exception {
        super(YARDART, BossID.CHIEN_BINH_3, BossesData.CHIEN_BINH_3);
    }

    @Override
    protected void init() {
        x = 787;
        x2 = 857;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 20000;
        rewardRatio = 3;
    }

}
