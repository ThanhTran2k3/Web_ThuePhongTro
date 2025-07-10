import React, {  useState } from 'react'
import '../assets/css/base.css';
import { useUser } from '../features/user/useUser';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';

const EditUserForm = ({ onClose }) => {
    const auth = useSelector(state => state.auth); 
    const {
        updateUser,
    } = useUser()
    const [ editData, setEditData] = useState({
        userName: '',
        birthDay: 0,
        gender: 0,
    });
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData();
            if (editData.userName !== auth.user.userName) {
                formData.append('userName', editData.userName);
            }
            if (editData.birthDay !== auth.user.birthDay && editData.birthDay !== getTodayDate()) {
                formData.append('birthDay', editData.birthDay);
            }

            if (editData.gender !== auth.user.gender && editData.gender !== 0) {
                formData.append('gender', editData.gender);
            }
            await updateUser(formData);
            onClose();
        } catch (error) {
            console.error(error);
        }
      
    };

    useEffect(() => {
        if (auth.user) {
            setEditData({
                userName: auth.user.userName || '',
                birthDay: auth.user.birthDay || getTodayDate(),
                gender: auth.user.gender ?? 0,
            });
        }
    }, [auth.user]);
    const getTodayDate = () => {
        const today = new Date();
        const day = String(today.getDate()).padStart(2, '0');
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const year = today.getFullYear();
        return `${year}-${month}-${day}`;
    };

   return (
    <form onSubmit={handleSubmit} className="modal-overlay">
      <div className="modal">
        <button className="close-btn" onClick={onClose}>×</button>
        <h2>Cập nhật thông tin</h2>
        <input type="text" placeholder="Tên tài khoản" required 
            value={editData.userName}
            onChange={(e) => setEditData({ ...editData, userName: e.target.value })}
        />
        <input type="date" placeholder="Ngày sinh" required 
            value={editData.birthDay}
            onChange={(e) => setEditData({ ...editData, birthDay: e.target.value })}
        />
        <select
            required
            value={editData.gender}
            onChange={(e) => setEditData({ ...editData, gender: parseInt(e.target.value) })}
            className="mt-2 p-2 border rounded w-full"
            >
            <option value="0" disabled>-- Chọn giới tính --</option>
            <option value="1">Nam</option>
            <option value="2">Nữ</option>
        </select>
        
        <button className="login-btn">Cập nhật</button>
      </div>
    </form>

  
  );
}

export default EditUserForm