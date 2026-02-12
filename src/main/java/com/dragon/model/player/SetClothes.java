package com.dragon.model.player;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import com.dragon.model.item.Item;

public class SetClothes {

    private Player player;

    public SetClothes(Player player) {
        this.player = player;
    }

    public byte songoku;
    public byte thienXinHang;
    public byte kirin;
    public byte kaioken;
    public byte thanVuTruKaio;

    public byte ocTieu;
    public byte pikkoroDaimao;
    public byte picolo;
    public byte lienHoan;
    public byte nail;

    public byte kakarot;
    public byte cadic;
    public byte nappa;
    public byte giamSatThuong;
    public byte cadicM;

    public byte worldcup;
    public byte setDHD;

    public boolean godClothes;
    public int ctHaiTac = -1;

    public void setup() {
        setDefault();
        setupSKT();

        this.godClothes = true;  
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {             
                if (item.template.id > 567 || item.template.id < 555) {
                    this.godClothes = false;  
                    break;
                }
            } else {           
                this.godClothes = false;               
            }
        }
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618, 619, 620, 621, 622, 623, 624, 626, 627 -> this.ctHaiTac = ct.template.id;
//                case 1087:
//                case 1088:
//                case 1089:
//                case 1090:
//                case 1091:
//                    this.ctDietQuy = ct.template.id;
//                    break;
//                case 1208:
//                case 1209:
//                case 1210:
//                    this.ctBunmaTocMau = ct.template.id;
//                    break;
//                case 1234:
//                case 1235:
//                case 1236:
//                    this.ctTiecbaiBien = ct.template.id;
//                    break;
//                case 1087:
//                case 1088:
//                case 1089:
//                case 1090:
//                case 1091:
//                    this.ctDietQuy = ct.template.id;
//                    break;
//                case 1208:
//                case 1209:
//                case 1210:
//                    this.ctBunmaTocMau = ct.template.id;
//                    break;
//                case 1234:
//                case 1235:
//                case 1236:
//                    this.ctTiecbaiBien = ct.template.id;
//                    break;

            }
        }

    }

    private void setupSKT() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);

            // Loại bỏ điều kiện kiểm tra isNotNullItem
            boolean isActSet = false;
            for (Item.ItemOption io : item.itemOptions) {
                switch (io.optionTemplate.id) {
                    case 129, 141 -> {
                        isActSet = true;
                        songoku++;
                    }
                    case 127, 139 -> {
                        isActSet = true;
                        thienXinHang++;
                    }
                    case 128, 140 -> {
                        isActSet = true;
                        kirin++;
                    }
                    case 131, 143 -> {
                        isActSet = true;
                        ocTieu++;
                    }
                    case 132, 144 -> {
                        isActSet = true;
                        pikkoroDaimao++;
                    }
                    case 130, 142 -> {
                        isActSet = true;
                        picolo++;
                    }
                    case 135, 138 -> {
                        isActSet = true;
                        nappa++;
                    }
                    case 133, 136 -> {
                        isActSet = true;
                        kakarot++;
                    }
                    case 134, 137 -> {
                        isActSet = true;
                        cadic++;
                    }
                    case 250, 253 -> {
                        isActSet = true;
                        kaioken++;
                    }
                    case 251, 254 -> {
                        isActSet = true;
                        lienHoan++;
                    }
                    case 252, 255 -> {
                        isActSet = true;
                        giamSatThuong++;
                    }
                    case 21 -> {
                        if (io.param == 80) {
                            setDHD++;
                        }
                    }
                    case 245, 246, 247, 248 -> {
                        isActSet = true;
                        thanVuTruKaio++;
                    }
                    case 237, 238, 239, 240 -> {
                        isActSet = true;
                        nail++;
                    }
                    case 241, 242, 243, 244 -> {
                        isActSet = true;
                        cadicM++;
                    }
                }

                if (isActSet) {
                    break;
                }
            }
        }
    }
    
    

//    private void setupSKHNew() {
//        for (int i = 0; i < 5; i++) {
//            Item item = this.player.inventory.itemsBody.get(i);
//            if (item.isNotNullItem()) {
//                boolean isActSet = false;
//                for (Item.ItemOption io : item.itemOptions) {
//                    switch (io.optionTemplate.id) {
//                        case 245:
//                        case 246:
//                        case 247:
//                        case 248:
//                            isActSet = true;
//                            thanVuTruKaio++;
//                            break;
//                        case 237:
//                        case 238:
//                        case 239:
//                        case 240:
//                            isActSet = true;
//                            nail++;
//                            break;
//                        case 241:
//                        case 242:
//                        case 243:
//                        case 244:
//                            isActSet = true;
//                            cadicM++;
//                            break;
//                    }
//
//                    if (isActSet) {
//                        break;
//                    }
//                }
//            } else {
//                break;
//            }
//        }
//        for (int i = 0; i < 5; i++) {
//            Item item = this.player.inventory.itemsBody.get(i);           
//            if (item.isNotNullItem()) {
//                boolean isActSet = false;
//                for (Item.ItemOption io : item.itemOptions) {
//                    switch (io.optionTemplate.id) {
//                        case 245:
//                        case 246:
//                        case 247:
//                        case 248:
//                            isActSet = true;
//                            thanVuTruKaio++;
//                            break;
//                        case 237:
//                        case 238:
//                        case 239:
//                        case 240:
//                            isActSet = true;
//                            nail++;
//                            break;
//                        case 241:
//                        case 242:
//                        case 243:
//                        case 244:
//                            isActSet = true;
//                            cadicM++;
//                            break;
//                    }
//
//                    if (isActSet) {
//                        break;
//                    }
//                }
//            }
//        }
//
//    }
    private void setDefault() {
        this.songoku = 0;
        this.thienXinHang = 0;
        this.kirin = 0;
        this.kaioken = 0;
        this.ocTieu = 0;
        this.pikkoroDaimao = 0;
        this.picolo = 0;
        this.lienHoan = 0;
        this.kakarot = 0;
        this.cadic = 0;
        this.nappa = 0;
        this.giamSatThuong = 0;
        this.thanVuTruKaio = 0;
        this.nail = 0;
        this.cadicM = 0;
        this.setDHD = 0;
        this.worldcup = 0;
        this.godClothes = false;
        this.ctHaiTac = -1;
    }

    public boolean checkSetGod() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id < 555 || item.template.id > 567) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean checkSetDes() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id < 650 || item.template.id > 662) {

                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void dispose() {
        this.player = null;
    }
}

