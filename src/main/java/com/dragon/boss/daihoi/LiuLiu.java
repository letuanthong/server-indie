package com.dragon.boss.daihoi;

import static com.dragon.consts.BossType.PHOBAN;

import com.dragon.boss.BossesData;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.consts.BossID;
import com.dragon.model.player.Player;

public class LiuLiu extends The23rdMartialArtCongress {

    public LiuLiu(Player player) throws Exception {
        super(PHOBAN, BossID.LIU_LIU, BossesData.LIU_LIU);
        this.playerAtt = player;
    }
}
