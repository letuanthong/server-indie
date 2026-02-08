package npc;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import player.Player;

public interface IAtionNpc {

    void openBaseMenu(Player player);

    void confirmMenu(Player player, int select);

}

