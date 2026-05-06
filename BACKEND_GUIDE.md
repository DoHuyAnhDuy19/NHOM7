# Hướng Dẫn Sử Dụng Backend

## 1. Cài Đặt Môi Trường

### Yêu Cầu
- Java JDK 17 trở lên
- Maven 3.6+
- MySQL 8.0+
- IDE: IntelliJ IDEA hoặc VS Code

### Bước 1: Cài Đặt MySQL
```bash
# Tạo database
CREATE DATABASE itro_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Tạo user (nếu cần)
CREATE USER 'itro'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON itro_db.* TO 'itro'@'localhost';
FLUSH PRIVILEGES;
```

### Bước 2: Cập Nhật Cấu Hình
Chỉnh sửa file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/itro_db
spring.datasource.username=root
spring.datasource.password=

jwt.secret=your_very_secure_secret_key_change_in_production
jwt.expiration=86400000
```

### Bước 3: Build và Run
```bash
# Build project
mvn clean build

# Run application
mvn spring-boot:run

# Hoặc chạy file JAR
java -jar target/itro-rental-1.0.0.jar
```

## 2. Cấu Trúc Thư Mục

```
src/main/java/com/itro/
├── models/
│   ├── User.java          # Entity người dùng
│   ├── Room.java          # Entity phòng
│   ├── Tenant.java        # Entity khách thuê
│   ├── Contract.java      # Entity hợp đồng
│   ├── Invoice.java       # Entity hóa đơn
│   └── Service.java       # Entity dịch vụ
│
├── repositories/
│   ├── UserRepository.java
│   ├── RoomRepository.java
│   ├── TenantRepository.java
│   ├── ContractRepository.java
│   ├── InvoiceRepository.java
│   └── ServiceRepository.java
│
├── services/
│   ├── AuthService.java
│   ├── RoomService.java
│   ├── ContractService.java
│   ├── InvoiceService.java
│   ├── TenantService.java
│   ├── ServiceManagementService.java
│   └── impl/
│       ├── AuthServiceImpl.java
│       ├── RoomServiceImpl.java
│       ├── ContractServiceImpl.java
│       ├── InvoiceServiceImpl.java
│       ├── TenantServiceImpl.java
│       └── ServiceManagementServiceImpl.java
│
├── controllers/
│   ├── AuthController.java
│   ├── RoomController.java
│   ├── ContractController.java
│   ├── InvoiceController.java
│   ├── TenantController.java
│   └── ServiceController.java
│
├── dto/
│   ├── ApiResponse.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegisterRequest.java
│   ├── UserDTO.java
│   ├── RoomDTO.java
│   ├── TenantDTO.java
│   ├── ContractDTO.java
│   ├── InvoiceDTO.java
│   └── ServiceDTO.java
│
├── config/
│   └── AppConfig.java
│
├── utils/
│   └── JwtTokenProvider.java
│
└── ItroApplication.java
```

## 3. API Documentation

### Authentication Endpoints
```
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response: {
  "success": true,
  "message": "Đăng nhập thành công",
  "data": {
    "token": "eyJhbGc...",
    "user": { ... }
  }
}
```

### Rooms Management
```
POST /rooms?ownerId=1
Content-Type: application/json

{
  "roomNumber": "A101",
  "roomName": "Phòng A101",
  "description": "...",
  "area": 20.5,
  "rentalPrice": 5000000,
  "maxTenants": 2,
  "imageUrl": "..."
}
```

## 4. Sử Dụng Postman

1. Import collection từ `postman-collection.json`
2. Set base URL: `http://localhost:8080/api`
3. Đặt token vào Authorization header

### Header Template:
```
Authorization: Bearer <token>
Content-Type: application/json
```

## 5. Debug Mode

Bật log chi tiết:
```properties
logging.level.root=DEBUG
logging.level.com.itro=DEBUG
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 6. Common Issues

### Issue: Port 8080 already in use
```bash
# Thay đổi port trong application.properties
server.port=8081
```

### Issue: Can't connect to database
```bash
# Kiểm tra MySQL service
# Windows: services.msc (tìm MySQL)
# Linux: sudo systemctl status mysql
# Mac: brew services list | grep mysql
```

### Issue: JWT Token Invalid
- Kiểm tra token chưa hết hạn
- Xác nhận secret key giống nhau

## 7. Testing

```bash
# Run tests
mvn test

# Run specific test class
mvn test -Dtest=AuthServiceTest

# Run with coverage
mvn test jacoco:report
```

## 8. Deployment

### Build WAR file
```bash
mvn clean package -DskipTests
```

### Deploy to Tomcat
1. Copy WAR file đến `CATALINA_HOME/webapps/`
2. Restart Tomcat

### Deploy to Docker
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/itro-rental-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
docker build -t itro-app .
docker run -p 8080:8080 itro-app
```

## 9. Production Checklist

- [ ] Đổi JWT secret
- [ ] Cài đặt HTTPS
- [ ] Cấu hình CORS cho domain cụ thể
- [ ] Enable database encryption
- [ ] Cấu hình logging
- [ ] Backup database định kỳ
- [ ] Monitor logs và metrics
- [ ] Cấu hình rate limiting
