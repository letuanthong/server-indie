package com.dragon.managers.boss;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dragon.boss.Boss;
import com.dragon.boss.android.Android13;
import com.dragon.boss.android.Android14;
import com.dragon.boss.android.Android15;
import com.dragon.boss.android.Android19;
import com.dragon.boss.android.DrKore;
import com.dragon.boss.android.KingKong;
import com.dragon.boss.android.Pic;
import com.dragon.boss.android.Poc;
import com.dragon.boss.black.BlackGoku;
import com.dragon.boss.broly.Broly;
import com.dragon.boss.cell.SieuBoHung;
import com.dragon.boss.cell.XENCON1;
import com.dragon.boss.cell.XENCON2;
import com.dragon.boss.cell.XENCON3;
import com.dragon.boss.cell.XENCON4;
import com.dragon.boss.cell.XENCON5;
import com.dragon.boss.cell.XENCON6;
import com.dragon.boss.cell.XENCON7;
import com.dragon.boss.cell.XenBoHung;
import com.dragon.boss.cold.Cooler;
import com.dragon.boss.earth.BIDO;
import com.dragon.boss.earth.BOJACK;
import com.dragon.boss.earth.BUJIN;
import com.dragon.boss.earth.KOGU;
import com.dragon.boss.earth.SUPER_BOJACK;
import com.dragon.boss.earth.ZANGYA;
import com.dragon.boss.event.halloween.BiMa;
import com.dragon.boss.event.halloween.Doi;
import com.dragon.boss.event.halloween.MaTroi;
import com.dragon.boss.event.hungvuong.SonTinh;
import com.dragon.boss.event.hungvuong.ThuyTinh;
import com.dragon.boss.event.newyear.LanCon;
import com.dragon.boss.event.noel.OngGiaNoel;
import com.dragon.boss.event.trungthu.KhiDot;
import com.dragon.boss.event.trungthu.NguyetThan;
import com.dragon.boss.event.trungthu.NhatThan;
import com.dragon.boss.fide.Fide;
import com.dragon.boss.ginyu_namek.SO1_NM;
import com.dragon.boss.ginyu_namek.SO2_NM;
import com.dragon.boss.ginyu_namek.SO3_NM;
import com.dragon.boss.ginyu_namek.SO4_NM;
import com.dragon.boss.ginyu_namek.TDT_NM;
import com.dragon.boss.ginyu_nappa.SO1;
import com.dragon.boss.ginyu_nappa.SO2;
import com.dragon.boss.ginyu_nappa.SO3;
import com.dragon.boss.ginyu_nappa.SO4;
import com.dragon.boss.ginyu_nappa.TDT;
import com.dragon.boss.golden_fide.DeathBeam1;
import com.dragon.boss.golden_fide.DeathBeam2;
import com.dragon.boss.golden_fide.DeathBeam3;
import com.dragon.boss.golden_fide.DeathBeam4;
import com.dragon.boss.golden_fide.DeathBeam5;
import com.dragon.boss.golden_fide.GoldenFrieza;
import com.dragon.boss.mabu_12H.BuiBui;
import com.dragon.boss.mabu_12H.BuiBui2;
import com.dragon.boss.mabu_12H.Cadic;
import com.dragon.boss.mabu_12H.Drabura;
import com.dragon.boss.mabu_12H.Drabura2;
import com.dragon.boss.mabu_12H.Drabura3;
import com.dragon.boss.mabu_12H.Goku;
import com.dragon.boss.mabu_12H.Mabu;
import com.dragon.boss.mabu_12H.Yacon;
import com.dragon.boss.mabu_14H.Mabu2H;
import com.dragon.boss.mabu_14H.SuperBu;
import com.dragon.boss.miniboss.Odo;
import com.dragon.boss.miniboss.SoiHecQuyn;
import com.dragon.boss.miniboss.Virus;
import com.dragon.boss.nappa.Kuku;
import com.dragon.boss.nappa.MapDauDinh;
import com.dragon.boss.nappa.Rambo;
import com.dragon.boss.tau_77.TaoPaiPai;
import com.dragon.boss.yardat.CHIENBINH0;
import com.dragon.boss.yardat.CHIENBINH1;
import com.dragon.boss.yardat.CHIENBINH2;
import com.dragon.boss.yardat.CHIENBINH3;
import com.dragon.boss.yardat.CHIENBINH4;
import com.dragon.boss.yardat.CHIENBINH5;
import com.dragon.boss.yardat.DOITRUONG5;
import com.dragon.boss.yardat.TANBINH0;
import com.dragon.boss.yardat.TANBINH1;
import com.dragon.boss.yardat.TANBINH2;
import com.dragon.boss.yardat.TANBINH3;
import com.dragon.boss.yardat.TANBINH4;
import com.dragon.boss.yardat.TANBINH5;
import com.dragon.boss.yardat.TAPSU0;
import com.dragon.boss.yardat.TAPSU1;
import com.dragon.boss.yardat.TAPSU2;
import com.dragon.boss.yardat.TAPSU3;
import com.dragon.boss.yardat.TAPSU4;
import com.dragon.consts.BossID;
import com.dragon.model.map.Zone;
import com.dragon.core.network.Message;
import com.dragon.model.player.Player;
import com.dragon.core.server.Maintenance;
import com.dragon.services.map.MapService;
import com.dragon.utils.Functions;
import com.dragon.utils.Logger;

