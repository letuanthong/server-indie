package com.dragon.boss.cold;

import java.util.Random;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

import com.dragon.boss.Boss;
import com.dragon.boss.BossesData;
import com.dragon.consts.BossID;
import com.dragon.model.item.Item;
import com.dragon.model.map.ItemMap;
import com.dragon.model.player.Player;
import com.dragon.services.EffectSkillService;
import com.dragon.services.Service;
import com.dragon.services.TaskService;
import com.dragon.utils.Util;

public class Cooler extends Boss {

    private long st;

    public Cooler() throws Exception {
        super(BossID.COOLER, BossesData.COOLER, BossesData.COOLER_2);
    }

    @Override
    public void reward(Player plKill) {
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
        // int[] itemDos = new int[] { 233, 237, 241, 245, 249, 253, 257, 261, 265, 269,
        // 273, 277, 281 };
        int[] itemtime = new int[] { 381, 382, 383, 384, 385 };
        // int randomDo = new Random().nextInt(itemDos.length);
        int randomitem = new Random().nextInt(itemtime.length);
        ItemMap it = ItemMap.create(this.zone, 702, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        it.options.add(new Item.ItemOption(93, 30));
        Service.gI().dropItemMap(this.zone, it);
        if (Util.isTrue(20, 100)) {
            Service.gI().dropItemMap(this.zone, ItemMap.create(zone, itemtime[randomitem], 1, this.location.x,
                    zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
            int diem = 50;
            plKill.event.setEventPoint(diem);
            Service.gI().sendThongBao(plKill, "+50 Point");
        }
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (piercing) {
                damage /= 100;
            }
            // if (Util.isTrue(200, 1000)) {
            // this.chat("Xí hụt");
            // return 0;
            // }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage > 10_000_000) {
                damage = Util.nextInt(9_000_000, 10_000_000);
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return (int) damage;
        } else {
            return 0;
        }
    }

    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
        // System.out.println("Cooler join map" + this.zone.map.mapId + " Zone: " +
        // this.zone.zoneId);
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

}
