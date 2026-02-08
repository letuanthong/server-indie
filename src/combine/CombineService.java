package combine;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */
import java.io.IOException;

import combine.list.CheTaoTrangBiThienSu;
import combine.list.CuongHoaLoSao;
import combine.list.DanhBongSaoPhaLe;
import consts.ConstNpc;
import item.Item;
import combine.list.EpSaoTrangBi;
import combine.list.LamPhepNhapDa;
import combine.list.NangCapBongTai;
import combine.list.NangCapSaoPhaLe;
import combine.list.NangCapVatPham;
import combine.list.NangChiSoBongTai;
import combine.list.NhapNgocRong;
import combine.list.PhaLeHoaTrangBi;
import combine.list.TaoDaHematite;
import item.Item.ItemOption;

import java.util.Random;

import player.Player;
import network.Message;
import npc.Npc;
import services.map.NpcManager;
import utils.Logger;

public class CombineService {

    public static final byte MAX_STAR_ITEM = 8;
    public static final byte MAX_LEVEL_ITEM = 8;

    private static final byte OPEN_TAB_COMBINE = 0;
    private static final byte REOPEN_TAB_COMBINE = 1;
    private static final byte COMBINE_SUCCESS = 2;
    private static final byte COMBINE_FAIL = 3;
    private static final byte COMBINE_DRAGON_BALL = 5;
    public static final byte OPEN_ITEM = 6;

    public static final int EP_SAO_TRANG_BI = 500;
    public static final int PHA_LE_HOA_TRANG_BI = 501;
    public static final int CHUYEN_HOA_TRANG_BI = 502;
    public static final int NANG_CAP_VAT_PHAM = 510;
    public static final int NANG_CAP_BONG_TAI = 511;
    public static final int LAM_PHEP_NHAP_DA = 512;
    public static final int NHAP_NGOC_RONG = 513;
    public static final int PHAN_RA_DO_THAN_LINH = 514;
    public static final int NANG_CAP_DO_TS = 515;
    public static final int NANG_CHI_SO_BONG_TAI = 517;
    public static final int NANG_CAP_SAO_PHA_LE = 5130;
    public static final int DANH_BONG_SAO_PHA_LE = 518;
    public static final int CUONG_HOA_LO_SAO = 519;
    public static final int TAO_DA_HEMATITE = 520;
    public static final int CHUYEN_HOA_DUNG_VANG = 521;
    public static final int NANG_KICH_HOAT_01 = 522;

    private static CombineService instance;
    public static int GIAM_DINH_SACH;
    public static int TAY_SACH;
    public static int NANG_CAP_SACH_TUYET_KY;
    public static int HOI_PHUC_SACH;
    public static int PHAN_RA_SACH;

    public final Npc baHatMit;
    public final Npc whis;

    private CombineService() {
        this.baHatMit = NpcManager.getNpc(ConstNpc.BA_HAT_MIT);
        this.whis = NpcManager.getNpc(ConstNpc.WHIS);
    }

    public static CombineService gI() {
        if (instance == null) {
            instance = new CombineService();
        }
        return instance;
    }

    /**
     * Hiển thị thông tin đập đồ
     *
     * @param player
     * @param index
     */
    public void showInfoCombine(Player player, int[] index) {
        if (player.combineNew == null) {
            return;
        }
        player.combineNew.clearItemCombine();
        if (index.length > 0) {
            for (int i = 0; i < index.length; i++) {
                player.combineNew.itemsCombine.add(player.inventory.itemsBag.get(index[i]));
            }
        }
        switch (player.combineNew.typeCombine) {
            case EP_SAO_TRANG_BI -> EpSaoTrangBi.showInfoCombine(player);
            case PHA_LE_HOA_TRANG_BI -> PhaLeHoaTrangBi.showInfoCombine(player);
            case NHAP_NGOC_RONG -> NhapNgocRong.showInfoCombine(player);
            case NANG_CAP_VAT_PHAM -> NangCapVatPham.showInfoCombine(player);
            case NANG_CAP_BONG_TAI -> NangCapBongTai.showInfoCombine(player);
            case NANG_CHI_SO_BONG_TAI -> NangChiSoBongTai.showInfoCombine(player);
            case NANG_CAP_DO_TS -> CheTaoTrangBiThienSu.showInfoCombine(player);
            case NANG_CAP_SAO_PHA_LE -> NangCapSaoPhaLe.showInfoCombine(player);
            case DANH_BONG_SAO_PHA_LE -> DanhBongSaoPhaLe.showInfoCombine(player);
            case CUONG_HOA_LO_SAO -> CuongHoaLoSao.showInfoCombine(player);
            case TAO_DA_HEMATITE -> TaoDaHematite.showInfoCombine(player);
            case LAM_PHEP_NHAP_DA -> LamPhepNhapDa.showInfoCombine(player);
        }
    }

