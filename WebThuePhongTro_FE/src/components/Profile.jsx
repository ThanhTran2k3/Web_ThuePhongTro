import React, { useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import EditUserForm from './EditUserForm';
import { useUser } from '../features/user/useUser';
import EditAccountForm from './EditAccountForm';
const Profile = () => {
    const auth = useSelector(state => state.auth); 
    const [showEdit, setShowEdit] = useState(false);
    const [showEditAccount, setShowEditAccount] = useState(false);
    const [editField, setEditField] = useState(null);
    const fileInputRef = useRef(null);
    const {
        updateUser,
    } = useUser()


    const formatDate = (dateStr) => {
        if (!dateStr) return '';

        const date = new Date(dateStr);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); 
        const year = date.getFullYear();

        return `${day}/${month}/${year}`;
    };

     const handleAvatarClick = () => {
        fileInputRef.current.click(); 
    };

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('avatar', file);
            await updateUser(formData);
        }
    };

    return (

        <article className="bg-white rounded-lg p-6 flex flex-col gap-6">
            <div className="flex justify-between items-center">
                <span className="font-semibold text-lg">Thông tin tài khoản</span>
                <button aria-label="Edit account info" className="text-gray-600 hover:text-black" onClick={() => setShowEdit(true)}>
                    <i className="fas fa-pencil-alt"></i>
                </button>
                {showEdit && (
                    <EditUserForm
                        onClose={() => setShowEdit(false)}
                    />
                )}
                {showEditAccount && (
                    <EditAccountForm
                        name={editField?.name}
                        value={editField?.value}
                        onClose={() => setShowEditAccount(false)}
                    />
                )}
            </div>
            <div className="flex gap-6 items-center">
                <div className="relative w-24 h-24 rounded-full overflow-hidden group cursor-pointer" onClick={handleAvatarClick}>
                    <img
                        src={auth.user.avatar}
                        alt="User Avatar"
                        className="w-full h-full object-cover"
                    />
                    <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                        <i className="fas fa-camera text-white text-xl"></i>
                    </div>
                </div>
                <input
                    type="file"
                    accept="image/*"
                    ref={fileInputRef}
                    onChange={handleFileChange}
                    className="hidden"
                />
            <div>
                <h2 className="font-semibold text-lg mb-0 text-left">{auth.user.userName}</h2>
                <p className="text-xs text-left text-gray-500 mt-1">Tham gia: {formatDate(auth.user.joinDay)}</p>
                <button
                aria-label="User points"
                className="mt-3 bg-[#fff4d9] border border-[#f0c419] rounded-md px-3 py-1 text-sm font-bold flex items-center gap-1 text-[#a67c00]"
                >
                <i className="fas fa-award"></i> 0 điểm
                </button>
            </div>
            <div className="border border-gray-300 rounded-md px-4 py-3 text-green-500 flex items-center gap-1 text-lg font-semibold ml-auto min-w-[100px]">
                <i className="fas fa-suitcase-rolling"></i>
                <span>0</span>
                <span className="text-sm font-normal">chuyến</span>
            </div>
            </div>
            <div className="grid grid-cols-3 gap-4 text-gray-700 text-base">
            <div className="col-span-1">Ngày sinh</div>
            <div className="col-span-2 text-right font-semibold">{auth.user?.birthDay ? formatDate(auth.user.birthDay) : '--/--/----'}</div>

            <div className="col-span-1">Giới tính</div>
            <div className="col-span-2 text-right font-semibold">{auth.user?.gender === 1? "Nam": auth.user?.gender === 2? "Nữ": "----"}</div>

            <div className="col-span-1 flex items-center gap-1">
                Số điện thoại
                <span className={`ml-1 text-xs ${auth.account.phoneVerified ? 'bg-[#7ed6a3] text-[#1f4d2e]' : 'bg-[#f0c419] text-[#7a5900]'} rounded-full px-2 py-[2px] flex items-center gap-1`}>
                <i className={auth.account.phoneVerified ? "fas fa-check-circle text-xs" : "fas fa-exclamation-circle text-xs"}></i> {auth.account.phoneVerified ? "Đã xác thực" : "Chưa xác thực"}
                </span>
            </div>
            <div className="col-span-2 text-right font-semibold flex items-center justify-end gap-1">
                <span>{auth.account.phoneNumber ? auth.account.phoneNumber : 'Thêm số điện thoại'}</span>
                <button aria-label="Edit phone" onClick={() => {
                    setShowEditAccount(true);
                    setEditField({ name: "số điện thoại", value: 'phoneNumber' });
                }} className="text-gray-600 hover:text-black">
                <i className="fas fa-pencil-alt"></i>
                </button>
            </div>

            <div className="col-span-1 flex items-center gap-1">
                Email
                <span className={`ml-1 text-xs ${auth.account.emailVerified ? 'bg-[#7ed6a3] text-[#1f4d2e]' : 'bg-[#f0c419] text-[#7a5900]'} rounded-full px-2 py-[2px] flex items-center gap-1`}>
                <i className={auth.account.emailVerified ? "fas fa-check-circle text-xs" : "fas fa-exclamation-circle text-xs"}></i> {auth.account.emailVerified ? "Đã xác thực" : "Chưa xác thực"}
                </span>
            </div>
            <div className="col-span-2 text-right font-semibold flex items-center justify-end gap-1">
                <span>{auth.account.email ? auth.account.email : 'Thêm địa chỉ email'}</span>
                <button aria-label="Edit email" onClick={() => {
                    setShowEditAccount(true);
                    setEditField({ name: "email", value: 'email' });
                }} className="text-gray-600 hover:text-black">
                <i className="fas fa-pencil-alt"></i>
                </button>
            </div>

            <div className="col-span-1">Facebook</div>
            <div className="col-span-2 text-right font-semibold flex items-center justify-end gap-1">
                <span>Thêm liên kết</span>
                <button aria-label="Add Facebook" className="text-gray-600 hover:text-black">
                <i className={auth.account.linkedGoogle ? "fas fa-unlink" : "fas fa-pencil-alt"}></i>
                </button>
            </div>

            <div className="col-span-1">Google</div>
            <div className="col-span-2 text-right font-semibold flex items-center justify-end gap-1">
                <span>{auth.account.linkedGoogle ? auth.account.googleName : 'Thêm liên kết'}</span>
                <button aria-label="Remove Google" className="text-gray-600 hover:text-black">
                <i className={auth.account.linkedGoogle ? "fas fa-unlink" : "fas fa-pencil-alt"}></i>
                </button>
            </div>
            </div>
        </article>    
    )
}

export default Profile
