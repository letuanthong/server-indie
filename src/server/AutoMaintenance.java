package server;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import utils.Logger;

public class AutoMaintenance {

    public static volatile boolean isRunning = false;
    private static volatile AutoMaintenance instance;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    
    public static final boolean IS_ENABLED = Manager.AUTO_MAINTENANCE == 1; 
    public static final int HOURS = Manager.AUTO_MAINTENANCE_HOUR; 
    public static final int MINS = Manager.AUTO_MAINTENANCE_MINUTE;

    private AutoMaintenance() {
        this.scheduler = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r, "AutoMaintenance-Thread");
            thread.setDaemon(true);
            return thread;
        });
    }

    @SuppressWarnings("DoubleCheckedLocking")
    public static AutoMaintenance gI() {
        if (instance == null) {
            synchronized (AutoMaintenance.class) {
                if (instance == null) {
                    instance = new AutoMaintenance();
                }
            }
        }
        return instance;
    }

    public void start() {
        if (!IS_ENABLED) {
            Logger.log(Logger.RED, "[SYSTEM] Auto Maintenance is disabled in config\n");
            return;
        }

        if (isRunning) {
            Logger.log(Logger.RED, "[SYSTEM] Auto Maintenance is already running\n");
            return;
        }

        long delaySeconds = calculateDelaySeconds();
        Logger.log(Logger.RED, "[SYSTEM] Auto Maintenance will occur in " + delaySeconds + " seconds (at " + HOURS + ":" + String.format("%02d", MINS) + ")\n");

        scheduledTask = scheduler.schedule(() -> {
            try {
                Logger.log(Logger.RED, "[SYSTEM] Starting auto maintenance process\n");
                Maintenance.gI().start(60);
                isRunning = true;
            } catch (Exception e) {
                Logger.logException(AutoMaintenance.class, e, "[SYSTEM] Error during auto maintenance");
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }

    private long calculateDelaySeconds() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.withHour(HOURS).withMinute(MINS).withSecond(0).withNano(0);
        
        // Nếu thời gian đã qua trong ngày hôm nay, lên lịch cho ngày mai
        if (now.isAfter(targetTime) || now.equals(targetTime)) {
            targetTime = targetTime.plusDays(1);
        }
        
        return Duration.between(now, targetTime).getSeconds();
    }

    public void cancel() {
        if (scheduledTask != null && !scheduledTask.isCancelled()) {
            scheduledTask.cancel(false);
            Logger.log(Logger.RED, "[SYSTEM] Đã hủy lịch bảo trì tự động\n");
        }
    }

    public void shutdown() {
        cancel();
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public boolean isScheduled() {
        return scheduledTask != null && !scheduledTask.isDone() && !scheduledTask.isCancelled();
    }

}


