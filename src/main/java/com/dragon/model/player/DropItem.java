package com.dragon.model.player;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.model.item.Item;
import com.dragon.model.map.ItemMap;
import com.dragon.model.map.Zone;
import com.dragon.services.player.InventoryService;
import com.dragon.services.map.ItemMapService;
import com.dragon.services.Service;
import com.dragon.utils.Util;

public class DropItem {

    private Player player;

    public DropItem(Player player) {
        this.player = player;
    }

    public void update() {
        Zone zone = player.zone;
        if (player.isPl() && zone != null && zone.map.mapId == 52
                && InventoryService.gI().getCountEmptyBag(player) > 0
                && !InventoryService.gI().isExistItemBag(player, 726)
                && !ItemMapService.gI().findItemMapByPlayer(player, 726)) {
            int x = Util.nextInt(100, zone.map.mapWidth - 100);
            int y = zone.map.yPhysicInTop(x, 100);
            ItemMap it = ItemMap.create(zone, 726, 1, x, y, player.id);
            it.options.add(new Item.ItemOption(30, 0));
            it.options.add(new Item.ItemOption(93, 1));
            Service.gI().dropItemMapForMe(player, it);
        }
    }

    public void dispose() {
        player = null;
    }
}

