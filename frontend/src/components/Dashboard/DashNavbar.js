import "./DashNavbar.css";
import { Bell, Menu, X } from "lucide-react";
import { useAuth } from "../../context/AuthContext";

function DashNavbar({ onMenuToggle, isSidebarOpen, isMobileView }) {

 const {user} = useAuth();

  return (
    <header className="dash-navbar">

      <div className="dash-navbar-left">
        {isMobileView && (
          <button
            type="button"
            className="mobile-menu-btn"
            aria-label={isSidebarOpen ? "Close sidebar" : "Open sidebar"}
            onClick={onMenuToggle}
          >
            {isSidebarOpen ? <X size={20} /> : <Menu size={20} />}
          </button>
        )}
      </div>

      <div className="dash-navbar-right">

        <button className="notification-btn">
          <Bell size={20} />
          <span className="notification-badge">3</span>
        </button>

        <div className="profile-section">

          <div className="profile-avatar">
            {user?.name?.toUpperCase()?.substring(0,2)}
          </div>

          <div className="profile-info">
            <h4>{user?.name}</h4>
            <span>Owner</span>
          </div>

        </div>

      </div>

    </header>
  );
}

export default DashNavbar;