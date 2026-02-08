package database.daos;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import data.AlyraManager;

import org.json.simple.JSONArray;

import player.Player;
import utils.Logger;

public class TraningDAO {

    @SuppressWarnings("unchecked")
    public static void updatePlayer(Player player) {
        if (player != null && player.idMark.isLoadedAllDataPlayer()) {
            try {
                JSONArray dataArray = new JSONArray();
                dataArray.add(player.levelLuyenTap);
                dataArray.add(player.dangKyTapTuDong);
                dataArray.add(player.mapIdDangTapTuDong);
                dataArray.add(player.tnsmLuyenTap);
                if (player.isOffline) {
                    dataArray.add(player.lastTimeOffline);
                } else {
                    dataArray.add(System.currentTimeMillis());
                }
                dataArray.add(player.traning.getTop());
                dataArray.add(player.traning.getTime());
                dataArray.add(player.traning.getLastTime());
                dataArray.add(player.traning.getLastTop());
                dataArray.add(player.traning.getLastRewardTime());

                String dataLuyenTap = dataArray.toJSONString();
                dataArray.clear();

                String query = "UPDATE player SET data_luyentap = ? WHERE id = ?";
                AlyraManager.executeUpdate(query, dataLuyenTap, player.id);
            } catch (Exception e) {
                Logger.logException(TraningDAO.class, e);
            }
        }
    }

}

