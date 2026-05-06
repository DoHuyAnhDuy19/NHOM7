// Authentication Service
class AuthService {
    static async login(email, password) {
        try {
            const response = await apiClient.login(email, password);
            if (response.success) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify(response.data.user));
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    }

    static async register(userData) {
        try {
            const response = await apiClient.register(userData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Register error:', error);
            throw error;
        }
    }

    static logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.href = '/dangnhap.html';
    }

    static isLoggedIn() {
        return !!localStorage.getItem('token');
    }

    static getCurrentUser() {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    }

    static async updateProfile(userId, userData) {
        try {
            const response = await apiClient.updateProfile(userId, userData);
            if (response.success) {
                localStorage.setItem('user', JSON.stringify(response.data));
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Update profile error:', error);
            throw error;
        }
    }
}

// Room Service
class RoomService {
    static async createRoom(ownerId, roomData) {
        try {
            const response = await apiClient.createRoom(ownerId, roomData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Create room error:', error);
            throw error;
        }
    }

    static async getAllRooms() {
        try {
            const response = await apiClient.getAllRooms();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get rooms error:', error);
            throw error;
        }
    }

    static async getRoomsByOwner(ownerId) {
        try {
            const response = await apiClient.getRoomsByOwner(ownerId);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get owner rooms error:', error);
            throw error;
        }
    }

    static async getRoomsByStatus(status) {
        try {
            const response = await apiClient.getRoomsByStatus(status);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get rooms by status error:', error);
            throw error;
        }
    }

    static async updateRoom(roomId, roomData) {
        try {
            const response = await apiClient.updateRoom(roomId, roomData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Update room error:', error);
            throw error;
        }
    }

    static async updateRoomStatus(roomId, status) {
        try {
            const response = await apiClient.updateRoomStatus(roomId, status);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Update room status error:', error);
            throw error;
        }
    }

    static async deleteRoom(roomId) {
        try {
            const response = await apiClient.deleteRoom(roomId);
            if (response.success) {
                return true;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Delete room error:', error);
            throw error;
        }
    }
}

// Contract Service
class ContractService {
    static async createContract(contractData) {
        try {
            const response = await apiClient.createContract(contractData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Create contract error:', error);
            throw error;
        }
    }

    static async getAllContracts() {
        try {
            const response = await apiClient.getAllContracts();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get contracts error:', error);
            throw error;
        }
    }

    static async getContractsByOwner(ownerId) {
        try {
            const response = await apiClient.getContractsByOwner(ownerId);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get owner contracts error:', error);
            throw error;
        }
    }

    static async terminateContract(contractId) {
        try {
            const response = await apiClient.terminateContract(contractId);
            if (response.success) {
                return true;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Terminate contract error:', error);
            throw error;
        }
    }
}

// Invoice Service
class InvoiceService {
    static async createInvoice(invoiceData) {
        try {
            const response = await apiClient.createInvoice(invoiceData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Create invoice error:', error);
            throw error;
        }
    }

    static async getAllInvoices() {
        try {
            const response = await apiClient.getAllInvoices();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get invoices error:', error);
            throw error;
        }
    }

    static async getOverdueInvoices() {
        try {
            const response = await apiClient.getOverdueInvoices();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get overdue invoices error:', error);
            throw error;
        }
    }

    static async markInvoiceAsPaid(invoiceId) {
        try {
            const response = await apiClient.markInvoiceAsPaid(invoiceId);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Mark invoice as paid error:', error);
            throw error;
        }
    }
}

// Tenant Service
class TenantService {
    static async createTenant(userId, tenantData) {
        try {
            const response = await apiClient.createTenant(userId, tenantData);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Create tenant error:', error);
            throw error;
        }
    }

    static async getAllTenants() {
        try {
            const response = await apiClient.getAllTenants();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get tenants error:', error);
            throw error;
        }
    }
}

// Service Management
class ServiceService {
    static async getAllServices() {
        try {
            const response = await apiClient.getAllServices();
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get services error:', error);
            throw error;
        }
    }

    static async getServicesByType(serviceType) {
        try {
            const response = await apiClient.getServicesByType(serviceType);
            if (response.success) {
                return response.data;
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('Get services by type error:', error);
            throw error;
        }
    }
}
