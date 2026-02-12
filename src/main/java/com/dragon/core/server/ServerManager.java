package com.dragon.core.server;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.dragon.content.event.shenron.Shenron_Manager;
import com.dragon.core.database.daos.HistoryTransactionDAO;
import com.dragon.core.interfaces.ISession;
import com.dragon.core.interfaces.ISessionAcceptHandler;
import com.dragon.core.network.MessageSendCollect;
import com.dragon.core.network.MyKeyHandler;
import com.dragon.core.network.MySession;
import com.dragon.core.network.Network;
import com.dragon.managers.ConsignShopManager;
import com.dragon.managers.SuperRankManager;
import com.dragon.managers.boss.BossManager;
import com.dragon.managers.boss.BrolyManager;
import com.dragon.managers.boss.ChristmasEventManager;
import com.dragon.managers.boss.GasDestroyManager;
import com.dragon.managers.boss.HalloweenEventManager;
import com.dragon.managers.boss.HungVuongEventManager;
import com.dragon.managers.boss.LunarNewYearEventManager;
import com.dragon.managers.boss.OtherBossManager;
import com.dragon.managers.boss.RedRibbonHQManager;
import com.dragon.managers.boss.SkillSummonedManager;
import com.dragon.managers.boss.SnakeWayManager;
import com.dragon.managers.boss.TreasureUnderSeaManager;
import com.dragon.managers.boss.TrungThuEventManager;
import com.dragon.managers.boss.YardartManager;
import com.dragon.managers.tournament.DeathOrAliveArenaManager;
import com.dragon.managers.tournament.The23rdMartialArtCongressManager;
import com.dragon.managers.tournament.WorldMartialArtsTournamentManager;
import com.dragon.services.dungeon.NgocRongNamecService;
import com.dragon.services.player.ClanService;
import com.dragon.utils.FileRunner;
import com.dragon.utils.Logger;
import com.dragon.utils.TimeUtil;

public class ServerManager {

    public static String timeStart;

    public static final Map<Object, Object> CLIENTS = new HashMap<>();
    public static String NAME_SERVER = "NgocRongGwen.Com"; // Tên Máy Chủ
    public static String DOMAIN = "https://ngocronggwen.com/"; // Domain Truy Cập
    public static String NAME = "VOZ"; // Name Khi Vào Giao Diện Game
    public static String IP = "gm.ngocronggwen.com"; // IPs - Không Cần Sửa
    public static int PORT = 14445; // Port
    public static int EVENT_SEVER = 0;

    private static ServerManager instance;

    public static boolean isRunning;

    public void init() {
        Manager.gI();
        HistoryTransactionDAO.deleteHistory();
    }

    public static ServerManager gI() {
        if (instance == null) {
            instance = new ServerManager();
            instance.init();
        }
        return instance;
    }

    public static void main(String[] args) {
        timeStart = TimeUtil.getTimeNow("dd/MM/yyyy HH:mm:ss");
        ServerManager.gI().run();
    }

    public void run() {
        isRunning = true;
        activeServerSocket();
        AutoMaintenance.gI().start();
        NgocRongNamecService.gI().start();
        new Thread(SuperRankManager.gI(), "Update Super Rank").start();
        new Thread(The23rdMartialArtCongressManager.gI(), "Update DHVT23").start();
        new Thread(DeathOrAliveArenaManager.gI(), "Update Võ Đài Sinh Tử").start();
        new Thread(WorldMartialArtsTournamentManager.gI(), "Update WMAT").start();
        new Thread(Shenron_Manager.gI(), "Update Shenron").start();
        BossManager.gI().loadBoss();
        Manager.MAPS.forEach(com.dragon.model.map.Map::initBoss);
        new Thread(BossManager.gI(), "Update boss").start();
        new Thread(SkillSummonedManager.gI(), "Update Skill-summoned boss").start();
        new Thread(BrolyManager.gI(), "Update broly boss").start();
        new Thread(OtherBossManager.gI(), "Update other boss").start();
        new Thread(RedRibbonHQManager.gI(), "Update reb ribbon hq boss").start();
        new Thread(TreasureUnderSeaManager.gI(), "Update treasure under sea boss").start();
        new Thread(SnakeWayManager.gI(), "Update snake way boss").start();
        new Thread(GasDestroyManager.gI(), "Update gas destroy boss").start();
        new Thread(TrungThuEventManager.gI(), "Update trung thu event boss").start();
        new Thread(HalloweenEventManager.gI(), "Update halloween event boss").start();
        new Thread(ChristmasEventManager.gI(), "Update christmas event boss").start();
        new Thread(HungVuongEventManager.gI(), "Update Hung Vuong event boss").start();
        new Thread(YardartManager.gI(), "Update null new year event boss").start();
        new Thread(LunarNewYearEventManager.gI(), "Update lunar new year event boss").start();
        // Khởi chạy tác vụ Auto Save Player

    }

