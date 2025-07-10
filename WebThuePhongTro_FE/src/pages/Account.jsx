import React, { useState } from 'react'
import { useAuth } from '../features/auth/useAuth';
import Profile from '../components/Profile';
import ChangePass from '../components/ChangePass';
import MainLayout from '../layouts/MainLayout';

const Account = () => {
    const [activeIndex, setActiveIndex] = useState(0); 
    const { logout } = useAuth();

    const menuItems = [
        { icon: "fas fa-user", label: "Tài khoản của tôi" },
        { icon: "far fa-heart", label: "Xe yêu thích" },
        { icon: "fa-solid fa-dollar-sign", label: "Nạp tiền" },
        { icon: "fa-solid fa-pen-to-square", label: "Đăng bài" },
        { icon: "fa-solid fa-table-list", label: "Quản lý bài đăng" },
        { icon: "fa-solid fa-file-invoice-dollar", label: "Lịch sử nạp tiền" },
        { icon: "far fa-address-card", label: "Địa chỉ của tôi" },
        { icon: "fas fa-lock", label: "Đổi mật khẩu" },
        { icon: "far fa-calendar-times", label: "Yêu cầu xoá tài khoản" },
    ];

   return (
        <MainLayout>
            <main className="max-w-[1200px] mx-auto px-4 py-10 flex flex-col md:flex-row gap-8 min-h-[calc(100vh-56px)] ">
                <div class="max-w-sm w-full text-base">
                    <h2 class="text-left text-black font-semibold text-lg mb-6">Xin chào bạn!</h2>
                    <ul class="space-y-4 text-gray-700 font-normal">
                        {menuItems.map((item, index) => (
                            <li
                                key={index}
                                onClick={() => setActiveIndex(index)}
                                className={`flex items-center gap-3 pl-3 rounded-md cursor-pointer transition 
                                ${activeIndex === index ? "bg-gray-200 font-semibold text-black border-green-500 border-l-4" : "hover:bg-gray-100"}`}
                            >
                                <i className={item.icon}></i>
                                <button>{item.label}</button>
                            </li>
                        ))}
                        <hr class="border-gray-300 my-3" />
                        <li class="flex items-center gap-3 text-red-600 font-semibold cursor-pointer pl-3">
                            <i class="fas fa-sign-out-alt"></i>
                            <button onClick={() => logout()}>Đăng xuất</button>
                        </li>
                    </ul>
                </div>


                <section className="flex-1 space-y-6">
                    {activeIndex === 0 && <Profile />}
                    {activeIndex === 7 && <ChangePass />}
                </section>
            </main>
        </MainLayout>
    )
}

export default Account