package managers;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import java.util.ArrayList;

import matches.PVP;
import player.Player;
import server.ServerManager;
import utils.Functions;

public class PVPManager implements Runnable {

    private static PVPManager i;

    public static PVPManager gI() {
        if (i == null) {
            i = new PVPManager();
            i.start();
        }
        return i;
    }

    private final ArrayList<PVP> pvps;

    public PVPManager() {
        this.pvps = new ArrayList<>();
    }

    private void start() {
        new Thread(this, "Update pvp").start();
    }

    public void removePVP(PVP pvp) {
        this.pvps.remove(pvp);
    }

    public void addPVP(PVP pvp) {
        this.pvps.add(pvp);
    }

    public PVP getPVP(Player player) {
        for (PVP pvp : this.pvps) {
            if (pvp.p1.equals(player) || pvp.p2.equals(player)) {
                return pvp;
            }
        }
        return null;
    }

    @Override
    public void run() {
        this.update();
    }

    private void update() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                for (PVP pvp : pvps) {
                    pvp.update();
                }
                Functions.sleep(Math.max(1000 - (System.currentTimeMillis() - st), 10));
            } catch (Exception e) {
            }
        }
    }

}