    private void activeServerSocket() {
        try {
            Network.gI().init().setAcceptHandler(new ISessionAcceptHandler() {
                @Override
                public void sessionInit(ISession is) {
                    if (!canConnectWithIp(is.getIP())) {
                        is.disconnect();
                        return;
                    }
                    is.setMessageHandler(Controller.gI())
                            .setSendCollect(new MessageSendCollect())
                            .setKeyHandler(new MyKeyHandler())
                            .startCollect().startQueueHandler();
                }

                @Override
                public void sessionDisconnect(ISession session) {
                    Client.gI().kickSession((MySession) session);
                }
            }).setTypeSessionClone(MySession.class)
                    .setDoSomeThingWhenClose(() -> {
                        Logger.error("SERVER CLOSE\n");
                        System.exit(0);
                    })
                    .start(PORT);
        } catch (Exception e) {
        }
    }

    private boolean canConnectWithIp(String ipAddress) {
        Object o = CLIENTS.get(ipAddress);
        if (o == null) {
            CLIENTS.put(ipAddress, 1);
            return true;
        } else {
            int n = Integer.parseInt(String.valueOf(o));
            if (n < Manager.MAX_PER_IP) {
                n++;
                CLIENTS.put(ipAddress, n);
                return true;
            } else {
                return false;
            }
        }
    }

    public void disconnect(MySession session) {
        Object o = CLIENTS.get(session.getIP());
        if (o != null) {
            int n = Integer.parseInt(String.valueOf(o));
            n--;
            if (n < 0) {
                n = 0;
            }
            CLIENTS.put(session.getIP(), n);
        }
    }

    public void close() {
        isRunning = false;
        try {
            ClanService.gI().close();
        } catch (Exception e) {
            Logger.error("Lỗi save clan!\n");
        }
        try {
            ConsignShopManager.gI().save();
        } catch (Exception e) {
            Logger.error("Lỗi save shop ký gửi!\n");
        }
        Client.gI().close();
        Logger.success("SUCCESSFULLY MAINTENANCE!\n");

        // Shutdown AutoMaintenance executor nếu đang chạy
        AutoMaintenance.gI().shutdown();
        NgocRongNamecService.gI().shutdown();

        // Nếu là auto maintenance, restart server
        if (AutoMaintenance.isRunning) {
            try {
                String batchFilePath = "run.bat";
                FileRunner.runBatchFile(batchFilePath);
            } catch (IOException e) {
                Logger.error("Lỗi khi chạy lại ứng dụng!\n");
            }
        }

        // Chạy lại ứng dụng sau khi bảo trì xong (Linux example)
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            try {
                String[] command = { "/bin/bash", "-c",
                        "cd /root/nro && nohup java -server -Xms512M -Xmx1536M -XX:MaxHeapFreeRatio=50 -jar VOZ_VER_3.jar </dev/null >nohup.out 2>nohup.err &" };
                Process process = new ProcessBuilder(command).start();
                process.waitFor(); // Chờ cho quá trình chạy hoàn thành
                Logger.success("SUCCESSFULLY RUN!\n");
            } catch (IOException | InterruptedException e) {
                Logger.error("Lỗi khi chạy lại ứng dụng trên Linux!\n");
            }
        }

        System.exit(0);
    }

}
