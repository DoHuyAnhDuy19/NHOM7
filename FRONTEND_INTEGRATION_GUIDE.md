# Hướng Dẫn Tích Hợp Frontend-Backend

## 1. Chuẩn Bị
- Backend đang chạy tại `http://localhost:8080/api`
- Tất cả các file HTML, CSS, JS đã sẵn sàng
- Include ba file JS chính: `api-client.js`, `services.js`, `utils.js`

## 2. Thêm Scripts vào HTML

```html
<!-- Thêm vào cuối tag <body> của mỗi file HTML -->
<script src="/js/api-client.js"></script>
<script src="/js/services.js"></script>
<script src="/js/utils.js"></script>
```

## 3. Implement Authentication

### dangnhap.html
```javascript
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const result = await AuthService.login(
            document.getElementById('email').value,
            document.getElementById('password').value
        );
        Utils.showNotification('Đăng nhập thành công!', 'success');
        // Redirect theo role
        window.location.href = result.user.role === 'LANDLORD' ? '/phong.html' : '/hoadon.html';
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
});
```

### dangky.html
```javascript
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        await AuthService.register({
            email: document.getElementById('email').value,
            username: document.getElementById('username').value,
            password: document.getElementById('password').value,
            fullName: document.getElementById('fullName').value,
            phone: document.getElementById('phone').value,
            address: document.getElementById('address').value,
            role: document.getElementById('role').value
        });
        Utils.showNotification('Đăng ký thành công!', 'success');
        window.location.href = '/dangnhap.html';
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
});
```

## 4. Implement Room Management

### phong.html
```javascript
// Tải danh sách phòng
async function loadRooms() {
    try {
        PageHelper.checkAuthentication();
        const userId = PageHelper.getCurrentUserId();
        const rooms = await RoomService.getRoomsByOwner(userId);
        
        const list = document.getElementById('roomsList');
        list.innerHTML = rooms.map(room => `
            <div class="room-card">
                <h3>${room.roomName}</h3>
                <p>Số phòng: ${room.roomNumber}</p>
                <p>Giá: ${Utils.formatCurrency(room.rentalPrice)}</p>
                <p>Trạng thái: ${Utils.getStatusBadge(room.status).text}</p>
                <button onclick="editRoom(${room.id})">Sửa</button>
                <button onclick="deleteRoom(${room.id})">Xóa</button>
            </div>
        `).join('');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

// Tạo phòng mới
async function createRoom() {
    try {
        const userId = PageHelper.getCurrentUserId();
        const roomData = {
            roomNumber: document.getElementById('roomNumber').value,
            roomName: document.getElementById('roomName').value,
            description: document.getElementById('description').value,
            area: parseFloat(document.getElementById('area').value),
            rentalPrice: parseFloat(document.getElementById('rentalPrice').value),
            maxTenants: parseInt(document.getElementById('maxTenants').value)
        };
        
        await RoomService.createRoom(userId, roomData);
        Utils.showNotification('Tạo phòng thành công!', 'success');
        loadRooms();
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

document.addEventListener('DOMContentLoaded', loadRooms);
```

## 5. Implement Invoice Management

### hoadon.html
```javascript
// Tải danh sách hóa đơn
async function loadInvoices() {
    try {
        PageHelper.checkAuthentication();
        const invoices = await InvoiceService.getAllInvoices();
        
        const table = document.getElementById('invoicesTable');
        table.innerHTML = invoices.map(inv => `
            <tr>
                <td>${inv.invoiceNumber}</td>
                <td>${inv.tenantName}</td>
                <td>${Utils.formatDate(inv.issuedDate)}</td>
                <td>${Utils.formatCurrency(inv.totalAmount)}</td>
                <td>
                    <span style="color: ${Utils.getStatusBadge(inv.status).color}">
                        ${Utils.getStatusBadge(inv.status).text}
                    </span>
                </td>
                <td>
                    <button onclick="viewInvoice(${inv.id})">Xem</button>
                    ${inv.status === 'PENDING' ? `<button onclick="payInvoice(${inv.id})">Thanh toán</button>` : ''}
                </td>
            </tr>
        `).join('');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

// Thanh toán hóa đơn
async function payInvoice(invoiceId) {
    if (await Utils.showConfirmDialog('Xác nhận thanh toán hóa đơn này?')) {
        try {
            await InvoiceService.markInvoiceAsPaid(invoiceId);
            Utils.showNotification('Thanh toán thành công!', 'success');
            loadInvoices();
        } catch (error) {
            Utils.showNotification(error.message, 'error');
        }
    }
}

document.addEventListener('DOMContentLoaded', loadInvoices);
```

## 6. Implement Contract Management

