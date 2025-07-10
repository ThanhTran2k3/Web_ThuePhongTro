import Footer from "../components/Footer";
import Header from "../components/Header";


function MainLayout({ children }) {
  return (
    <>
      <Header />
        <main>
            {children}
        </main>
      <Footer />
    </>
  );
}

export default MainLayout;
