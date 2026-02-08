package interfaces;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import matches.TYPE_LOSE_PVP;
import player.Player;

public interface IPVP {

    void start();

    void finish();

    void dispose();

    void update();

    void reward(Player plWin);

    void sendResult(Player plLose, TYPE_LOSE_PVP typeLose);

    void lose(Player plLose, TYPE_LOSE_PVP typeLose);

    boolean isInPVP(Player pl);
}

