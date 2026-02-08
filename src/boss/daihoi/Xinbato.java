package boss.daihoi;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.BossID;
import boss.BossesData;
import static consts.BossType.PHOBAN;
import player.Player;

public class Xinbato extends The23rdMartialArtCongress {

    public Xinbato(Player player) throws Exception {
        super(PHOBAN, BossID.XINBATO, BossesData.XINBATO);
        this.playerAtt = player;
    }
}