public class BossManager implements Runnable {

    private static BossManager instance;
    public static byte ratioReward = 10;

    public static BossManager gI() {
        if (instance == null) {
            instance = new BossManager();
        }
        return instance;
    }

    public BossManager() {
        this.bosses = new ArrayList<>();
    }

    protected final List<Boss> bosses;

    public void addBoss(Boss boss) {
        this.bosses.add(boss);
    }

    public void removeBoss(Boss boss) {
        this.bosses.remove(boss);
    }
//O DCM

    public void loadBoss() {
        this.createBoss(BossID.BROLY, 50);
        this.createBoss(BossID.TIEU_DOI_TRUONG);
        this.createBoss(BossID.TIEU_DOI_TRUONG_NM);
        this.createBoss(BossID.BOJACK);
        this.createBoss(BossID.SUPER_BOJACK);
        this.createBoss(BossID.KING_KONG);
        this.createBoss(BossID.XEN_BO_HUNG);
        this.createBoss(BossID.SIEU_BO_HUNG);
        this.createBoss(BossID.KUKU, 5);
        this.createBoss(BossID.MAP_DAU_DINH, 5);
        this.createBoss(BossID.RAMBO, 5);
        this.createBoss(BossID.FIDE);
        this.createBoss(BossID.ANDROID_14);
        this.createBoss(BossID.DR_KORE);
        this.createBoss(BossID.COOLER);
        this.createBoss(BossID.BLACK_GOKU, 5);
        this.createBoss(BossID.GOLDEN_FRIEZA, 5);
        this.createBoss(BossID.SOI_HEC_QUYN1, 10);
//        this.createBoss(BossID.AN_TROM, 100);
//        this.createBoss(BossID.O_DO1, 10);
//        this.createBoss(BossID.Virut, 10);
        Logger.success("[BOSS] Loaded " + this.bosses.size() + " bosses successfully!\n");

    }

    public void createBoss(int bossID, int total) {
        for (int i = 0; i < total; i++) {
            createBoss(bossID);
        }
    }

