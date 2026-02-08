# server-indie

Game server NRO - Java 21, MySQL, NIO Socket.

---

## Quick Deploy (Debian 12)

**Trên server chỉ cần 3 bước:**

```bash
# 1. Clone repo
git clone https://github.com/letuanthong/server-indie.git
cd server-indie

# 2. Cấu hình
cp .env.example .env
nano .env                           # Sửa password DB, RAM, ...

# 3. Chạy setup (tự động cài Java, MariaDB, build, tạo service)
sudo bash deploy/setup.sh
```

**Xong.** Server sẽ tự chạy và auto-start khi reboot.

---

## Sau khi setup

```bash
# Sửa IP public cho client kết nối
nano config/server.properties
# → server.ip=0.0.0.0
# → server.sv1=TênServer:IP_PUBLIC:14445:0,0,0

# Restart để áp dụng
sudo systemctl restart dragonserver
```

---

## Quản lý Server

| Lệnh | Mô tả |
|-------|--------|
| `sudo systemctl start dragonserver` | Khởi động |
| `sudo systemctl stop dragonserver` | Dừng |
| `sudo systemctl restart dragonserver` | Khởi động lại |
| `sudo systemctl status dragonserver` | Xem trạng thái |
| `sudo journalctl -u dragonserver -f` | Xem log realtime |
| `sudo journalctl -u dragonserver --since "1 hour ago"` | Log 1 giờ trước |

---

## Cập nhật Code

```bash
cd server-indie
bash deploy/rebuild.sh              # Pull → Build → Restart
```

---

## Backup Database

```bash
# Backup thủ công
bash deploy/backup.sh

# Tự động backup hàng ngày lúc 4h sáng
crontab -e
# Thêm dòng:
0 4 * * * ~/server-indie/deploy/backup.sh >> ~/backups/backup.log 2>&1
```

---

## Cấu trúc File

```
server-indie/
├── .env.example          # Template cấu hình (copy → .env)
├── .env                  # Cấu hình thật (KHÔNG commit → gitignore)
├── config/
│   ├── server.properties # Cấu hình server + database
│   └── event.properties  # Cấu hình event
├── data/                 # Game data (map, mob, effect, ...)
├── sql/
│   ├── whis_1.sql        # Schema DB player
│   └── whis_2.sql        # Schema DB game data
├── deploy/               # ⭐ Scripts triển khai
│   ├── setup.sh          # Cài đặt toàn bộ (1 lệnh)
│   ├── start.sh          # Khởi động server
│   ├── stop.sh           # Dừng server
│   ├── rebuild.sh        # Pull + Build + Restart
│   ├── backup.sh         # Backup database
│   ├── install-service.sh # Cài systemd service
│   └── dragonserver.service # Systemd unit file
├── src/                  # Source code Java
├── build.gradle          # Gradle build config
└── gradlew               # Gradle wrapper
```

---

## Cấu hình .env

| Biến | Mặc định | Mô tả |
|------|----------|-------|
| `DB_NAME` | `whis_1` | Database player |
| `DB_NAME_DATA` | `whis_2` | Database game data |
| `DB_USER` | `dragonserver` | MySQL user |
| `DB_PASS` | `DragonServer@2025` | MySQL password |
| `SERVER_PORT` | `14445` | Port game (TCP) |
| `GAME_USER` | `gameserver` | Linux user chạy server |
| `JAVA_XMS` | `1g` | JVM min memory |
| `JAVA_XMX` | `2g` | JVM max memory |
| `BACKUP_DIR` | `/home/gameserver/backups` | Thư mục backup |
| `KEEP_DAYS` | `7` | Giữ backup bao nhiêu ngày |

### JVM Memory theo RAM server

| RAM Server | `JAVA_XMS` | `JAVA_XMX` |
|-----------|-----------|-----------|
| 2GB | `512m` | `1g` |
| 4GB | `1g` | `2g` |
| 8GB | `2g` | `4g` |
| 16GB+ | `4g` | `8g` |

---

## Yêu cầu hệ thống

| Thành phần | Yêu cầu |
|-----------|---------|
| OS | Debian 12 (Bookworm) |
| CPU | 2+ cores |
| RAM | 2GB+ (khuyến nghị 4GB) |
| Disk | 5GB+ (SSD khuyến nghị) |
| Port | 14445/tcp mở |

> `setup.sh` tự động cài Java 21, MariaDB, tạo database, build JAR, cấu hình firewall.

---

## Troubleshooting

### Server không start

```bash
sudo journalctl -u dragonserver --no-pager -n 100     # Xem log lỗi
sudo systemctl status mariadb                          # MySQL có chạy?
ss -tlnp | grep 14445                                  # Port bị chiếm?
java -version                                          # Java 21?
ls -la build/libs/server-indie-1.0.0.jar               # JAR tồn tại?
```

### Client không kết nối được

```bash
ss -tlnp | grep 14445                                  # Server listen?
sudo ufw status                                        # Firewall mở port?
grep "server.ip" config/server.properties               # IP = 0.0.0.0?
```

### Lỗi tiếng Việt

```bash
locale                                                 # Kiểm tra UTF-8
sudo dpkg-reconfigure locales                           # Cài locale
# Chọn en_US.UTF-8 hoặc vi_VN.UTF-8
```

### Rebuild sau khi fix code

```bash
bash deploy/rebuild.sh                                 # Hoặc:
sudo systemctl stop dragonserver
./gradlew clean jar
sudo systemctl start dragonserver
```

---

## Tại sao chạy trực tiếp (không Docker)?

| Lý do | Giải thích |
|-------|------------|
| **Latency thấp** | NIO Socket cần latency thấp nhất — Docker thêm network overhead |
| **I/O nhanh** | Thư mục `data/` có hàng nghìn file — native I/O nhanh hơn Docker volume |
| **JVM tuning** | Truy cập trực tiếp RAM/CPU, GC tuning chính xác |
| **Đơn giản** | Single-process Java, không cần container orchestration |
| **Debug dễ** | journalctl, htop, jstack — trực tiếp trên host |

---

## Thông tin kỹ thuật

| Thông tin | Giá trị |
|-----------|---------|
| Java | 21 (Eclipse Temurin) |
| Build | Gradle (fat JAR) |
| Database | MySQL/MariaDB x 2 (`whis_1`, `whis_2`) |
| Connection Pool | HikariCP |
| Network | Java NIO Selector |
| Port | 14445/tcp |
| Main Class | `server.ServerManager` |
| GC | G1GC (target pause < 50ms) |
