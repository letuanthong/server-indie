package com.dragon.model.player;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.consts.ConstPlayer;
import lombok.Setter;
import com.dragon.utils.Util;

public class Fusion {

    public static final int TIME_FUSION = 600000;

    @Setter
    private Player player;
    public byte typeFusion;
    public long lastTimeFusion;

    public Fusion(Player player) {
        this.player = player;
    }

    public void update() {
        if (typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE && Util.canDoWithTime(lastTimeFusion, TIME_FUSION)) {
            this.player.pet.unFusion();
        }
    }

    public void dispose() {
        this.player = null;
    }

}

