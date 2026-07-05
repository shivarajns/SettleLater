import { useState, useEffect } from "react";
import "./Navbar.css";
import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const handleScroll = () => {
      const shouldScroll = window.scrollY > 16;

      setScrolled(prev => {
        if (prev === shouldScroll) return prev;
        return shouldScroll;
      });
    };

    window.addEventListener("scroll", handleScroll, {
      passive: true
    });

    return () =>
      window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    document.body.style.overflow = isOpen ? "hidden" : "auto";

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);

  const closeMenu = () => setIsOpen(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsAuthenticated(false);
    closeMenu();
    navigate("/");
  };

  return (
    <>
      <nav className={`navbar ${scrolled ? "navbar-scrolled" : ""}`}>
        <div className="navbar-container">
          <Link to="/" className="brand" onClick={closeMenu}>
            <div className="brand-mark" aria-hidden="true">
              <span className="brand-mark-inner" />
            </div>

            <div className="brand-text">
              <span className="brand-name">SettleLater</span>
              <span className="brand-tag">Finance OS</span>
            </div>
          </Link>

          <button
            className={`hamburger ${isOpen ? "active" : ""}`}
            onClick={() => setIsOpen(!isOpen)}
            aria-label="Toggle navigation"
            aria-expanded={isOpen}
          >
            <span></span>
            <span></span>
            <span></span>
          </button>

          <div className={`nav-menu ${isOpen ? "open" : ""}`} id="main-nav">
            <div className="nav-actions">
              {isAuthenticated ? (
                <>
                  <Link
                    className="signup-btn"
                    to="/dashboard"
                    onClick={closeMenu}
                    style={{"textAlign":"center"}}
                  >
                    Dashboard
                  </Link>

                  <button
                    className="logout-btn"
                    onClick={handleLogout}
                  >
                    Logout
                  </button>
                </>
              ) : (
                <>
                  <Link
                    className="login-btn"
                    to="/login"
                    onClick={closeMenu}
                  >
                    Login
                  </Link>

                  <Link
                    className="signup-btn"
                    to="/signup"
                    onClick={closeMenu}
                  >
                    Signup
                  </Link>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>

      <div
        className={`mobile-backdrop ${isOpen ? "show" : ""}`}
        onClick={closeMenu}
      />
    </>
  );
}

export default Navbar;