package com.dragon.content.matches;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
// thonk
// import matches.PVP;
// import matches.TYPE_LOSE_PVP;
// import matches.TYPE_PVP;
import com.dragon.model.player.Player;
import com.dragon.services.EffectSkillService;
import com.dragon.services.Service;

public class PKCommeson extends PVP {

    private PKCommeson(Player p1, Player p2) {
        super(TYPE_PVP.THACH_DAU, p1, p2);
    }

    public static PKCommeson create(Player p1, Player p2) {
        PKCommeson pvp = new PKCommeson(p1, p2);
        pvp.init();
        return pvp;
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
    public void sendResult(Player plLose, TYPE_LOSE_PVP typeLose) {
        if (typeLose == TYPE_LOSE_PVP.RUNS_AWAY) {
            Player pl = p1.isPl() ? p1 : p2;
            EffectSkillService.gI().removePKCommeson(pl);
            if (pl.equals(plLose)) {
                Service.gI().sendThongBao(pl, "Bạn đã thất bại, ngày mai hãy thử sức tiếp");
            }
        }
    }

    @Override
    public void reward(Player plWin) {

    }

}

