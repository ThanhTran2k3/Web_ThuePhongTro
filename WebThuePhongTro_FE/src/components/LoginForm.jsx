import React, {  useEffect, useState } from 'react'
import '../assets/css/base.css';
import { useAuth } from '../features/auth/useAuth';

const LoginFrom = ({ onClose, onSwitchToRegister, onSwitchToForgot  }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [loginData, setLoginData] = useState({ account: '', password: ''});
  const { login, authWithGoogle } = useAuth();
  const google_client_id = import.meta.env.VITE_GOOGLE_CLIENT_ID;

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(loginData, onClose);
      setLoginData({ account: '', password: '' });
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
        <h2>Đăng nhập</h2>
        <input type="text" placeholder="Số điện thoại hoặc email" required 
            value={loginData.account}
            onChange={(e) => setLoginData({ ...loginData, account: e.target.value })}
        />
        <div className="password-group">
          <div className="password-input">
            <input type={showPassword ? 'text' : 'password'} placeholder="Mật khẩu" required 
              value={loginData.password}
              onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
            />
            <i
              className={`fa-solid ${showPassword ? 'fa-eye-slash' : 'fa-eye'} toggle-password`}
              onClick={() => setShowPassword(!showPassword)}
            ></i>
          </div>
          <div className="forgot-password">
            <button onClick={onSwitchToForgot}>Quên mật khẩu?</button>
          </div>
        </div>
        <button className="login-btn">Đăng nhập</button>

        <p>Bạn chưa là thành viên? <button className='register-btn' onClick={onSwitchToRegister}>Đăng ký ngay</button></p>
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

  
  );
}

export default LoginFrom