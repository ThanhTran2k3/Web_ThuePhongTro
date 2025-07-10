import "../assets/css/base.css"


const Footer = () => {
  return (
   <footer role="contentinfo">
        <div className="footer-container">
            <section className="footer-section" aria-labelledby="footer-about">
              <h4 id="footer-about">Về Mioto</h4>
              <a href="#" tabIndex="0">Giới thiệu</a>
              <a href="#" tabIndex="0">Tuyển dụng</a>
              <a href="#" tabIndex="0">Tin tức</a>
            </section>
            <section className="footer-section" aria-labelledby="footer-support">
              <h4 id="footer-support">Hỗ trợ khách hàng</h4>
              <a href="#" tabIndex="0">Liên hệ</a>
              <a href="#" tabIndex="0">Chính sách</a>
              <a href="#" tabIndex="0">FAQ</a>
            </section>
            <section className="footer-section" aria-labelledby="footer-legal">
              <h4 id="footer-legal">Pháp lý</h4>
              <a href="#" tabIndex="0">Điều khoản sử dụng</a>
              <a href="#" tabIndex="0">Chính sách bảo mật</a>
            </section>
            <section className="footer-section" aria-labelledby="footer-social">
              <h4 id="footer-social">Kết nối với chúng tôi</h4>
              <div className="footer-social">
                  <a href="#" aria-label="Facebook" tabIndex="0"><span className="material-icons">facebook</span></a>
                  <a href="#" aria-label="Instagram" tabIndex="0"><span className="material-icons">camera_alt</span></a>
                  <a href="#" aria-label="YouTube" tabIndex="0"><span className="material-icons">play_circle_filled</span></a>
              </div>
            </section>
        </div>
    </footer>
  );
}

export default Footer;
