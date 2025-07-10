import axios from 'axios';
import Swal from 'sweetalert2';
const authAPI = 'http://localhost:8080/api';

export const loginAPI = async (loginData) => {
    try {
        const response = await axios.post(`${authAPI}/auth/login`, loginData, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        Swal.fire({
            title: "Đăng nhập thành công!",
            icon: "success",
        });
        return response.data.result;
    } catch (error) {
        const errors = error?.response?.data?.error;
        const errorHtml = Array.isArray(errors)
            ? errors.map(err => `${err.split(":")[1]?.trim()}<br>`).join('')
            : "Tài khoản hoặc mật khẩu không đúng! Vui lòng thử lại.";
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            html: errorHtml,
        });
    }
};


export const registerAPI = async (registerData) => {
    try {
        await axios.post(`${authAPI}/auth/register`, registerData, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        Swal.fire({
            title: "Đăng ký thành công!",
            icon: "success",
        });
    } catch (error) {
        const errors = error?.response?.data?.error;
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            html: errors.map(err => `<label>${err.split(':').slice(1).join(':').trim()}</label><br>`).join('')
        });
    }
}


export const getAccountAPI = async (token) => {
     try {
        const response = await axios.get(`${authAPI}/auth/account`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data.result;
    } catch (error) {
        const errors = error?.response?.data?.error;
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            html: errors,
        });
    }
}

export const changePassAPI = async (token, changePassData) => {
    try {
        const response = await axios.put(`${authAPI}/auth/changePass`, changePassData, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
        Swal.fire({
            title: "Đổi mật khẩu thành công!",
            icon: "success",
        });
        return response.data.result;
    } catch (error) {
        const errors = error?.response?.data?.error;
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            html: errors,
        });
    }
}


export const editAccountAPI = async (token, editData) => {
    try {
        const response = await axios.put(`${authAPI}/auth/edit`, editData, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
    
        Swal.fire({ 
            title: "Cập nhật thông tin thành công!",
            icon: "success",
        });
        return response.data.result;
    } catch (error) {
        const errors = error?.response?.data?.error;
        const errorHtml = Array.isArray(errors)
            ? errors.map(err => `${err.split(":")[1]?.trim()}<br>`).join('')
            : "Đã xảy ra lỗi trong quá trình cập nhật thông tin.";
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            html: errorHtml,
        });
        return null;
    }
};
