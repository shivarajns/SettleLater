import "./Sidebar.css";
import { Link, NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Store,
  Users,
  ArrowLeftRight,
  Bell,
  BarChart3,
  Settings,
  User,
  LogOut,
  Crown,
} from "lucide-react";

function Sidebar({ onNavItemClick }) {
  const handleNavClick = () => {
    if (onNavItemClick) {
      onNavItemClick();
    }
  };

  return (
    <aside className="sidebar">
      <div className="sidebar-top">

        {/* LOGO */}
        <Link to="/" className="sidebar-brand">
          <div className="brand-mark">
            <span className="brand-mark-inner" />
          </div>

          <div className="brand-text">
            <span className="sidebar-brand-name">
              SettleLater
            </span>

            <span className="sidebar-brand-tag">
              Finance OS
            </span>
          </div>
        </Link>

        {/* NAVIGATION */}
        <nav className="sidebar-nav">

          <NavLink to="/dashboard" className="sidebar-link" onClick={handleNavClick}>
            <LayoutDashboard size={18} />
            <span>Dashboard</span>
          </NavLink>

          <NavLink to="/shops" className="sidebar-link" onClick={handleNavClick}>
            <Store size={18} />
            <span>Shops</span>
          </NavLink>

          <NavLink to="/customers" className="sidebar-link" onClick={handleNavClick}>
            <Users size={18} />
            <span>Customers</span>
          </NavLink>

          <NavLink to="/transactions" className="sidebar-link" onClick={handleNavClick}>
            <ArrowLeftRight size={18} />
            <span>Transactions</span>
          </NavLink>

          <NavLink to="/reminders" className="sidebar-link" onClick={handleNavClick}>
            <Bell size={18} />
            <span>Reminders</span>
          </NavLink>

          <NavLink to="/reports" className="sidebar-link" onClick={handleNavClick}>
            <BarChart3 size={18} />
            <span>Reports</span>
          </NavLink>

          <NavLink to="/settings" className="sidebar-link" onClick={handleNavClick}>
            <Settings size={18} />
            <span>Settings</span>
          </NavLink>

          <NavLink to="/profile" className="sidebar-link" onClick={handleNavClick}>
            <User size={18} />
            <span>Profile</span>
          </NavLink>

          <button type="button" className="sidebar-link logout-btn" onClick={handleNavClick}>
            <LogOut size={18} />
            <span>Logout</span>
          </button>

        </nav>
      </div>

      {/* UPGRADE CARD */}
      {/* <div className="upgrade-card">
        <div className="upgrade-icon">
          <Crown size={24} />
        </div>

        <h4>Stay on top of your business credit</h4>

        <button className="upgrade-btn">
          Upgrade Plan
        </button>
      </div> */}
    </aside>
  );
}

export default Sidebar;