# ITRO - Hệ Thống Quản Lý Cho Thuê Nhà/Phòng

## Mô Tả Dự Án
ITRO là một ứng dụng web toàn diện để quản lý cho thuê nhà và phòng, kết hợp Frontend (HTML, CSS, JavaScript) và Backend (Java Spring Boot).

## Chức Năng Chính

### 1. Quản Lý Người Dùng
- Đăng ký tài khoản (Chủ nhà, Khách thuê)
- Đăng nhập/Đăng xuất
- Quản lý hồ sơ người dùng
- Phân quyền: Admin, Chủ nhà, Khách thuê

### 2. Quản Lý Phòng
- Tạo/Sửa/Xóa thông tin phòng
- Quản lý trạng thái phòng (Còn trống, Đã cho thuê, Bảo trì)
- Xem danh sách phòng theo chủ nhà
- Xem danh sách phòng theo trạng thái

### 3. Quản Lý Khách Thuê
- Tạo hồ sơ khách thuê
- Lưu thông tin cá nhân chi tiết
- Quản lý liên hệ khẩn cấp
- Xem lịch sử hợp đồng

### 4. Quản Lý Hợp Đồng
- Tạo hợp đồng thuê phòng
- Quản lý thời hạn hợp đồng
- Theo dõi trạng thái hợp đồng (Chờ duyệt, Hoạt động, Chấm dứt)
- Chấm dứt hợp đồng sớm

### 5. Quản Lý Hóa Đơn
- Tạo hóa đơn cho mỗi hợp đồng
- Tính toán chi phí: Tiền thuê, Nước, Điện, Chi phí khác
- Theo dõi thanh toán
- Xem hóa đơn quá hạn

### 6. Quản Lý Dịch Vụ
- Tạo/Sửa/Xóa dịch vụ
- Phân loại dịch vụ: Nước, Điện, Internet, Vệ sinh, v.v.
- Định giá dịch vụ

## Cấu Trúc Dự Án

### Backend (Java Spring Boot)
```
src/main/java/com/itro/
├── models/           # JPA Entities
│   ├── User
│   ├── Room
│   ├── Tenant
│   ├── Contract
│   ├── Invoice
│   └── Service
├── repositories/     # Spring Data JPA Repositories
├── services/         # Business Logic
│   ├── AuthService
│   ├── RoomService
│   ├── ContractService
│   ├── InvoiceService
│   ├── TenantService
│   └── ServiceManagementService
├── controllers/      # REST Controllers
├── dto/             # Data Transfer Objects
├── config/          # Configuration Classes
├── utils/           # Utility Classes
└── ItroApplication.java
```

### Frontend (HTML, CSS, JavaScript)
```
├── dangnhap.html      # Trang Đăng Nhập
├── dangky.html        # Trang Đăng Ký
├── trangchinh.html    # Trang Chính
├── phong.html         # Quản Lý Phòng
├── khachthue.html     # Quản Lý Khách Thuê
├── hopdong.html       # Quản Lý Hợp Đồng
├── hoadon.html        # Quản Lý Hóa Đơn
├── dichvu.html        # Quản Lý Dịch Vụ
├── taikhoan.html      # Quản Lý Tài Khoản
└── js/
    ├── api-client.js  # API Client
    ├── services.js    # Service Classes
    └── utils.js       # Utility Functions
```

## Công Nghệ Sử Dụng

### Backend
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL 8.0
- Maven

### Frontend
- HTML5
- CSS3
- JavaScript (ES6+)
- Fetch API

## Cài Đặt

### Backend
1. Clone/Download dự án
2. Cập nhật database configuration trong `application.properties`
3. Chạy: `mvn spring-boot:run`
4. API sẽ chạy tại: `http://localhost:8080/api`

### Frontend
1. Cập nhật `API_BASE_URL` trong `js/api-client.js` nếu cần
2. Mở các file HTML trong trình duyệt
3. Hoặc sử dụng live server

## API Endpoints

### Authentication
- `POST /auth/login` - Đăng nhập
- `POST /auth/register` - Đăng ký
- `GET /auth/profile/{userId}` - Lấy hồ sơ
- `PUT /auth/profile/{userId}` - Cập nhật hồ sơ

### Rooms
- `POST /rooms` - Tạo phòng
- `GET /rooms` - Danh sách phòng
- `GET /rooms/{roomId}` - Chi tiết phòng
- `PUT /rooms/{roomId}` - Cập nhật phòng
- `DELETE /rooms/{roomId}` - Xóa phòng
- `PATCH /rooms/{roomId}/status` - Cập nhật trạng thái

### Tenants
- `POST /tenants` - Tạo khách thuê
- `GET /tenants` - Danh sách khách thuê
- `GET /tenants/{tenantId}` - Chi tiết khách thuê
- `PUT /tenants/{tenantId}` - Cập nhật khách thuê
- `DELETE /tenants/{tenantId}` - Xóa khách thuê

### Contracts
- `POST /contracts` - Tạo hợp đồng
- `GET /contracts` - Danh sách hợp đồng
- `GET /contracts/{contractId}` - Chi tiết hợp đồng
- `PUT /contracts/{contractId}` - Cập nhật hợp đồng
- `PATCH /contracts/{contractId}/terminate` - Chấm dứt hợp đồng

### Invoices
- `POST /invoices` - Tạo hóa đơn
- `GET /invoices` - Danh sách hóa đơn
- `GET /invoices/{invoiceId}` - Chi tiết hóa đơn
- `PUT /invoices/{invoiceId}` - Cập nhật hóa đơn
- `PATCH /invoices/{invoiceId}/pay` - Đánh dấu đã thanh toán
- `GET /invoices/overdue` - Hóa đơn quá hạn

### Services
- `POST /services` - Tạo dịch vụ
- `GET /services` - Danh sách dịch vụ
- `GET /services/{serviceId}` - Chi tiết dịch vụ
- `PUT /services/{serviceId}` - Cập nhật dịch vụ
- `DELETE /services/{serviceId}` - Xóa dịch vụ

## Cấu Hình Database

```sql
CREATE DATABASE itro_db;
USE itro_db;

-- Tables sẽ được tạo tự động bởi Hibernate
```

## File Cấu Hình

### application.properties
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/itro_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_secret_key
jwt.expiration=86400000
```

## Authentication Flow

1. User đăng nhập với email và password
2. Backend verify credentials và tạo JWT token
3. Frontend lưu token vào localStorage
4. Mỗi request có gửi token trong Authorization header
5. Backend verify token trước khi xử lý request

## Lỗi Thường Gặp

### CORS Error
Đảm bảo backend đã config CORS:
```java
@CrossOrigin(origins = "*", maxAge = 3600)
```

### Database Connection Error
- Kiểm tra MySQL service đang chạy
- Cập nhật username/password trong application.properties
- Tạo database: `CREATE DATABASE itro_db;`

### JWT Token Invalid
- Kiểm tra token chưa hết hạn
- Xác nhận `jwt.secret` giống nhau

## Tác Giả
ITRO Development Team - Nhóm 7

## License
MIT License
