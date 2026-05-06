// Integration Example for dangnhap.html

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            try {
                const user = await AuthService.login(email, password);
                Utils.showNotification('Đăng nhập thành công!', 'success');
                
                // Redirect based on user role
                setTimeout(() => {
                    if (user.role === 'LANDLORD') {
                        window.location.href = '/phong.html';
                    } else if (user.role === 'TENANT') {
                        window.location.href = '/hoadon.html';
                    } else {
                        window.location.href = '/trangchinh.html';
                    }
                }, 1000);
            } catch (error) {
                Utils.showNotification('Đăng nhập thất bại: ' + error.message, 'error');
            }
        });
    }
});

// Integration Example for dangky.html

document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = {
                email: document.getElementById('email').value,
                username: document.getElementById('username').value,
                password: document.getElementById('password').value,
                fullName: document.getElementById('fullName').value,
                phone: document.getElementById('phone').value,
                address: document.getElementById('address').value,
                role: document.getElementById('role').value
            };
            
            // Validation
            if (!Utils.validateEmail(formData.email)) {
                Utils.showNotification('Email không hợp lệ', 'error');
                return;
            }
            
            if (!Utils.validatePhone(formData.phone)) {
                Utils.showNotification('Số điện thoại không hợp lệ', 'error');
                return;
            }
            
            try {
                await AuthService.register(formData);
                Utils.showNotification('Đăng ký thành công! Vui lòng đăng nhập', 'success');
                
                setTimeout(() => {
                    window.location.href = '/dangnhap.html';
                }, 1500);
            } catch (error) {
                Utils.showNotification('Đăng ký thất bại: ' + error.message, 'error');
            }
        });
    }
});

// Integration Example for phong.html

document.addEventListener('DOMContentLoaded', async () => {
    PageHelper.checkAuthentication();
    
    const roomsList = document.getElementById('roomsList');
    const createRoomBtn = document.getElementById('createRoomBtn');
    
    if (createRoomBtn) {
        createRoomBtn.addEventListener('click', () => {
            showCreateRoomModal();
        });
    }
    
    if (roomsList) {
        try {
            const userId = PageHelper.getCurrentUserId();
            const rooms = await RoomService.getRoomsByOwner(userId);
            
            roomsList.innerHTML = '';
            rooms.forEach(room => {
                const statusBadge = Utils.getStatusBadge(room.status);
                const roomCard = document.createElement('div');
                roomCard.className = 'room-card';
                roomCard.innerHTML = `
                    <h3>${room.roomName} (${room.roomNumber})</h3>
                    <p>Diện tích: ${room.area} m²</p>
                    <p>Giá thuê: ${Utils.formatCurrency(room.rentalPrice)}/tháng</p>
                    <p>Trạng thái: <span style="color: ${statusBadge.color}">${statusBadge.text}</span></p>
                    <button onclick="editRoom(${room.id})">Sửa</button>
                    <button onclick="deleteRoom(${room.id})">Xóa</button>
                `;
                roomsList.appendChild(roomCard);
            });
        } catch (error) {
            Utils.showNotification('Lỗi tải danh sách phòng: ' + error.message, 'error');
        }
    }
});

// Integration Example for hoadon.html

document.addEventListener('DOMContentLoaded', async () => {
    PageHelper.checkAuthentication();
    
    const invoicesList = document.getElementById('invoicesList');
    
    if (invoicesList) {
        try {
            const userId = PageHelper.getCurrentUserId();
            const invoices = await InvoiceService.getAllInvoices();
            
            invoicesList.innerHTML = '';
            invoices.forEach(invoice => {
                const statusBadge = Utils.getStatusBadge(invoice.status);
                const invoiceRow = document.createElement('tr');
                invoiceRow.innerHTML = `
                    <td>${invoice.invoiceNumber}</td>
                    <td>${invoice.tenantName}</td>
                    <td>${Utils.formatDate(invoice.issuedDate)}</td>
                    <td>${Utils.formatCurrency(invoice.totalAmount)}</td>
                    <td><span style="color: ${statusBadge.color}">${statusBadge.text}</span></td>
                    <td>
                        <button onclick="viewInvoice(${invoice.id})">Xem</button>
                        ${invoice.status === 'PENDING' ? `<button onclick="payInvoice(${invoice.id})">Thanh toán</button>` : ''}
                    </td>
                `;
                invoicesList.appendChild(invoiceRow);
            });
        } catch (error) {
            Utils.showNotification('Lỗi tải danh sách hóa đơn: ' + error.message, 'error');
        }
    }
});
