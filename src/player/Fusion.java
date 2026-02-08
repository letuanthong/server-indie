package player;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.ConstPlayer;
import lombok.Setter;
import utils.Util;

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

