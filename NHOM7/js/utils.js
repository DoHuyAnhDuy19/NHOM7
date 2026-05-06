// Utility Functions
class Utils {
    static formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    }

    static formatDate(date) {
        if (typeof date === 'string') {
            date = new Date(date);
        }
        return date.toLocaleDateString('vi-VN');
    }

    static formatDateTime(dateTime) {
        if (typeof dateTime === 'string') {
            dateTime = new Date(dateTime);
        }
        return dateTime.toLocaleString('vi-VN');
    }

    static getDaysDifference(date1, date2) {
        const d1 = new Date(date1);
        const d2 = new Date(date2);
        const diffTime = Math.abs(d2 - d1);
        return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    }

    static validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    static validatePhone(phone) {
        const re = /^[0-9]{10,11}$/;
        return re.test(phone);
    }

    static showNotification(message, type = 'success') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        
        const style = document.createElement('style');
        style.textContent = `
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 15px 20px;
                border-radius: 8px;
                font-weight: 500;
                z-index: 9999;
                animation: slideIn 0.3s ease-out;
            }
            .notification-success {
                background-color: #4caf50;
                color: white;
            }
            .notification-error {
                background-color: #f44336;
                color: white;
            }
            .notification-warning {
                background-color: #ff9800;
                color: white;
            }
            .notification-info {
                background-color: #2196f3;
                color: white;
            }
            @keyframes slideIn {
                from {
                    transform: translateX(400px);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }
        `;
        
        document.head.appendChild(style);
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease-in forwards';
            setTimeout(() => {
                notification.remove();
                style.remove();
            }, 300);
        }, 3000);
    }

    static showConfirmDialog(message) {
        return new Promise((resolve) => {
            const confirmed = confirm(message);
            resolve(confirmed);
        });
    }

    static getStatusBadge(status) {
        const statusMap = {
            'AVAILABLE': { text: 'Còn trống', color: '#4caf50' },
            'RENTED': { text: 'Đã cho thuê', color: '#ff9800' },
            'MAINTENANCE': { text: 'Bảo trì', color: '#f44336' },
            'INACTIVE': { text: 'Không hoạt động', color: '#9e9e9e' },
            'PENDING': { text: 'Chờ duyệt', color: '#2196f3' },
            'ACTIVE': { text: 'Hoạt động', color: '#4caf50' },
            'TERMINATED': { text: 'Đã chấm dứt', color: '#f44336' },
            'EXPIRED': { text: 'Hết hạn', color: '#9e9e9e' },
            'PAID': { text: 'Đã thanh toán', color: '#4caf50' },
            'OVERDUE': { text: 'Quá hạn', color: '#f44336' },
        };
        
        return statusMap[status] || { text: status, color: '#9e9e9e' };
    }
}

// Page Helper
class PageHelper {
    static checkAuthentication() {
        if (!AuthService.isLoggedIn()) {
            window.location.href = '/dangnhap.html';
        }
    }

    static getCurrentUserId() {
        const user = AuthService.getCurrentUser();
        return user ? user.id : null;
    }

    static canAccessPage(requiredRole) {
        const user = AuthService.getCurrentUser();
        if (!user || user.role !== requiredRole) {
            window.location.href = '/trangchinh.html';
        }
    }
}
