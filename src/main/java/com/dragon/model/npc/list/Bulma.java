package com.dragon.model.npc.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.consts.ConstNpc;
import com.dragon.consts.ConstPlayer;
import com.dragon.model.npc.Npc;
import com.dragon.model.player.Player;
import com.dragon.services.map.NpcService;
import com.dragon.services.TaskService;
import com.dragon.services.ShopService;

public class Bulma extends Npc {

    public Bulma(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                if (player.gender != 0) {
                    NpcService.gI().createTutorial(player, tempId, this.avartar, "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất");
                } else if (!player.inventory.itemsDaBan.isEmpty()) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng", "Mua lại vật phẩm đã bán");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                }
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            if (player.idMark.isBaseMenu()) {
                switch (select) {
                    case 0 -> {
                        //Shop
                        if (player.gender == ConstPlayer.TRAI_DAT) {
                            ShopService.gI().opendShop(player, "BUNMA", true);
                        } else {
                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất", "Đóng");
                        }
                    }
                    case 1 -> {
                        if (!player.inventory.itemsDaBan.isEmpty()) {
                            ShopService.gI().opendShop(player, "ITEMS_DABAN", true);
                        }
                    }
                }
            }
        }
    }
}

