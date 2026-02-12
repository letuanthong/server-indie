package com.dragon.model.npc.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import com.dragon.model.npc.Npc;
import com.dragon.model.player.Player;
import com.dragon.services.TaskService;
import com.dragon.system.QuaSuKien_8_3;

public class LyTieuNuong extends Npc {

    public LyTieuNuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
            this.createOtherMenu(player, 0, "Hiii.",
                    "Tặng Quà\n8/3");
        }
    }
    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            switch (player.idMark.getIndexMenu()) {
                case 0 -> { // con mẹ chó thắng giao việc đéo làm
                    switch (select) {
                        case 0 -> { 
                            this.createOtherMenu(player, 322, 
                                    "Bạn muốn tặng quà cho mình ư ?"
                                    , 
                                    "Tặng\nHộp Quà nhẹ\nnhàng","Tặng\nHộp Quà chỉnh\nchu","Từ chối");
                        }
                       
                    }
                   
                }         
                case 322 -> { // Kích Hoạt VIP1
                    if (select == 0) {
                        QuaSuKien_8_3.HopQuaNheNhang(player);
                    }
                    if (select == 1) {
                        QuaSuKien_8_3.HopQuaChinhChu(player);
                    }
                    break;
                }
            }
        }
    }
}
