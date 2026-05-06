// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// API Service Class
class ApiClient {
    constructor() {
        this.baseURL = API_BASE_URL;
        this.token = localStorage.getItem('token');
    }

    getHeaders() {
        const headers = {
            'Content-Type': 'application/json'
        };
        if (this.token) {
            headers['Authorization'] = `Bearer ${this.token}`;
        }
        return headers;
    }

    async request(method, endpoint, data = null) {
        const url = `${this.baseURL}${endpoint}`;
        const config = {
            method,
            headers: this.getHeaders()
        };

        if (data) {
            config.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(url, config);
            
            if (!response.ok && response.status === 401) {
                localStorage.removeItem('token');
                window.location.href = '/dangnhap.html';
            }

            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    // Auth APIs
    login(email, password) {
        return this.request('POST', '/auth/login', { email, password });
    }

    register(registerData) {
        return this.request('POST', '/auth/register', registerData);
    }

    getProfile(userId) {
        return this.request('GET', `/auth/profile/${userId}`);
    }

    updateProfile(userId, userData) {
        return this.request('PUT', `/auth/profile/${userId}`, userData);
    }

    // Room APIs
    createRoom(ownerId, roomData) {
        return this.request('POST', `/rooms?ownerId=${ownerId}`, roomData);
    }

    updateRoom(roomId, roomData) {
        return this.request('PUT', `/rooms/${roomId}`, roomData);
    }

    getRoomById(roomId) {
        return this.request('GET', `/rooms/${roomId}`);
    }

    getAllRooms() {
        return this.request('GET', '/rooms');
    }

    getRoomsByOwner(ownerId) {
        return this.request('GET', `/rooms/owner/${ownerId}`);
    }

    getRoomsByStatus(status) {
        return this.request('GET', `/rooms/status/${status}`);
    }

    updateRoomStatus(roomId, status) {
        return this.request('PATCH', `/rooms/${roomId}/status?status=${status}`);
    }

    deleteRoom(roomId) {
        return this.request('DELETE', `/rooms/${roomId}`);
    }

    // Tenant APIs
    createTenant(userId, tenantData) {
        return this.request('POST', `/tenants?userId=${userId}`, tenantData);
    }

    updateTenant(tenantId, tenantData) {
        return this.request('PUT', `/tenants/${tenantId}`, tenantData);
    }

    getTenantById(tenantId) {
        return this.request('GET', `/tenants/${tenantId}`);
    }

    getTenantByUser(userId) {
        return this.request('GET', `/tenants/user/${userId}`);
    }

    getAllTenants() {
        return this.request('GET', '/tenants');
    }

    deleteTenant(tenantId) {
        return this.request('DELETE', `/tenants/${tenantId}`);
    }

    // Contract APIs
    createContract(contractData) {
        return this.request('POST', '/contracts', contractData);
    }

    updateContract(contractId, contractData) {
        return this.request('PUT', `/contracts/${contractId}`, contractData);
    }

    getContractById(contractId) {
        return this.request('GET', `/contracts/${contractId}`);
    }

    getAllContracts() {
        return this.request('GET', '/contracts');
    }

    getContractsByOwner(ownerId) {
        return this.request('GET', `/contracts/owner/${ownerId}`);
    }

    getContractsByTenant(tenantId) {
        return this.request('GET', `/contracts/tenant/${tenantId}`);
    }

    getContractsByRoom(roomId) {
        return this.request('GET', `/contracts/room/${roomId}`);
    }

    getContractsByStatus(status) {
        return this.request('GET', `/contracts/status/${status}`);
    }

    terminateContract(contractId) {
        return this.request('PATCH', `/contracts/${contractId}/terminate`);
    }

    // Invoice APIs
    createInvoice(invoiceData) {
        return this.request('POST', '/invoices', invoiceData);
    }

    updateInvoice(invoiceId, invoiceData) {
        return this.request('PUT', `/invoices/${invoiceId}`, invoiceData);
    }

    getInvoiceById(invoiceId) {
        return this.request('GET', `/invoices/${invoiceId}`);
    }

    getAllInvoices() {
        return this.request('GET', '/invoices');
    }

    getInvoicesByContract(contractId) {
        return this.request('GET', `/invoices/contract/${contractId}`);
    }

    getInvoicesByTenant(tenantId) {
        return this.request('GET', `/invoices/tenant/${tenantId}`);
    }

    getInvoicesByStatus(status) {
        return this.request('GET', `/invoices/status/${status}`);
    }

    markInvoiceAsPaid(invoiceId) {
        return this.request('PATCH', `/invoices/${invoiceId}/pay`);
    }

    getOverdueInvoices() {
        return this.request('GET', '/invoices/overdue');
    }

    // Service APIs
    createService(serviceData) {
        return this.request('POST', '/services', serviceData);
    }

    updateService(serviceId, serviceData) {
        return this.request('PUT', `/services/${serviceId}`, serviceData);
    }

    getServiceById(serviceId) {
        return this.request('GET', `/services/${serviceId}`);
    }

    getAllServices() {
        return this.request('GET', '/services');
    }

    getServicesByType(serviceType) {
        return this.request('GET', `/services/type/${serviceType}`);
    }

    deleteService(serviceId) {
        return this.request('DELETE', `/services/${serviceId}`);
    }
}

// Export
const apiClient = new ApiClient();
