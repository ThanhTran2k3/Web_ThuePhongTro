import React, { useEffect, useState } from 'react'
import { useAuth } from '../features/auth/useAuth';


const RegisterForm = ({ onClose }) => {
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [isAccepted, setIsAccepted] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState('');
    const { register, authWithGoogle } = useAuth();
    const [registerData, setRegisterData] = useState({ phoneNumber: '', email: '', password: '', userName: '' });
    const google_client_id = import.meta.env.VITE_GOOGLE_CLIENT_ID;
    const isPasswordMatch = registerData.password && confirmPassword && registerData.password  === confirmPassword;

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register(registerData);
            setRegisterData({ phoneNumber: '', email: '', password: '', userName: '' });
            setConfirmPassword('');
            setIsAccepted(false);
            onClose();
        } catch (error) {
            console.error(error);
        }
       
    };

    const handleGoogleLogin = () => {
        const googleUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${google_client_id}&redirect_uri=http://localhost:8080/api/oauth2/google&response_type=code&scope=email profile`;

        window.open(
            googleUrl,
            '_blank',
            'width=1200,height=600,left=100,top=100'
        );
    };

    useEffect(() => {
        const handleMessage = async (event) => {
            const { token } = event.data;
            if (token) {
                try {
                    await authWithGoogle(token);
                    onClose(); 
                } catch (error) {
                    console.error(error);
                }
            }
        };

        window.addEventListener("message", handleMessage);
        return () => window.removeEventListener("message", handleMessage);
    });

    return (
        <form onSubmit={handleSubmit} className="modal-overlay">
            <div className="modal">
                <button className="close-btn" onClick={onClose}>×</button>
                <h2>Đăng ký</h2>
                <input type="text" placeholder="Số điện thoại" name="phoneNumber" required
                    pattern="^\d{10,11}$"
                    value={registerData.phoneNumber}
                    onChange={(e) => setRegisterData({ ...registerData, phoneNumber: e.target.value })}/>

                <input type="text" placeholder="Email" name="email" required
                    value={registerData.email}
                    pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                    onChange={(e) => setRegisterData({ ...registerData, email: e.target.value })}/>
                <input type="text" placeholder="Tên hiển thị" name="userName" required
                    value={registerData.userName}
                    onChange={(e) => setRegisterData({ ...registerData, userName: e.target.value })}/>
                <div className="password-group">
                    <div className="password-field">
                        <input type={showPassword ? 'text' : 'password'} placeholder="Mật khẩu" name="password" required
                            value={registerData.password}
                            onChange={(e) => setRegisterData({ ...registerData, password: e.target.value })}/>
                        <i
                            className={`fa-solid ${showPassword ? 'fa-eye-slash' : 'fa-eye'} eye-icon`}
                            onClick={() => setShowPassword(!showPassword)}
                        ></i>
                    </div>

                    <div className="password-field">
                        <input type={showConfirmPassword ? 'text' : 'password'} placeholder="Xác nhận mật khẩu" name="password" required
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value )}/>
                        <i
                            className={`fa-solid ${showConfirmPassword ? 'fa-eye-slash' : 'fa-eye'} eye-icon`}
                            onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                        ></i>
                    </div>
                </div>

                {!isPasswordMatch && confirmPassword && (
                    <label className='accept-label' style={{ color: 'red' }}>Mật khẩu không khớp</label>
                )}

                <label className="accept-label">
                    <input
                        type="checkbox"
                        checked={isAccepted}
                        onChange={(e) => setIsAccepted(e.target.checked)}
                    />
                    Tôi đồng ý với <a href="#">điều khoản sử dụng</a> và <a href="#">chính sách bảo mật</a>
                </label>
                <button className="login-btn" disabled={!isAccepted || !isPasswordMatch}>Đăng ký</button>

                <div className="social-login">
                <button className="fb">
                    <i className="fab fa-facebook-f"></i> Facebook
                </button>

        
                <button className="gg" onClick={handleGoogleLogin} type='button'>
                        <i className="fab fa-google"></i> Google
                    </button>
                </div>
            </div>
        </form>
    )
}

export default RegisterForm