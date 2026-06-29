import { useState, useEffect } from "react";
import "./Navbar.css";
import { Link } from "react-router-dom";

function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 16);
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    document.body.style.overflow = isOpen ? "hidden" : "";
    return () => {
      document.body.style.overflow = "";
    };
  }, [isOpen]);

  const closeMenu = () => setIsOpen(false);

  return (
    <>
      <nav className={`navbar ${scrolled ? "navbar-scrolled" : ""}`}>
        <div className="navbar-container">
          <a href="/" className="brand" onClick={closeMenu}>
            <div className="brand-mark" aria-hidden="true">
              <span className="brand-mark-inner" />
            </div>
            <div className="brand-text">
              <span className="brand-name">SettleLater</span>
              <span className="brand-tag">Finance OS</span>
            </div>
          </a>

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
            <a href="#product" className="nav-link" onClick={closeMenu}>
              Product
            </a>
            <a href="#solutions" className="nav-link" onClick={closeMenu}>
              Solutions
            </a>
            <a href="#pricing" className="nav-link" onClick={closeMenu}>
              Pricing
            </a>
            <a href="#about" className="nav-link" onClick={closeMenu}>
              About
            </a>

            <div className="nav-actions">
              <button className="login-btn" onClick={closeMenu}>
                Login
              </button>
              {/* <button className="signup-btn" onClick={closeMenu}>
                Get Started
              </button> */}
              <Link className="signup-btn" onClick={closeMenu} to="/signup">Get Started</Link>
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