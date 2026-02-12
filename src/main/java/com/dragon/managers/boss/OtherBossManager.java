package com.dragon.managers.boss;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.boss.Boss;
import com.dragon.core.server.Maintenance;
import com.dragon.utils.Functions;
import com.dragon.utils.Logger;

public class OtherBossManager extends BossManager {

    private static OtherBossManager instance;

    public static OtherBossManager gI() {
        if (instance == null) {
            instance = new OtherBossManager();
        }
        return instance;
    }

    @Override
    public void run() {
        while (!Maintenance.isRunning) {
            try {
                long st = System.currentTimeMillis();
                for (int i = this.bosses.size() - 1; i >= 0; i--) {
                    if (i < this.bosses.size()) {
                        Boss boss = this.bosses.get(i);
                        try {
                            boss.update();
                        } catch (Exception e) {
                            Logger.logException(OtherBossManager.class, e);
                            try {
                                removeBoss(boss);
                            } catch (Exception ex) {
                                Logger.logException(OtherBossManager.class, ex);
                            }
                        }
                    }
                }
                Functions.sleep(Math.max(150 - (System.currentTimeMillis() - st), 10));
            } catch (Exception e) {
                Logger.logException(OtherBossManager.class, e);
            }
        }
    }
}

