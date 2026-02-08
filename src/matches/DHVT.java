package matches;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */

// thonk
// import matches.PVP;
// import matches.TYPE_LOSE_PVP;
// import matches.TYPE_PVP;
import player.Player;

public class DHVT extends PVP {

    private DHVT(Player p1, Player p2) {
        super(TYPE_PVP.THACH_DAU, p1, p2);
    }

    public static DHVT create(Player p1, Player p2) {
        DHVT pvp = new DHVT(p1, p2);
        pvp.init();
        return pvp;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void finish() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void update() {
    }

    @Override
    public void reward(Player plWin) {
    }

    @Override
    public void sendResult(Player plLose, TYPE_LOSE_PVP typeLose) {
    }

}

