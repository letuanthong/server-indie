package com.dragon.boss.android;

import java.util.Random;

import com.dragon.boss.Boss;
import com.dragon.boss.BossesData;
import com.dragon.consts.BossID;
import com.dragon.consts.BossStatus;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.consts.ConstPlayer;
import com.dragon.model.map.ItemMap;
import com.dragon.model.player.Player;
import com.dragon.services.Service;
import com.dragon.services.TaskService;
import com.dragon.services.player.PlayerService;
import com.dragon.utils.Util;

public class Android14 extends Boss {

    public boolean callApk13;

    public Android14() throws Exception {
        super(BossID.ANDROID_14, BossesData.ANDROID_14);
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
    protected void resetBase() {
        super.resetBase();
        this.callApk13 = false;
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK && !this.callApk13) {
            this.changeToTypePK();
        }
        this.attack();
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.callApk13 && damage >= this.nPoint.hp) {
            this.callApk13();
            return 0;
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    public void callApk13() {
        if (this.bossAppearTogether == null || this.bossAppearTogether[this.currentLevel] == null) {
            return;
        }
        for (Boss boss : this.bossAppearTogether[this.currentLevel]) {
            if (boss.id == BossID.ANDROID_13) {
                boss.changeStatus(BossStatus.RESPAWN);
            } else if (boss.id == BossID.ANDROID_15) {
                boss.changeToTypeNonPK();
                ((Android15) boss).callApk13 = true;
                ((Android15) boss).recoverHP();
            }
        }
        this.changeToTypeNonPK();
        this.recoverHP();
        this.callApk13 = true;
    }

    public void recoverHP() {
        PlayerService.gI().hoiPhuc(this, this.nPoint.hpMax, 0);
    }

    @Override
    public void doneChatS() {
        if (this.bossAppearTogether == null || this.bossAppearTogether[this.currentLevel] == null) {
            return;
        }
        for (Boss boss : this.bossAppearTogether[this.currentLevel]) {
            if (boss.id == BossID.ANDROID_15) {
                boss.changeToTypePK();
                break;
            }
        }
    }

}
