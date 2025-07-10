import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import Swal from 'sweetalert2';
import { useAuth } from '../features/auth/useAuth';


const EditAccountForm = ({name, value, onClose}) => {
  const auth = useSelector(state => state.auth); 
  const [ editData, setEditData] = useState();
  const [otp, setOtp] = useState('');
  const { editAccount } = useAuth();
  const [timeLeft, setTimeLeft] = useState(0);


  const handleSubmit = async (e) => {
      e.preventDefault();
      try {
          if (!editData) {
              Swal.fire({
                  title: "Lỗi",
                  text: "Vui lòng nhập đầy đủ thông tin",
                  icon: "error",
              });
              return;
          }
          const formData = new FormData();
          formData.append(value, editData);
          await editAccount(auth.token, formData);
          onClose();
      } catch (error) {
          console.error(error);
      }
    
  };

    useEffect(() => {
      if (timeLeft <= 0) return; 
  
          const timer = setInterval(() => {
              setTimeLeft((prevTime) => prevTime - 1);
            }, 1000);
        
            return () => clearInterval(timer); 
      
      
    }, [timeLeft]);

    const formatTime = (time) => {
      const minutes = Math.floor(time / 60);
      const seconds = time % 60;
      return `${minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;
    };

    const resendOTP = async ()=>{
      setTimeLeft(60)
    }


   return (
    <form onSubmit={handleSubmit} className="modal-overlay">
      <div className="modal">
        <button className="close-btn" type='button' onClick={onClose}>×</button>
        <h2>Cập nhật {name}</h2>
        <input type="text"  placeholder={name.charAt(0).toUpperCase() + name.slice(1)} required
            value={editData}
            onChange={(e) => setEditData(e.target.value )}
        />
      <div className="flex space-x-2">
        <input
          type="text"
          placeholder="OTP"
          required
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
          class="border border-gray-300 rounded-lg px-4 py-2 w-40 text-gray-400 placeholder-gray-400"
        />
        <button
           class="border border-gray-300 rounded-lg px-4 py-2 mb-4 text-green-500 font-bold text-center whitespace-nowrap">
          Xác thực
        </button>
      </div>
       {timeLeft!==0?(
          <h3 className='text-sm text-green-500 font-semibold rounded-md transition duration-200 mb-4'>Thời gian còn lại: {formatTime(timeLeft)}</h3>
        ):(
            <button onClick={resendOTP} type='button' className="text-sm text-green-500 font-semibold hover:bg-green-50 rounded-md transition duration-200 mb-4">
                Gửi mã
            </button>
        )}
   


        <button className="login-btn">Cập nhật</button>
      </div>
    </form>

  
  );
}

export default EditAccountForm