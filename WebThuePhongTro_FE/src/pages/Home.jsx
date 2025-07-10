
import MainLayout from '../layouts/MainLayout';
import "../assets/css/home.css"
import SearchForm from '../components/SearchForm';

const Home = () => {

  return (
    <MainLayout>
        <main>
          <section className="hero" aria-label="Phần giới thiệu chính">
            <div className="container hero-content">
              <SearchForm />
            </div>
          </section>
          <section className="categories" aria-labelledby="car-categories-title">
            <h2 id="car-categories-title" className="section-title">Loại xe phổ biến</h2>
            <div className="container categories-grid">
              <article className="category-card" tabIndex="0" aria-label="Xe 4 chỗ tiện nghi">
                <img className="category-image" src="https://storage.googleapis.com/workspace-0f70711f-8b4e-4d94-86f1-2a93ccde5887/image/18ef1e2e-0d8f-4196-89cb-09a0630f041b.png" alt="Xe gia đình 4 chỗ tiện nghi, màu trắng bóng, đặt trên nền phố hiện đại" />
                <div className="category-content">
                  <h3 className="category-title">Xe 4 chỗ tiện nghi</h3>
                  <p className="category-desc">Dòng xe phổ biến, phù hợp với gia đình và nhóm nhỏ. Đảm bảo an toàn, tiện lợi.</p>
                  <button className="category-btn">Xem ngay</button>
                </div>
              </article>
              <article className="category-card" tabIndex="0" aria-label="Xe 7 chỗ đa dụng">
                <img className="category-image" src="https://storage.googleapis.com/workspace-0f70711f-8b4e-4d94-86f1-2a93ccde5887/image/89e31004-910c-49d2-8266-884b04cadfce.png" alt="Xe đa dụng 7 chỗ màu xanh đậm, chụp ở góc nghiêng trên đường phố" />
                <div className="category-content">
                  <h3 className="category-title">Xe 7 chỗ đa dụng</h3>
                  <p className="category-desc">Phù hợp nhóm đông người hoặc di chuyển xa. Không gian rộng rãi, thoải mái.</p>
                  <button className="category-btn">Xem ngay</button>
                </div>
              </article>
              <article className="category-card" tabIndex="0" aria-label="Xe sang trọng cao cấp">
                <img className="category-image" src="https://storage.googleapis.com/workspace-0f70711f-8b4e-4d94-86f1-2a93ccde5887/image/bdf225b6-7298-45ae-ada0-85c95a048ba1.png" alt="Xe sang trọng màu đen bóng bẩy, đỗ trong bãi đậu xe chất lượng cao" />
                <div className="category-content">
                  <h3 className="category-title">Xe sang trọng cao cấp</h3>
                  <p className="category-desc">Trải nghiệm đẳng cấp với các dòng xe hạng sang, phù hợp sự kiện và khách VIP.</p>
                  <button className="category-btn">Xem ngay</button>
                </div>
              </article>
            </div>
          </section>

          <section className="categories" aria-labelledby="featured-places-title">
              <h2 id="featured-places-title" className="section-title">
                Địa điểm nổi bật
              </h2>
              <div className="flex-center-gap">
                <div className="flex-gap">
                  <div className="card-container" tabIndex="0" aria-label="TP. Hồ Chí Minh">
                    <img
                      alt="TP. Hồ Chí Minh cityscape at sunset with tall buildings and a river with a bridge and train"
                      src="https://storage.googleapis.com/a1aa/image/2acda257-76f5-4a8f-98d5-9768e43be6a5.jpg"
                      className="image-style image-clip-top-right"
                      width="256"
                      height="400"
                    />
                    <div className="card-title">TP. Hồ Chí Minh</div>
                  </div>
                  <div className="card-container" tabIndex="0" aria-label="Hà Nội">
                    <img
                      alt="Hà Nội lake with temple and trees in background"
                      src="https://storage.googleapis.com/a1aa/image/dcc744ec-6b95-4aff-ce6c-e13dfd009d06.jpg"
                      className="image-style image-clip-top-right"
                      width="256"
                      height="400"
                    />
                    <div className="card-title">Hà Nội</div>
                  </div>
                  <div className="card-container" tabIndex="0" aria-label="Đà Nẵng">
                    <img
                      alt="Đà Nẵng city with bridge over river at sunset"
                      src="https://storage.googleapis.com/a1aa/image/0bfd3c21-cc54-466f-5aff-eb195a09a3f6.jpg"
                      className="image-style image-clip-top-right"
                      width="256"
                      height="400"
                    />
                    <div className="card-title">Đà Nẵng</div>
                  </div>
                  <div className="card-container" tabIndex="0" aria-label="Bình Dương">
                    <img
                      alt="Bình Dương government building with large square and sky at sunset"
                      src="https://storage.googleapis.com/a1aa/image/26332823-54f3-4769-a66b-a5550df6e965.jpg"
                      className="image-style image-clip-top-right"
                      width="256"
                      height="400"
                    />
                    <div className="card-title">Bình Dương</div>
                  </div>
                </div>
              </div>
          </section>

          <section className="features" aria-label="Các lợi ích dịch vụ">
            <div className="container">
              <div className="features-grid">
                <div className="feature-item" tabIndex="0">
                  <span className="material-icons feature-icon" aria-hidden="true">shield</span>
                  <h3 className="feature-title">An toàn tuyệt đối</h3>
                  <p className="feature-desc">Xe được kiểm tra định kỳ đảm bảo an toàn trên mọi hành trình.</p>
                </div>
                <div className="feature-item" tabIndex="0">
                  <span className="material-icons feature-icon" aria-hidden="true">local_offer</span>
                  <h3 className="feature-title">Giá cạnh tranh</h3>
                  <p className="feature-desc">Cam kết giá thuê xe hợp lý, cạnh tranh trên thị trường.</p>
                </div>
                <div className="feature-item" tabIndex="0">
                  <span className="material-icons feature-icon" aria-hidden="true">support_agent</span>
                  <h3 className="feature-title">Hỗ trợ 24/7</h3>
                  <p className="feature-desc">Đội ngũ chăm sóc khách hàng luôn sẵn sàng hỗ trợ bạn mọi lúc.</p>
                </div>
              </div>
            </div>
          </section>

        </main>

    </MainLayout>
  );
}

export default Home;
