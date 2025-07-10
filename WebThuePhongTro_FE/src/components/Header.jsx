import { useState } from "react";
import "../assets/css/base.css"
import LoginFrom from "./LoginForm";
import RegisterForm from "./RegisterForm";
import ForgotPass from "./ForgotPass";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

const Header = () => {
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [showForgot, setShowForgot] = useState(false);
  const auth = useSelector(state => state.auth); 
  return (
    <header>
      <div className="container header-inner" role="banner">
        <Link to={"/"} className="logo" tabIndex="0" aria-label="Logo Tro24H">
          Tro24H
        </Link>

        <div className="flex items-center space-x-4 p-4">
          <nav role="navigation" aria-label="Menu chính">
            <a href="#" tabIndex="0">Thuê phòng</a>
            <a href="#" tabIndex="0">Dịch vụ</a>
            <a href="#" tabIndex="0">Liên hệ</a>
          </nav>
          <span className="mx-2">|</span>

          {auth.isAuthenticated ? (
            <>
              <img 
                src={auth.user?.avatar} 
                alt="avatar" 
                className="w-10 h-10 rounded-full object-cover border" 
                referrerPolicy="no-referrer"
              />
              <Link to={"/account"} className="font-semibold text-black text-base">
                {auth.user.userName}
                <i class="fas fa-chevron-down text-xs ml-1"></i>
              </Link>
          
            </>
          ) : (
            <>
              <button
                onClick={() => setShowRegister(true)}
                className="font-semibold text-black text-base"
              >
                Đăng ký
              </button>

              <button
                onClick={() => setShowLogin(true)}
                className="font-semibold text-black text-base border border-black rounded px-2.5 py-1.5"
                type="button"
              >
                Đăng nhập
              </button>
            </>
          )}
        </div>
      </div>

      {showLogin && (
        <LoginFrom
          onClose={() => setShowLogin(false)}
          onSwitchToRegister={() => {
            setShowLogin(false);
            setShowRegister(true);
          }}
          onSwitchToForgot={() => {
            setShowLogin(false);
            setShowForgot(true);
          }}
        />
      )}

      {showRegister && <RegisterForm onClose={() => setShowRegister(false)} />}

      {showForgot && <ForgotPass onClose={() => setShowForgot(false)} />}
    </header>
  );
}

export default Header;
