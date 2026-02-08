package npc.list;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import npc.Npc;
import player.Player;
import services.ShopService;

public class Uron extends Npc {

    public Uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player pl) {
        if (canOpenNpc(pl)) {
            ShopService.gI().opendShop(pl, "URON", false);
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {

        }
    }
}

