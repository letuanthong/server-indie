package com.dragon.boss.ginyu_nappa;

import java.util.Random;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import com.dragon.boss.Boss;
import com.dragon.boss.BossesData;
import com.dragon.consts.BossID;
import com.dragon.consts.BossStatus;
import com.dragon.model.map.ItemMap;
import com.dragon.model.player.Player;
import com.dragon.services.Service;
import com.dragon.services.TaskService;
import com.dragon.utils.Util;

public class SO1 extends Boss {

    private long st;

    public SO1() throws Exception {
        super(BossID.SO_1, false, true, BossesData.SO_1);
    }

    @Override
    public void moveTo(int x, int y) {
        if (this.currentLevel == 1) {
            return;
        }
        super.moveTo(x, y);
    }

    @Override
    public void reward(Player plKill) {
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);

        // Rơi vàng (ID 190)
        Service.gI().dropItemMap(this.zone, ItemMap.create(this.zone, 190, Util.nextInt(20000, 30001),
                this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));

        // 50% xác suất rơi thêm vật phẩm
        if (Util.isTrue(80, 100)) {
            int[] items = Util.isTrue(50, 100) ? new int[] { 18, 19, 20 }
                    : new int[] { 1066, 1067, 1068, 1069, 1070, 1229 };
            int randomItem = items[new Random().nextInt(items.length)];
            Service.gI().dropItemMap(this.zone, ItemMap.create(this.zone, randomItem, 1,
                    this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
    }

    @Override
    protected void notifyJoinMap() {
        if (this.currentLevel == 1) {
            return;
        }
        super.notifyJoinMap();
    }

    @Override
    public void doneChatS() {
        this.changeStatus(BossStatus.AFK);
    }

    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(st, 900000)) {
            this.leaveMapNew();
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            st = System.currentTimeMillis();
        }
    }

    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
    }

    @Override
    public void doneChatE() {
        if (this.parentBoss == null || this.parentBoss.bossAppearTogether == null
                || this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel] == null) {
            return;
        }
        for (Boss boss : this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel]) {
            if (boss.id == BossID.SO_2 && !boss.isDie()) {
                return;
            }
        }
        this.parentBoss.changeStatus(BossStatus.ACTIVE);
    }

}
