import React, { useState } from 'react'

const ForgotPass = ({ onClose }) => {
 const [inputValue, setInputValue] = useState('');

  const handleChange = (e) => {
    setInputValue(e.target.value);
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <button className="close-btn" onClick={onClose}>×</button>
        <h2>Quên mật khẩu</h2>
        <input type="text" placeholder="Số điện thoại hoặc email" value={inputValue} onChange={handleChange}/>
        <button className="login-btn" disabled={!inputValue.trim()}>Tiếp tục</button>
      </div>
    </div>

  );
}

export default ForgotPass