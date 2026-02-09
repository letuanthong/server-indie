package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import utils.Logger;

public class AlyraManager {

    private static String DRIVER;
    // private static String URL; thonk
    private static String DB_HOST;
    private static String DB_PORT;
    private static String DB_NAME;
    private static String DB_NAME_DATA;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static int MIN_CONN;
    private static int MAX_CONN;
    private static long MAX_LIFE_TIME;
    public static boolean LOG_QUERY;
    private static final HikariDataSource ds;
    private static final HikariDataSource ds_data;


    static {
        loadProperties();
        // Validate required config trước khi tạo connection pool
        if (DRIVER == null || DB_HOST == null || DB_PORT == null || DB_USER == null || DB_NAME == null || DB_NAME_DATA == null) {
            Logger.log(Logger.RED, "[ERROR] Thiếu config database! Kiểm tra config/server.properties\n");
            Logger.log(Logger.RED, String.format("  driver=%s host=%s port=%s user=%s name=%s name_data=%s\n",
                    DRIVER, DB_HOST, DB_PORT, DB_USER, DB_NAME, DB_NAME_DATA));
            throw new ExceptionInInitializerError("Thiếu config database trong config/server.properties");
        }
        if (DB_PASSWORD == null) {
            DB_PASSWORD = "";
        }
        String dbName = AlyraManager.DB_NAME;
        String dbNameData = AlyraManager.DB_NAME_DATA;
        ds = new HikariDataSource(createConfig("User Management", dbName));
        ds_data = new HikariDataSource(createConfig("Game Assets", dbNameData));
    }

    public static Connection getConnection() throws SQLException {
        return AlyraManager.ds.getConnection();
    }

    public static Connection getConnection_Data() throws SQLException {
        return AlyraManager.ds_data.getConnection();
    }

    public static void close() {
        AlyraManager.ds.close();
    }

    public static void close_data() {
        AlyraManager.ds_data.close();
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/server.properties"));
            Object value;
            if ((value = properties.get("database.driver")) != null) {
                AlyraManager.DRIVER = String.valueOf(value);
            }
            if ((value = properties.get("database.host")) != null) {
                AlyraManager.DB_HOST = String.valueOf(value);
            }
            if ((value = properties.get("database.port")) != null) {
                AlyraManager.DB_PORT = String.valueOf(value);
            }
            if ((value = properties.get("database.name")) != null) {
                AlyraManager.DB_NAME = String.valueOf(value);
            }
            if ((value = properties.get("database.name_data")) != null) {
                AlyraManager.DB_NAME_DATA = String.valueOf(value);
            }
            if ((value = properties.get("database.user")) != null) {
                AlyraManager.DB_USER = String.valueOf(value);
            }
            if ((value = properties.get("database.pass")) != null) {
                AlyraManager.DB_PASSWORD = String.valueOf(value);
            }
            if ((value = properties.get("database.min")) != null) {
                AlyraManager.MIN_CONN = Integer.parseInt(String.valueOf(value));
            }
            if ((value = properties.get("database.max")) != null) {
                AlyraManager.MAX_CONN = Integer.parseInt(String.valueOf(value));
            }
            if ((value = properties.get("database.lifetime")) != null) {
                AlyraManager.MAX_LIFE_TIME = Integer.parseInt(String.valueOf(value));
            }
            if ((value = properties.get("database.log")) != null) {
                AlyraManager.LOG_QUERY = Boolean.parseBoolean(String.valueOf(value));
            }
            Logger.log(Logger.GREEN, "[LOADED] file properites!\n");
        } catch (final IOException | NumberFormatException ex) {
            Logger.log(Logger.RED, "Không thể load file properites!\n");
        } finally {
            properties.clear();
        }
    }

    public static AlyraResultSet executeQuery(final String query) throws Exception {
        try {
            Connection con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (AlyraManager.LOG_QUERY) {
                        Logger.log(Logger.GREEN, "Thực thi thành công câu lệnh: " + ps.toString() + "\n");
                    }
                    return new ResultSetImpl(rs);
                }
            } finally {
                con.close();
            }
        } catch (Exception ex) {
            Logger.log(Logger.RED, "Có lỗi xảy ra khi thực thi câu lệnh: " + query + "\n");
            throw ex;
        }
    }

    public static AlyraResultSet executeQuery(final String query, final Object... objs) throws Exception {
        try (final Connection con = getConnection(); final PreparedStatement ps = con.prepareStatement(query)) {
            for (int i = 0; i < objs.length; ++i) {
                ps.setObject(i + 1, objs[i]);
            }
            if (AlyraManager.LOG_QUERY) {
                Logger.log(Logger.GREEN, "Thực thi thành công câu lệnh: " + ps.toString() + "\n");
            }
            return new ResultSetImpl(ps.executeQuery());
        } catch (final Exception ex) {
            Logger.log(Logger.RED, "Có lỗi xảy ra khi thực thi câu lệnh: " + query + "\n");
            throw ex;
        }
    }

    public static int executeUpdate(final String query) throws Exception {
        int rowUpdated = -1;
        try (final Connection con = getConnection(); final PreparedStatement ps = con.prepareStatement(query)) {
            if (AlyraManager.LOG_QUERY) {
                Logger.log(Logger.GREEN, "Thực thi thành công câu lệnh: " + ps.toString() + "\n");
            }
            rowUpdated = ps.executeUpdate();
        } catch (final Exception e) {
            Logger.log(Logger.RED, "Có lỗi xảy ra khi thực thi câu lệnh: " + query + "\n");
            throw e;
        }
        return rowUpdated;
    }

    public static int executeUpdate(String query, final Object... objs) throws Exception {
        if (query.indexOf("insert") == 0 && query.lastIndexOf("()") == query.length() - 2) {
            final StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < objs.length; ++i) {
                sb.append("?");
                if (i < objs.length - 1) {
                    sb.append(",");
                } else {
                    sb.append(")");
                }
            }
            query = query.replace("()", sb.toString());
        }
        try (final Connection con = getConnection(); final PreparedStatement ps = con.prepareStatement(query)) {
            for (int j = 0; j < objs.length; ++j) {
                ps.setObject(j + 1, objs[j]);
            }
            if (AlyraManager.LOG_QUERY) {
                Logger.log(Logger.GREEN, "Thực thi thành công câu lệnh: " + ps.toString() + "\n");
            }
            return ps.executeUpdate();
        } catch (final Exception ex) {
            Logger.log(Logger.RED, "Có lỗi xảy ra khi thực thi câu lệnh: " + query + "\n");
            throw ex;
        }
    }

    private static HikariConfig createConfig(String poolName, String databaseName) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=yes&characterEncoding=UTF-8",
                DB_HOST, DB_PORT, databaseName));
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setMinimumIdle(MIN_CONN);
        config.setMaximumPoolSize(MAX_CONN);
        config.setMaxLifetime(MAX_LIFE_TIME);
        config.setPoolName(poolName);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "true");
        return config;
    }
}

