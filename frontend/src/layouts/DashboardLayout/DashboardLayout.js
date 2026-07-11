import { useEffect, useState } from "react";
import "./DashboardLayout.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import DashNavbar from "../../components/Dashboard/DashNavbar";

function DashboardLayout({ children }) {
  const [isMobileView, setIsMobileView] = useState(() => {
    if (typeof window === "undefined") return false;
    return window.innerWidth <= 900;
  });

  const [isSidebarOpen, setIsSidebarOpen] = useState(() => {
    if (typeof window === "undefined") return true;
    return window.innerWidth > 900;
  });

  useEffect(() => {
    const handleResize = () => {
      const mobile = window.innerWidth <= 900;
      setIsMobileView(mobile);
      setIsSidebarOpen(!mobile);
    };

    handleResize();
    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const toggleSidebar = () => setIsSidebarOpen((prev) => !prev);
  const closeSidebar = () => setIsSidebarOpen(false);

  return (
    <div className={`dashboard-layout ${isMobileView && isSidebarOpen ? "sidebar-open" : ""}`}>
      {isMobileView && (
        <div
          className={`sidebar-overlay ${isSidebarOpen ? "visible" : ""}`}
          onClick={closeSidebar}
        />
      )}

      <Sidebar onNavItemClick={closeSidebar} />

      <div className="dashboard-main">
        <DashNavbar
          onMenuToggle={toggleSidebar}
          isSidebarOpen={isSidebarOpen}
          isMobileView={isMobileView}
        />

        <main className="dashboard-content">
          {children}
        </main>
      </div>
    </div>
  );
}

export default DashboardLayout;