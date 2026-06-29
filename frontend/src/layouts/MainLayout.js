import Navbar from "../components/navbar";

function MainLayout({ children }) {
  return (
    <>
      <Navbar />
      <main>{children}</main>
    </>
  );
}

export default MainLayout;