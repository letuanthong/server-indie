package com.dragon.core.server;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.dragon.services.Service;
import com.dragon.utils.Logger;

public class Maintenance {

    public static volatile boolean isRunning = false;

    private static Maintenance i;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r, "Maintenance-Thread");
        thread.setDaemon(true);
        return thread;
    });
    private final AtomicInteger timeRemaining = new AtomicInteger(0);

    private Maintenance() {
    }

    public static Maintenance gI() {
        if (i == null) {
            i = new Maintenance();
        }
        return i;
    }

    public void start(int min) {
        if (!isRunning) {
            isRunning = true;
            this.timeRemaining.set(min * 60);
            startCountdown();
        }
    }

    public void startNew(int min) {
        if (!isRunning) {
            isRunning = true;
            this.timeRemaining.set(min * 60);
            startCountdown();
        }
    }

    public void startImmediately() {
        if (!isRunning) {
            isRunning = true;
            Logger.log(Logger.YELLOW, "[SYSTEM] BEGIN MAINTENANCE\n");
            ServerManager.gI().close();
        }
    }

    private void startCountdown() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                int time = timeRemaining.get();
                
                if (time <= 0) {
                    Logger.log(Logger.YELLOW, "[SYSTEM] BEGIN MAINTENANCE\n");
                    scheduler.shutdown();
                    ServerManager.gI().close();
                    return;
                }

                // Gửi thông báo dựa trên thời gian còn lại
                if (time == 60) {
                    Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau 1 phút nữa hãy thoát game ngay để tránh mất mát vật phẩm.");
                    timeRemaining.decrementAndGet();
                } else if (time < 60) {
                    Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau " + time + " giây nữa");
                    timeRemaining.decrementAndGet();
                } else {
                    int hour = time / 3600;
                    int min = (time - hour * 3600) / 60;
                    int sec = time % 60;

                    String hourStr = (hour > 0) ? hour + " giờ " : "";
                    String minStr = (min > 0) ? min + " phút " : "";
                    String secStr = (sec > 0) ? sec + " giây " : "";

                    Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau " + hourStr + minStr + secStr + "nữa");
                    Logger.log(Logger.YELLOW, "[SYSTEM] Hệ thống sẽ bảo trì sau " + hourStr + minStr + secStr + "nữa\n");
                    
                    int decrement = (sec == 0 && time > 60) ? 60 : (sec == 0 ? 1 : sec);
                    timeRemaining.addAndGet(-decrement);
                }
            } catch (Exception e) {
                Logger.logException(Maintenance.class, e, "Lỗi trong quá trình countdown bảo trì");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void cancel() {
        isRunning = false;
        scheduler.shutdownNow();
    }

}

