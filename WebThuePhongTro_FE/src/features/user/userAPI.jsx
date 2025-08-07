import axios from 'axios';
import Swal from 'sweetalert2';
import { loadConfig } from '../../config';


let userAPI 
export const setupAPI = async () => {
  const cfg = await loadConfig();
  userAPI = `${cfg.API_URL}/api`;
};

export const meAPI = async (token) => {
    if (!userAPI) await setupAPI();

    try {
        const response = await axios.get(`${userAPI}/user/me`, {
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
};

export const editUserAPI = async (token, editData) => {
    if (!userAPI) await setupAPI();
    try {
        const response = await axios.put(`${userAPI}/user/edit`, editData, {
            headers: {
                'Content-Type': 'multipart/form-data',
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