    /**
     * Bắt đầu đập đồ - điều hướng từng loại đập đồ
     *
     * @param player
     */
    public void startCombine(Player player) {
        switch (player.combineNew.typeCombine) {
            case EP_SAO_TRANG_BI -> EpSaoTrangBi.epSaoTrangBi(player);
            case PHA_LE_HOA_TRANG_BI -> PhaLeHoaTrangBi.phaLeHoa(player);
            case NHAP_NGOC_RONG -> NhapNgocRong.nhapNgocRong(player);
            case NANG_CAP_VAT_PHAM -> NangCapVatPham.nangCapVatPham(player);
            case NANG_CAP_BONG_TAI -> NangCapBongTai.nangCapBongTai(player);
            case NANG_CHI_SO_BONG_TAI -> NangChiSoBongTai.nangChiSoBongTai(player);
            case NANG_CAP_DO_TS -> CheTaoTrangBiThienSu.CheTaoTS(player);
            case NANG_CAP_SAO_PHA_LE -> NangCapSaoPhaLe.nangCapSaoPhaLe(player);
            case DANH_BONG_SAO_PHA_LE -> DanhBongSaoPhaLe.danhBongSaoPhaLe(player);
            case CUONG_HOA_LO_SAO -> CuongHoaLoSao.cuongHoaLoSao(player);
            case TAO_DA_HEMATITE -> TaoDaHematite.taoDaHematite(player);
            case LAM_PHEP_NHAP_DA -> LamPhepNhapDa.lamphepnhapda(player);
        }

        player.idMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    public void startCombineVip(Player player, int n) {
        switch (player.combineNew.typeCombine) {
            case PHA_LE_HOA_TRANG_BI -> PhaLeHoaTrangBi.phaLeHoa(player, n);
        }

        player.idMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    /**
     * Mở tab đập đồ
     *
     * @param player
     * @param type kiểu đập đồ
     */
    public void openTabCombine(Player player, int type) {
        player.combineNew.setTypeCombine(type);
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_TAB_COMBINE);
            msg.writer().writeUTF(getTextInfoTabCombine(type));
            msg.writer().writeUTF(getTextTopTabCombine(type));
            if (player.idMark.getNpcChose() != null) {
                msg.writer().writeShort(player.idMark.getNpcChose().tempId);
            }
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi mở tab combine");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    /**
     * Hiệu ứng mở item
     *
     * @param player
     * @param icon1
     * @param icon2
     */
    public void sendEffectOpenItem(Player player, short icon1, short icon2) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_ITEM);
            msg.writer().writeShort(icon1);
            msg.writer().writeShort(icon2);
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng mở item");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public void sendEffectCombineItem(Player player, byte type, short icon1, short icon2) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(type);
            switch (type) {
                case 0 -> {
                    msg.writer().writeUTF("");
                    msg.writer().writeUTF("");
                }
                case 1 -> {
                    msg.writer().writeByte(0);
                    msg.writer().writeByte(-1);
                }
                case 2, 3 -> // success 0 eff 0
                {
                }
                case 4 -> // success 0 eff 1
                    msg.writer().writeShort(icon1);
                case 5 -> // success 0 eff 2
                    msg.writer().writeShort(icon1);
                case 6 -> {
                    // success 0 eff 3
                    msg.writer().writeShort(icon1);
                    msg.writer().writeShort(icon2);
                }
                case 7 -> // success 0 eff 4
                    msg.writer().writeShort(icon1);
                case 8 -> {
                }
            }
            // success 0 eff 0
            // success 1 eff 0
            // success 1 eff 4
            // Lam do ts
            msg.writer().writeShort(-1); // id npc
            msg.writer().writeShort(-1); // x
            msg.writer().writeShort(-1); // y
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng combine item");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    /**
     * Hiệu ứng đập đồ thành công
     *
     * @param player
     */
    public void sendEffectSuccessCombine(Player player) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_SUCCESS);
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng đập đồ thành công");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    /**
     * Hiệu ứng đập đồ thất bại
     *
     * @param player
     */
    public void sendEffectFailCombine(Player player) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_FAIL);
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng đập đồ thất bại");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    /**
     * Gửi lại danh sách đồ trong tab combine
     *
     * @param player
     */
    public void reOpenItemCombine(Player player) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(REOPEN_TAB_COMBINE);
            msg.writer().writeByte(player.combineNew.itemsCombine.size());
            for (Item it : player.combineNew.itemsCombine) {
                for (int j = 0; j < player.inventory.itemsBag.size(); j++) {
                    if (it == player.inventory.itemsBag.get(j)) {
                        msg.writer().writeByte(j);
                    }
                }
            }
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng mở item");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public int getTempIdItemC0(int gender, int type) {
        Random rand = new Random();  // Khởi tạo đối tượng Random

        if (type == 4) {
            int[] TrangBi = {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281};
            return TrangBi[rand.nextInt(TrangBi.length)];
        }
        switch (gender) {
            case 0 -> {
                switch (type) {
                    case 0 -> {
                        int[] TrangBi = {0, 3, 33, 34, 136, 137, 138, 139, 230, 231, 232, 233};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 1 -> {
                        int[] TrangBi = {6, 9, 35, 36, 140, 141, 142, 143, 242, 243, 244, 245};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 2 -> {
                        int[] TrangBi = {21, 24, 37, 38, 144, 145, 146, 147, 254, 255, 256, 257};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 3 -> {
                        int[] TrangBi = {27, 30, 39, 40, 148, 149, 150, 151, 266, 267, 268, 269};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                }
            }

            case 1 -> {
                switch (type) {
                    case 0 -> {
                        int[] TrangBi = {1, 4, 41, 42, 152, 153, 154, 155, 234, 235, 236, 237};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 1 -> {
                        int[] TrangBi = {7, 10, 43, 44, 156, 157, 158, 159, 246, 247, 248, 249};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 2 -> {
                        int[] TrangBi = {22, 25, 45, 46, 160, 161, 162, 163, 258, 259, 260, 261};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 3 -> {
                        int[] TrangBi = {28, 31, 47, 48, 164, 165, 166, 167, 270, 271, 272, 273};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                }
            }

            case 2 -> {
                switch (type) {
                    case 0 -> {
                        int[] TrangBi = {2, 5, 49, 50, 168, 169, 170, 171, 238, 239, 240, 241};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 1 -> {
                        int[] TrangBi = {8, 11, 51, 52, 172, 173, 174, 175, 250, 251, 252, 253};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 2 -> {
                        int[] TrangBi = {23, 26, 53, 54, 176, 177, 178, 179, 262, 263, 264, 265};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                    case 3 -> {
                        int[] TrangBi = {29, 32, 55, 56, 180, 181, 182, 183, 274, 275, 276, 277};
                        return TrangBi[rand.nextInt(TrangBi.length)];
                    }
                }
            }

        }
        return -1;
    }

    public int getTempIdItemC1(int gender, int type) {
        if (type == 4) {
            return 561;
        }
        switch (gender) {
            case 0 -> {
                switch (type) {
                    case 0 -> {
                        return 555;
                    }
                    case 1 -> {
                        return 556;
                    }
                    case 2 -> {
                        return 562;
                    }
                    case 3 -> {
                        return 563;
                    }
                }
            }

            case 1 -> {
                switch (type) {
                    case 0 -> {
                        return 557;
                    }
                    case 1 -> {
                        return 558;
                    }
                    case 2 -> {
                        return 564;
                    }
                    case 3 -> {
                        return 565;
                    }
                }
            }

            case 2 -> {
                switch (type) {
                    case 0 -> {
                        return 559;
                    }
                    case 1 -> {
                        return 560;
                    }
                    case 2 -> {
                        return 566;
                    }
                    case 3 -> {
                        return 567;
                    }
                }
            }

        }
        return -1;
    }

    public int getTempIdItemC2(int gender, int type) {
        if (type == 4) {
            return 656;
        }
        switch (gender) {
            case 0 -> {
                switch (type) {
                    case 0 -> {
                        return 650;
                    }
                    case 1 -> {
                        return 651;
                    }
                    case 2 -> {
                        return 657;
                    }
                    case 3 -> {
                        return 658;
                    }
                }
            }

            case 1 -> {
                switch (type) {
                    case 0 -> {
                        return 652;
                    }
                    case 1 -> {
                        return 653;
                    }
                    case 2 -> {
                        return 659;
                    }
                    case 3 -> {
                        return 660;
                    }
                }
            }

            case 2 -> {
                switch (type) {
                    case 0 -> {
                        return 654;
                    }
                    case 1 -> {
                        return 655;
                    }
                    case 2 -> {
                        return 661;
                    }
                    case 3 -> {
                        return 662;
                    }
                }
            }

        }
        return -1;
    }

    //Trả về tên đồ c0
    public String getNameItemC0(int gender, int type) {
        if (type == 4) {
            return "Rada";
        }
        switch (gender) {
            case 0 -> {
                switch (type) {
                    case 0 -> {
                        return "Áo";
                    }
                    case 1 -> {
                        return "Quần";
                    }
                    case 2 -> {
                        return "Găng";
                    }
                    case 3 -> {
                        return "Giầy";
                    }
                }
            }

            case 1 -> {
                switch (type) {
                    case 0 -> {
                        return "Áo";
                    }
                    case 1 -> {
                        return "Quần";
                    }
                    case 2 -> {
                        return "Găng";
                    }
                    case 3 -> {
                        return "Giầy";
                    }
                }
            }

            case 2 -> {
                switch (type) {
                    case 0 -> {
                        return "Áo";
                    }
                    case 1 -> {
                        return "Quần";
                    }
                    case 2 -> {
                        return "Găng";
                    }
                    case 3 -> {
                        return "Giầy";
                    }
                }
            }

        }
        return "";
    }

    /**
     * Hiệu ứng ghép ngọc rồng
     *
     * @param player
     * @param icon
     */
    public void sendEffectCombineDB(Player player, short icon) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_DRAGON_BALL);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.logException(CombineService.class, e, "Có lỗi khi gửi hiệu ứng combine item");
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public boolean CheckSlot(Item trangBi, int starEmpty) {
        if (starEmpty < 8) {
            // Nếu starEmpty nhỏ hơn 8, không cần kiểm tra cường hóa, trả về true ngay
            return true;
        }

        // Nếu starEmpty >= 8, kiểm tra cường hóa
        for (ItemOption io : trangBi.itemOptions) {
            if (starEmpty == 8 && io.optionTemplate.id == 228) {
                return io.param >= 8;
            } else if (starEmpty == 9 && io.optionTemplate.id == 228) {
                return io.param >= 9;
            }
        }

        return false;
   
    } public boolean isAngelClothes(int id) {
        return id >= 1048 && id <= 1062;
    }

    public boolean isDestroyClothes(int id) {
        return id >= 650 && id <= 662;
    }
  public int getRatioLuckyStone(int id) {
        switch (id) {
            case 1079 -> {
                return 10;
            }
            case 1080 -> {
                return 20;
            }
            case 1081 -> {
                return 30;
            }
            case 1082 -> {
                return 40;
            }
            case 1083 -> {
                return 50;
            }
        }
        return 0;
    }
    // thonk
    // private String getTypeTrangBi(int type) {
    //     switch (type) {
    //         case 0 -> {
    //             return "Áo";
    //         }
    //         case 1 -> {
    //             return "Quần";
    //         }
    //         case 2 -> {
    //             return "Găng";
    //         }
    //         case 3 -> {
    //             return "Giày";
    //         }
    //         case 4 -> {
    //             return "Nhẫn";
    //         }
    //     }
    //     return "";
    // }

    public boolean isManhTrangBi(Item it) {
        switch (it.template.id) {
            case 1066, 1067, 1068, 1069, 1070 -> {
                return true;
            }
        }
        return false;
    }

    public boolean isCraftingRecipe(int id) {
        switch (id) {
            case 1071, 1072, 1073, 1084, 1085, 1086 -> {
                return true;
            }
        }
        return false;
    }

    public int getRatioCraftingRecipe(int id) {
        switch (id) {
            case 1071 -> {
                return 0;
            }
            case 1072 -> {
                return 0;
            }
            case 1073 -> {
                return 0;
            }
            case 1084 -> {
                return 10;
            }
            case 1085 -> {
                return 10;
            }
            case 1086 -> {
                return 10;
            }
        }
        return 0;
    }

    public boolean isUpgradeStone(int id) {
        switch (id) {
            case 1074, 1075, 1076, 1077, 1078 -> {
                return true;
            }
        }
        return false;
    }

    public int getRatioUpgradeStone(int id) {
        switch (id) {
            case 1074 -> {
                return 10;
            }
            case 1075 -> {
                return 20;
            }
            case 1076 -> {
                return 30;
            }
            case 1077 -> {
                return 40;
            }
            case 1078 -> {
                return 50;
            }
        }
        return 0;
    }

    public boolean isLuckyStone(int id) {
        switch (id) {
            case 1079, 1080, 1081, 1082, 1083 -> {
                return true;
            }
        }
        return false;
    }

    private String getTextTopTabCombine(int type) {
        return switch (type) {
            case EP_SAO_TRANG_BI -> "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case PHA_LE_HOA_TRANG_BI -> "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case NHAP_NGOC_RONG -> "Ta sẽ phù phép\ncho 7 viên Ngọc Rồng\nthành 1 viên Ngọc Rồng cấp cao";
            case NANG_CAP_VAT_PHAM -> "Ta sẽ phù phép cho trang bị của ngươi trở lên mạnh mẽ";
            case PHAN_RA_DO_THAN_LINH -> "Ta sẽ phân rã \n  trang bị của người thành điểm!";
            case NANG_CAP_DO_TS -> "Ta sẽ nâng cấp \n  trang bị của người thành\n đồ thiên sứ!";
            case NANG_CAP_BONG_TAI -> "Ta sẽ phù phép\ncho bông tai Porata của ngươi\nthành cấp 2";
            case NANG_CHI_SO_BONG_TAI -> "Ta sẽ phù phép\ncho bông tai Porata cấp 2 của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case NANG_CAP_SAO_PHA_LE -> "Ta sẽ phù phép\nnâng cấp Sao Pha Lê\nthành cấp 2";
            case DANH_BONG_SAO_PHA_LE -> "Đánh bóng\nSao pha lê cấp 2";
            case CUONG_HOA_LO_SAO -> "Cường hóa\nÔ Sao Pha lê";
            case TAO_DA_HEMATITE -> """
                Ta sẽ phù phép\ntạo đá Hematite
                """;
            case LAM_PHEP_NHAP_DA -> """
            Ta sẽ phù phép\ntạo đá nâng cấp
                """;
            default -> "";
        }; // START _ NEW PHA LÊ HÓA //
    }

    private String getTextInfoTabCombine(int type) {
        return switch (type) {
            case EP_SAO_TRANG_BI -> """
            Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\n
            Sau đó chọn 'Nâng cấp'
                """;
            case PHA_LE_HOA_TRANG_BI -> "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case NHAP_NGOC_RONG -> "Vào hành trang\nChọn 7 viên ngọc cùng sao\nSau đó chọn 'Làm phép'";
            case NANG_CAP_VAT_PHAM -> """
            Vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để nâng cấp\n
            Sau đó chọn 'Nâng cấp
                """;
            case PHAN_RA_DO_THAN_LINH -> """
            Vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để phân rã\n
            Sau đó chọn 'Phân Rã'                        
                    """;
            case NANG_CAP_DO_TS -> """
            Vào hành trang\nChọn 1 công thức or công thức Vip\nkèm 1 đá nâng, 1 đá may mắn\n và 999 mảnh thiên sứ\n
            Ta sẽ cho ra đồ thiên sứ từ 0-15% chỉ số\n
            Sau đó chọn 'Nâng Cấp'
                """;
            case NANG_CAP_BONG_TAI -> """
            Vào hành trang\nChọn bông tai Porata\nChọn mảnh bông tai để nâng cấp, Số lượng 9999 cái
                """;
            case NANG_CHI_SO_BONG_TAI -> """
            Vào hành trang\nChọn bông tai Porata cấp 2\nChọn đá xanh lam để nâng cấp
            \nSau đó chọn 'Nâng cấp chỉ số'
                    """;
            case NANG_CAP_SAO_PHA_LE -> "Vào hành trang\nChọn đá Hematite\n Chọn loại sao pha lê (cấp 1)\nSau đó chọn 'Nâng cấp'";
            case DANH_BONG_SAO_PHA_LE -> "Vào hành trang\nChọn loại sao pha lê cấp 2 có từ 2 viên trở\nlên\nChọn 1 loại đá mài\nSau đó chọn 'Đánh bóng'";
            case CUONG_HOA_LO_SAO -> """
            Vào hành trang\n
            Chọn trang bị có Ô sao thứ 8 trở lên chưa\n
            cường hóa\n
            Chọn đá Hematite\n
            Chọn dùi đục\n
            Sau đó chọn 'Cường hóa'
                """;
            case TAO_DA_HEMATITE -> """
            Vào hành trang\n
            Chọn 5 sao pha lê cấp 2 cùng màu\n
            Chọn 'Tạo đá Hematite'
                """;
            case LAM_PHEP_NHAP_DA -> """
            Vào hành trang\n
            Chọn 10 mảnh đá vụn và 1 bình nước phép\n
            Chọn Nâng Cấp
                """;
            default -> "";
        }; // START _ NEW PHA LÊ HÓA //
        // END _ NEW PHA LÊ HÓA //
    }

}

