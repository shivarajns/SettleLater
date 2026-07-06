import "./DashboardLayout.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import DashNavbar from "../../components/DashboardNavbar/DashNavbar";

function DashboardLayout({ children }) {
  return (
    <div className="dashboard-layout">
      <Sidebar />

      <div className="dashboard-main">
        <DashNavbar />

        <main className="dashboard-content">
          {children}
        </main>
      </div>
    </div>
  );
}

export default DashboardLayout;