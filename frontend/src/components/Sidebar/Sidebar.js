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

function Sidebar() {
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

          <NavLink to="/dashboard" className="sidebar-link">
            <LayoutDashboard size={18} />
            <span>Dashboard</span>
          </NavLink>

          <NavLink to="/shops" className="sidebar-link">
            <Store size={18} />
            <span>Shops</span>
          </NavLink>

          <NavLink to="/customers" className="sidebar-link">
            <Users size={18} />
            <span>Customers</span>
          </NavLink>

          <NavLink to="/transactions" className="sidebar-link">
            <ArrowLeftRight size={18} />
            <span>Transactions</span>
          </NavLink>

          <NavLink to="/reminders" className="sidebar-link">
            <Bell size={18} />
            <span>Reminders</span>
          </NavLink>

          <NavLink to="/reports" className="sidebar-link">
            <BarChart3 size={18} />
            <span>Reports</span>
          </NavLink>

          <NavLink to="/settings" className="sidebar-link">
            <Settings size={18} />
            <span>Settings</span>
          </NavLink>

          <NavLink to="/profile" className="sidebar-link">
            <User size={18} />
            <span>Profile</span>
          </NavLink>

          <button className="sidebar-link logout-btn">
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