    public Boss createBoss(int bossID) {
        try {
            return switch (bossID) {
                case BossID.BROLY ->
                    new Broly();
                case BossID.TAP_SU_0 ->
                    new TAPSU0();
                case BossID.TAP_SU_1 ->
                    new TAPSU1();
                case BossID.TAP_SU_2 ->
                    new TAPSU2();
                case BossID.TAP_SU_3 ->
                    new TAPSU3();
                case BossID.TAP_SU_4 ->
                    new TAPSU4();
                case BossID.TAN_BINH_5 ->
                    new TANBINH5();
                case BossID.TAN_BINH_0 ->
                    new TANBINH0();
                case BossID.TAN_BINH_1 ->
                    new TANBINH1();
                case BossID.TAN_BINH_2 ->
                    new TANBINH2();
                case BossID.TAN_BINH_3 ->
                    new TANBINH3();
                case BossID.TAN_BINH_4 ->
                    new TANBINH4();
                case BossID.CHIEN_BINH_5 ->
                    new CHIENBINH5();
                case BossID.CHIEN_BINH_0 ->
                    new CHIENBINH0();
                case BossID.CHIEN_BINH_1 ->
                    new CHIENBINH1();
                case BossID.CHIEN_BINH_2 ->
                    new CHIENBINH2();
                case BossID.CHIEN_BINH_3 ->
                    new CHIENBINH3();
                case BossID.CHIEN_BINH_4 ->
                    new CHIENBINH4();
                case BossID.DOI_TRUONG_5 ->
                    new DOITRUONG5();
                case BossID.SO_4 ->
                    new SO4();
                case BossID.SO_3 ->
                    new SO3();
                case BossID.SO_2 ->
                    new SO2();
                case BossID.SO_1 ->
                    new SO1();
                case BossID.TIEU_DOI_TRUONG ->
                    new TDT();
                case BossID.SO_4_NM ->
                    new SO4_NM();
                case BossID.SO_3_NM ->
                    new SO3_NM();
                case BossID.SO_2_NM ->
                    new SO2_NM();
                case BossID.SO_1_NM ->
                    new SO1_NM();
                case BossID.TIEU_DOI_TRUONG_NM ->
                    new TDT_NM();
                case BossID.BUJIN ->
                    new BUJIN();
                case BossID.KOGU ->
                    new KOGU();
                case BossID.ZANGYA ->
                    new ZANGYA();
                case BossID.BIDO ->
                    new BIDO();
                case BossID.BOJACK ->
                    new BOJACK();
                case BossID.SUPER_BOJACK ->
                    new SUPER_BOJACK();
                case BossID.KUKU ->
                    new Kuku();
                case BossID.MAP_DAU_DINH ->
                    new MapDauDinh();
                case BossID.RAMBO ->
                    new Rambo();
                case BossID.TAU_PAY_PAY_DONG_NAM_KARIN ->
                    new TaoPaiPai();
                case BossID.DRABURA ->
                    new Drabura();
                case BossID.BUI_BUI ->
                    new BuiBui();
                case BossID.BUI_BUI_2 ->
                    new BuiBui2();
                case BossID.YA_CON ->
                    new Yacon();
                case BossID.DRABURA_2 ->
                    new Drabura2();
                case BossID.GOKU ->
                    new Goku();
                case BossID.CADIC ->
                    new Cadic();
                case BossID.MABU_12H ->
                    new Mabu();
                case BossID.DRABURA_3 ->
                    new Drabura3();
                case BossID.MABU ->
                    new Mabu2H();
                case BossID.SUPERBU ->
                    new SuperBu();
                case BossID.FIDE ->
                    new Fide();
                case BossID.DR_KORE ->
                    new DrKore();
                case BossID.ANDROID_19 ->
                    new Android19();
                case BossID.ANDROID_13 ->
                    new Android13();
                case BossID.ANDROID_14 ->
                    new Android14();
                case BossID.ANDROID_15 ->
                    new Android15();
                case BossID.PIC ->
                    new Pic();
                case BossID.POC ->
                    new Poc();
                case BossID.KING_KONG ->
                    new KingKong();
                case BossID.XEN_BO_HUNG ->
                    new XenBoHung();
                case BossID.SIEU_BO_HUNG ->
                    new SieuBoHung();
                case BossID.XEN_CON_1 ->
                    new XENCON1();
                case BossID.XEN_CON_2 ->
                    new XENCON2();
                case BossID.XEN_CON_3 ->
                    new XENCON3();
                case BossID.XEN_CON_4 ->
                    new XENCON4();
                case BossID.XEN_CON_5 ->
                    new XENCON5();
                case BossID.XEN_CON_6 ->
                    new XENCON6();
                case BossID.XEN_CON_7 ->
                    new XENCON7();
                case BossID.COOLER ->
                    new Cooler();
                case BossID.KHIDOT ->
                    new KhiDot();
                case BossID.NGUYETTHAN ->
                    new NguyetThan();
                case BossID.NHATTHAN ->
                    new NhatThan();
                case BossID.GOLDEN_FRIEZA ->
                    new GoldenFrieza();
                case BossID.DEATH_BEAM_1 ->
                    new DeathBeam1();
                case BossID.DEATH_BEAM_2 ->
                    new DeathBeam2();
                case BossID.DEATH_BEAM_3 ->
                    new DeathBeam3();
                case BossID.DEATH_BEAM_4 ->
                    new DeathBeam4();
                case BossID.DEATH_BEAM_5 ->
                    new DeathBeam5();
                case BossID.BIMA ->
                    new BiMa();
                case BossID.MATROI ->
                    new MaTroi();
                case BossID.DOI ->
                    new Doi();
                case BossID.ONG_GIA_NOEL ->
                    new OngGiaNoel();
                case BossID.SON_TINH ->
                    new SonTinh();
                case BossID.THUY_TINH ->
                    new ThuyTinh();
                case BossID.LAN_CON ->
                    new LanCon();
                case BossID.SOI_HEC_QUYN1 ->
                    new SoiHecQuyn();
                case BossID.O_DO1 ->
                    new Odo();
                case BossID.VIRUS ->
                    new Virus();
                case BossID.BLACK_GOKU ->
                    new BlackGoku();
                default ->
                    null;
            };
        } catch (Exception e) {
            Logger.error(e + "\n");
            return null;
        }
    }

