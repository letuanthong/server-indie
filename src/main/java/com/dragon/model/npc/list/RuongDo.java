package com.dragon.model.npc.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.model.npc.Npc;
import com.dragon.model.player.Player;
import com.dragon.services.player.InventoryService;

public class RuongDo extends Npc {

    public RuongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            InventoryService.gI().sendItemBox(player);
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {

        }
    }
}

