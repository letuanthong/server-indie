package npc.list;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.ConstNpc;
import npc.Npc;
import player.Player;
import services.ShopService;
import services.TaskService;


public class BulmaTuongLai extends Npc {

    public BulmaTuongLai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (this.mapId == 104 || this.mapId == 5) {
                if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "build", "Cửa hàng", "Đóng");
                }
            } else if (this.mapId == 102) {
                if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "learn", "Cửa hàng");
                }
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            if (this.mapId == 104 || this.mapId == 5) {
                if (player.idMark.isBaseMenu()) {
                    if (select == 0) {
                        ShopService.gI().opendShop(player, "KARIN", true);
                    }
                }
            } else if (this.mapId == 102) {
                if (player.idMark.isBaseMenu()) {
                    switch (select) {
                        case 0 ->
                            ShopService.gI().opendShop(player, "BUNMA_FUTURE", true);
                      
                    }
                            }
                        
                
            }
        }
    }
}