### hopdong.html
```javascript
async function loadContracts() {
    try {
        PageHelper.checkAuthentication();
        const userId = PageHelper.getCurrentUserId();
        const contracts = await ContractService.getContractsByOwner(userId);
        
        const list = document.getElementById('contractsList');
        list.innerHTML = contracts.map(contract => `
            <div class="contract-card">
                <h3>${contract.contractNumber}</h3>
                <p>Phòng: ${contract.roomName}</p>
                <p>Khách: ${contract.tenantName}</p>
                <p>Từ: ${Utils.formatDate(contract.startDate)} đến ${Utils.formatDate(contract.endDate)}</p>
                <p>Tiền thuê: ${Utils.formatCurrency(contract.rentalAmount)}/tháng</p>
                <p>Trạng thái: ${Utils.getStatusBadge(contract.status).text}</p>
                ${contract.status === 'ACTIVE' ? `<button onclick="terminateContract(${contract.id})">Chấm dứt</button>` : ''}
            </div>
        `).join('');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

async function terminateContract(contractId) {
    if (await Utils.showConfirmDialog('Chấm dứt hợp đồng này?')) {
        try {
            await ContractService.terminateContract(contractId);
            Utils.showNotification('Chấm dứt hợp đồng thành công!', 'success');
            loadContracts();
        } catch (error) {
            Utils.showNotification(error.message, 'error');
        }
    }
}

document.addEventListener('DOMContentLoaded', loadContracts);
```

## 7. Implement Tenant Management

### khachthue.html
```javascript
async function loadTenants() {
    try {
        PageHelper.checkAuthentication();
        const tenants = await TenantService.getAllTenants();
        
        const list = document.getElementById('tenantsList');
        list.innerHTML = tenants.map(tenant => `
            <div class="tenant-card">
                <h3>${tenant.userName}</h3>
                <p>Email: ${tenant.userEmail}</p>
                <p>Phone: ${tenant.userPhone}</p>
                <p>ID: ${tenant.idNumber} (${tenant.idType})</p>
                <button onclick="viewTenant(${tenant.id})">Xem Chi Tiết</button>
            </div>
        `).join('');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

document.addEventListener('DOMContentLoaded', loadTenants);
```

## 8. Implement Service Management

### dichvu.html
```javascript
async function loadServices() {
    try {
        PageHelper.checkAuthentication();
        const services = await ServiceService.getAllServices();
        
        const list = document.getElementById('servicesList');
        list.innerHTML = services.map(service => `
            <div class="service-card">
                <h3>${service.serviceName}</h3>
                <p>Loại: ${service.serviceType}</p>
                <p>Giá: ${Utils.formatCurrency(service.price)}</p>
                <p>${service.description}</p>
            </div>
        `).join('');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

document.addEventListener('DOMContentLoaded', loadServices);
```

## 9. Implement Account Management

### taikhoan.html
```javascript
async function loadProfile() {
    try {
        PageHelper.checkAuthentication();
        const userId = PageHelper.getCurrentUserId();
        const user = await apiClient.getProfile(userId);
        
        if (user.success) {
            document.getElementById('fullName').value = user.data.fullName;
            document.getElementById('email').value = user.data.email;
            document.getElementById('phone').value = user.data.phone;
            document.getElementById('address').value = user.data.address;
        }
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
}

document.getElementById('updateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const userId = PageHelper.getCurrentUserId();
        await AuthService.updateProfile(userId, {
            fullName: document.getElementById('fullName').value,
            phone: document.getElementById('phone').value,
            address: document.getElementById('address').value
        });
        Utils.showNotification('Cập nhật hồ sơ thành công!', 'success');
    } catch (error) {
        Utils.showNotification(error.message, 'error');
    }
});

document.addEventListener('DOMContentLoaded', loadProfile);
```

## 10. Logging Out

```javascript
document.getElementById('logoutBtn').addEventListener('click', () => {
    if (confirm('Bạn có chắc chắn muốn đăng xuất?')) {
        AuthService.logout();
    }
});
```

## 11. Testing

### Test Login
1. Mở `dangnhap.html`
2. Nhập email: `test@example.com`
3. Password: `123456`
4. Kiểm tra console cho API response

### Test Create Room
1. Đăng nhập với tài khoản LANDLORD
2. Trang `phong.html`
3. Nhập thông tin phòng
4. Kiểm tra database MySQL

## 12. Error Handling

Mỗi API call đã có error handling, kiểm tra console nếu có lỗi:
```javascript
console.error('API Error:', error);
```

## 13. Performance Tips

- Cache dữ liệu khi không thay đổi
- Dùng pagination cho danh sách lớn
- Debounce search input
- Lazy load images

## 14. Security Tips

- Không lưu password trong localStorage
- Validate dữ liệu phía client và server
- Sử dụng HTTPS trong production
- Refresh token khi hết hạn
