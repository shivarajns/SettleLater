import "./DashNavbar.css";
import { Bell } from "lucide-react";

function DashNavbar() {
  return (
    <header className="dash-navbar">

      <div />

      <div className="dash-navbar-right">

        <button className="notification-btn">
          <Bell size={20} />
          <span className="notification-badge">3</span>
        </button>

        <div className="profile-section">

          <div className="profile-avatar">
            RK
          </div>

          <div className="profile-info">
            <h4>Rakesh Kumar</h4>
            <span>Owner</span>
          </div>

        </div>

      </div>

    </header>
  );
}

export default DashNavbar;