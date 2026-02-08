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

public class Pocolo extends The23rdMartialArtCongress {

    public Pocolo(Player player) throws Exception {
        super(PHOBAN, BossID.PO_CO_LO, BossesData.POCOLO);
        this.playerAtt = player;
    }
}