    public Boss getBoss(int id) {
        try {
            Boss boss = this.bosses.get(id);
            if (boss != null) {
                return boss;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void showListBoss(Player player) {
        if (!player.isAdmin()) {
            return;
        }
        player.idMark.setMenuType(3);
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Boss");
            msg.writer().writeByte((int) bosses.stream().filter(boss -> !MapService.gI().isMapBossFinal(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapHuyDiet(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapYardart(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])).count());
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (MapService.gI().isMapBossFinal(boss.data[0].getMapJoin()[0]) || MapService.gI().isMapYardart(boss.data[0].getMapJoin()[0]) || MapService.gI().isMapHuyDiet(boss.data[0].getMapJoin()[0]) || MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0]) || MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])) {
                    continue;
                }
                msg.writer().writeInt(i);
                msg.writer().writeInt(i);
                msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                if (player.getSession().version >= 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                msg.writer().writeUTF(boss.data[0].getName());
                if (boss.zone != null) {
                    msg.writer().writeUTF(boss.bossStatus.toString());
                    msg.writer().writeUTF(boss.zone.map.mapName + "(" + boss.zone.map.mapId + ") khu " + boss.zone.zoneId + "");
                } else {
                    msg.writer().writeUTF(boss.bossStatus.toString());
                    msg.writer().writeUTF("=))");
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
            Logger.logException(BossManager.class, e);
        }
    }

    public Boss getBossById(int bossId) {
        return this.bosses.stream().filter(boss -> boss.id == bossId && !boss.isDie()).findFirst().orElse(null);
    }

    public boolean checkBosses(Zone zone, int BossID) {
        return this.bosses.stream().filter(boss -> boss.id == BossID && boss.zone != null && boss.zone.equals(zone) && !boss.isDie()).findFirst().orElse(null) != null;
    }

    public Player findBossClone(Player player) {
        return player.zone.getBosses().stream().filter(boss -> boss.id < -100_000_000 && !boss.isDie()).findFirst().orElse(null);
    }

    public Boss getBossById(int bossId, int mapId, int zoneId) {
        return this.bosses.stream().filter(boss -> boss.id == bossId && boss.zone != null && boss.zone.map.mapId == mapId && boss.zone.zoneId == zoneId && !boss.isDie()).findFirst().orElse(null);
    }

    @Override
    public void run() {
        while (!Maintenance.isRunning) {
            try {
                long st = System.currentTimeMillis();
                for (int i = this.bosses.size() - 1; i >= 0; i--) {
                    try {
                        this.bosses.get(i).update();
                    } catch (Exception e) {
                        Logger.logException(BossManager.class, e);
                    }
                }
                Functions.sleep(Math.max(150 - (System.currentTimeMillis() - st), 10));
            } catch (Exception e) {
                Logger.logException(BossManager.class, e);
            }
        }
    }

}

