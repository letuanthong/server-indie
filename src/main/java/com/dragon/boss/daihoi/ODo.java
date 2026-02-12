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

public class ODo extends The23rdMartialArtCongress {

    public ODo(Player player) throws Exception {
        super(PHOBAN, BossID.O_DO, BossesData.O_DO);
        this.playerAtt = player;
    }
}
