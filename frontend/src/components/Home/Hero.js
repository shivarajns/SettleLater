import DashboardPreview from "./DashboardPreview";
import "../../pages/Home/home.css"
import { useState, useEffect } from "react";

function Hero() {

  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);

  return (
    <section className="hero-section">
      <div className="hero-content">

        <div className="hero-left">

          {isAuthenticated ? (
            <>
              <span className="hero-badge">
                Welcome Back
              </span>

              <h1>
                Welcome back
                {/* <br />
                {username} 👋 */}
              </h1>

              <p>
                Keep track of your customer credits,
                repayments, and outstanding balances
                from one dashboard.
              </p>

              <div className="hero-actions">
                <button className="dashboard-btn">
                  View Dashboard
                </button>
              </div>
            </>
          ) : (
            <>
              <span className="hero-badge">
                Smart Credit Management
              </span>

              <h1>
                Manage Customer Credit
                <br />
                Without The Notebook.
              </h1>

              <p>
                Track customer dues,
                repayments, and reminders
                digitally with SettleLater.
              </p>

              <div className="hero-actions">
                <button className="primary-btn">
                  Get Started
                </button>

                <button className="secondary-btn">
                  Login
                </button>
              </div>
            </>
          )}

        </div>

        <DashboardPreview />

      </div>
    </section>
  );
}

export default Hero;