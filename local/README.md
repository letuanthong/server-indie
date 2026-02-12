# Local Development Setup (Docker)

Sử dụng Docker Compose để khởi tạo MariaDB local cho development.

## Chuẩn bị

```bash
# 1. Tạo thư mục data (nếu chưa có)
mkdir -p local/data

# 2. Khởi động container
cd local
docker compose up -d

# 3. Kiểm tra logs
docker compose logs -f mariadb
```

## Cấu hình Server

Đảm bảo `config/server.properties` dùng:

```properties
database.host=localhost
database.port=3306
database.name=whis_1
database.name_data=whis_2
database.user=root
database.pass=
```

## Chạy Server

```bash
# Cách 1: Gradle task (từ root project)
./gradlew runServer

# Cách 2: Chạy thủ công
java -Xms512m -Xmx2g -Dfile.encoding=UTF-8 \
  -jar build/libs/server-indie-1.0.0.jar
```

## Các lệnh hữu ích

```bash
# Dừng container
docker compose down

# Xóa toàn bộ data (reset database)
docker compose down -v
rm -rf data/

# Xem logs
docker compose logs -f mariadb

# Truy cập MySQL console
docker compose exec mariadb mysql -uroot

# Backup database
docker compose exec mariadb mysqldump -uroot whis_1 > backup.sql
```

## Đường dẫn Data

- Database files: `local/data/mariadb/`
- Config: `docker/mariadb.cnf` (mount read-only)
- Init scripts: `sql/whis_1.sql`, `sql/whis_2.sql`

## Ghi chú

- Database password rỗng (root user không password)
- UTF8MB4 encoding mặc định
- SQL files sẽ tự động import lần đầu container khởi tạo
- Volme `local/data/` được ignore by git (`.gitignore`)
