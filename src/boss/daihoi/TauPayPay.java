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

public class TauPayPay extends The23rdMartialArtCongress {

    public TauPayPay(Player player) throws Exception {
        super(PHOBAN, BossID.TAU_PAY_PAY, BossesData.TAU_PAY_PAY);
        this.playerAtt = player;
    }
}

