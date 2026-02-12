package managers.boss;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import boss.Boss;
import server.Maintenance;
import utils.Functions;
import utils.Logger;

public class SkillSummonedManager extends BossManager {

    private static SkillSummonedManager instance;

    public static SkillSummonedManager gI() {
        if (instance == null) {
            instance = new SkillSummonedManager();
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
                            Logger.logException(SkillSummonedManager.class, e);
                            try {
                                removeBoss(boss);
                            } catch (Exception ex) {
                                Logger.logException(SkillSummonedManager.class, ex);
                            }
                        }
                    }
                }
                Functions.sleep(Math.max(150 - (System.currentTimeMillis() - st), 10));
            } catch (Exception e) {
                Logger.logException(SkillSummonedManager.class, e);
            }
        }
    }
}

