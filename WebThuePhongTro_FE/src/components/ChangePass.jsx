import React, { useState } from 'react'
import { useAuth } from '../features/auth/useAuth';
import { useSelector } from 'react-redux';
import Swal from 'sweetalert2';

const ChangePass = () => {
    const [showCurrentPassword, setShowCurrentPassword] = useState(false);
    const [showNewPassword, setShowNewPassword] = useState(false);
    const [showConfirmNewPassword, setShowConfirmNewPassword] = useState(false);
    const [changePassData, setChangePassData] = useState({ currentPassword: '', newPassword: '' });
    const [confirmPassword, setConfirmPassword] = useState('');
    const isPasswordMatch = changePassData.newPassword && confirmPassword && changePassData.newPassword === confirmPassword;
    const { changePass } = useAuth();
    const auth = useSelector(state => state.auth); 

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!changePassData.currentPassword || !changePassData.newPassword) {
            Swal.fire({
                title: "Lỗi",
                text: "Vui lòng nhập đầy đủ thông tin",
                icon: "error",
            });
            return;
        }
        try {
            await changePass(auth.token, changePassData);
            setChangePassData({ currentPassword: '', newPassword: '' });
            setConfirmPassword('');
        } catch (error) {
            console.error(error);
        }
       
    };

    return (
        <article className="bg-white rounded-lg p-6 flex flex-col gap-6">
            <span className="font-semibold text-lg">Đổi mật khẩu</span>
            <label>Vui lòng nhập mật khẩu hiện tại của bạn để thay đổi mật khẩu</label>
            <div className=" justify-between items-center">
                <div className="password-field">
                    <input type={showCurrentPassword ? 'text' : 'password'} placeholder="Mật khẩu hiện tại" name="currentPassword" required
                        value={changePassData.currentPassword}
                        onChange={(e) => setChangePassData({ ...changePassData, currentPassword: e.target.value })}
                    />
                    <i
                        className={`fa-solid ${showCurrentPassword ? 'fa-eye-slash' : 'fa-eye'} eye-icon`}
                        onClick={() => setShowCurrentPassword(!showCurrentPassword)}></i>
                </div>

                <div className="password-field">
                    <input type={showNewPassword ? 'text' : 'password'} placeholder="Mật khẩu mới" name="newPassword" required
                        value={changePassData.newPassword}
                        onChange={(e) => setChangePassData({ ...changePassData, newPassword: e.target.value })}
                    />
                    <i
                        className={`fa-solid ${showNewPassword ? 'fa-eye-slash' : 'fa-eye'} eye-icon`}
                        onClick={() => setShowNewPassword(!showNewPassword)}></i>
                </div>
                <div className="password-field">
                    <input type={showConfirmNewPassword ? 'text' : 'password'} placeholder="Xác nhận mật khẩu mới" name="confirmNewPassword" required
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value )}/>
                    <i
                        className={`fa-solid ${showConfirmNewPassword ? 'fa-eye-slash' : 'fa-eye'} eye-icon`}
                        onClick={() => setShowConfirmNewPassword(!showConfirmNewPassword)}
                    ></i>
                </div>

                  {!isPasswordMatch && confirmPassword && (
                    <label className='accept-label' style={{ color: 'red' }}>Mật khẩu không khớp</label>
                )}

                <div className="flex justify-center mt-4">
                    <button className="login-btn !w-fit" onClick={handleSubmit}>Xác nhận</button>
                </div>
                
            </div>
        </article>
  )
}

export default ChangePass