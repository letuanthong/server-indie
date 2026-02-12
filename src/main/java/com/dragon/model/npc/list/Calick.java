package com.dragon.model.npc.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.consts.ConstNpc;
import com.dragon.consts.ConstTask;
import com.dragon.model.map.Map;
import com.dragon.model.npc.Npc;
import com.dragon.model.player.Player;
import com.dragon.services.map.MapService;
import com.dragon.services.map.NpcService;
import com.dragon.services.Service;
import com.dragon.services.TaskService;
import com.dragon.services.map.ChangeMapService;
import com.dragon.utils.Util;

public class Calick extends Npc {

    private final byte COUNT_CHANGE = 25;
    private int count;

    public Calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    private void changeMap() {
        if (this.mapId != 102) {
            count++;
            if (this.count >= COUNT_CHANGE) {
                count = 0;
                this.map.npcs.remove(this);
                Map mapcl = MapService.gI().getMapForCalich();
                this.mapId = mapcl.mapId;
                this.cx = Util.nextInt(100, mapcl.mapWidth - 100);
                this.cy = mapcl.yPhysicInTop(this.cx, 0);
                this.map = mapcl;
                this.map.npcs.add(this);
            }
        }
    }

    @Override
    public void openBaseMenu(Player player) {
        if (TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
            return;
        }
        player.idMark.setIndexMenu(ConstNpc.BASE_MENU);
        if (TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
            Service.gI().hideWaitDialog(player);
            Service.gI().sendThongBao(player, "Không thể thực hiện");
            return;
        }
        if (this.mapId != player.zone.map.mapId) {
            Service.gI().sendThongBao(player, "Calích đã rời khỏi map!");
            Service.gI().hideWaitDialog(player);
            return;
        }

        if (this.mapId == 102) {
            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                    "Chào chú, cháu có thể giúp gì?",
                    "Kể\nChuyện", "Quay về\nQuá khứ");
        } else {
            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                    "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối");
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (this.mapId == 102) {
            if (player.idMark.isBaseMenu()) {
                if (select == 0) {
                    //kể chuyện
                    NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                } else if (select == 1) {
                    //về quá khứ
                    ChangeMapService.gI().goToQuaKhu(player);
                }
            }
        } else if (player.idMark.isBaseMenu()) {
            switch (select) {
                case 0 -> //kể chuyện
                    NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                case 1 -> {
                    //đến tương lai
                    changeMap();
                    if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_20_0) {
                        ChangeMapService.gI().goToTuongLai(player);
                    }
                }
                default ->
                    Service.gI().sendThongBao(player, "Không thể thực hiện");
            }
        }
    }
}

