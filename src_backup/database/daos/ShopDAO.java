package database.daos;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import item.Item;
import shop.ItemShop;
import shop.Shop;
import shop.TabShop;
import services.ItemService;
import utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// thonk
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.JSONValue;

public class ShopDAO {

    public static List<Shop> getShops(Connection con) {
        List<Shop> list = new ArrayList<>();
        String query = "select * from shop order by npc_id asc";
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Shop shop = new Shop();
                shop.id = rs.getInt("id");
                shop.npcId = rs.getByte("npc_id");
                shop.tagName = rs.getString("tag_name");
                shop.typeShop = rs.getByte("type_shop");
                loadShopTab(con, shop);
                list.add(shop);
            }
        } catch (SQLException e) {
            Logger.logException(ShopDAO.class, e);
        }
        return list;
    }

    private static void loadShopTab(Connection con, Shop shop) {
        String query = "select * from tab_shop where shop_id = ? order by id";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, shop.id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TabShop tab = new TabShop();
                    tab.shop = shop;
                    tab.id = rs.getInt("id");
                    tab.name = rs.getString("name").replaceAll("<>", "\n");
                    loadItemShop(con, tab);
                    shop.tabShops.add(tab);
                }
            }
        } catch (SQLException e) {
            Logger.logException(ShopDAO.class, e);
        }
    }

    private static void loadItemShop(Connection con, TabShop tabShop) {
        String query = "select * from item_shop where is_sell = 1 and tab_id = ? order by create_time desc";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            int id = tabShop.id;
            if (id >= 41 && id <= 43) { //10,11,12
                id -= 31;
            }
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemShop itemShop = new ItemShop();
                    itemShop.tabShop = tabShop;
                    itemShop.id = rs.getInt("id");
                    itemShop.temp = ItemService.gI().getTemplate(rs.getShort("temp_id"));
                    itemShop.isNew = rs.getBoolean("is_new");
                    itemShop.cost = rs.getInt("cost");
                    itemShop.iconSpec = rs.getInt("icon_spec");
                    itemShop.typeSell = rs.getByte("type_sell");
                    loadItemShopOption(con, itemShop);
                    tabShop.itemShops.add(itemShop);
                }
            }
        } catch (SQLException e) {
            Logger.logException(ShopDAO.class, e);
        }
    }

    private static void loadItemShopOption(Connection con, ItemShop itemShop) {
        String query = "select * from item_shop_option where item_shop_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, itemShop.id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    itemShop.options.add(new Item.ItemOption(rs.getInt("option_id"), rs.getInt("param")));
                }
            }
        } catch (SQLException e) {
            Logger.logException(ShopDAO.class, e);
        }
    }
}

