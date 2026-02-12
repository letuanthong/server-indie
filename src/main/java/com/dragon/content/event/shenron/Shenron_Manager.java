package com.dragon.content.event.shenron;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */

// thonk
// import event.shenron.Shenron_Event;
import com.dragon.utils.Functions;
import com.dragon.utils.Util;

import java.util.ArrayList;
import java.util.List;

import com.dragon.core.server.Maintenance;
import com.dragon.utils.Logger;

public class Shenron_Manager implements Runnable {

    private static Shenron_Manager instance;
    private long lastUpdate;
    private static final List<Shenron_Event> list = new ArrayList<>();

    ;

    public static Shenron_Manager gI() {
        if (instance == null) {
            instance = new Shenron_Manager();
        }
        return instance;
    }

    @Override
    public void run() {
        while (!Maintenance.isRunning) {
            try {
                long start = System.currentTimeMillis();
                update();
                long timeUpdate = System.currentTimeMillis() - start;
                Functions.sleep(Math.max(1000 - timeUpdate, 10));
            } catch (Exception ex) {
                Logger.logException(Shenron_Manager.class, ex);
            }
        }
    }

    public void update() {
        if (Util.canDoWithTime(lastUpdate, 1000)) {
            lastUpdate = System.currentTimeMillis();
            List<Shenron_Event> listCopy = new ArrayList<>(list);

            for (Shenron_Event se : listCopy) {
                try {
                    se.update();
                } catch (Exception e) {
                    Logger.logException(Shenron_Manager.class, e);
                }
            }
            listCopy.clear();
        }
    }

    public void add(Shenron_Event se) {
        list.add(se);
    }

    public void remove(Shenron_Event se) {
        list.remove(se);
    }

}

