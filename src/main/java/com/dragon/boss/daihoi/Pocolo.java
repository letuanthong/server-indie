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

public class Pocolo extends The23rdMartialArtCongress {

    public Pocolo(Player player) throws Exception {
        super(PHOBAN, BossID.PO_CO_LO, BossesData.POCOLO);
        this.playerAtt = player;
    }
}
