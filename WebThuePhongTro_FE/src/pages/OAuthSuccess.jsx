import { useEffect } from 'react';


const OAuthSuccess = () => {
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    if (token && window.opener) {
      window.opener.postMessage({ token }, "*");
      window.close();
    }
  }, []);

  return <p>Đang xử lý đăng nhập...</p>;
};
export default OAuthSuccess;