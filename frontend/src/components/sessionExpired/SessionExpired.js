import { useNavigate } from "react-router-dom";
import "./SessionExpired.css";

function SessionExpired() {
  const navigate = useNavigate();

  return (
    <div className="session-container">
      <div className="session-card">
        <div className="lock-icon">🔒</div>

        <h1>Session Expired</h1>

        <p>
          Your login session has expired for security reasons.
          Please sign in again to continue managing your
          customers, credit records, and repayments.
        </p>

        <button
          className="login-btn"
          onClick={() => navigate("/login")}
        >
          Login Again
        </button>

        <button
          className="home-btn"
          onClick={() => navigate("/")}
        >
          Go to Home
        </button>
      </div>
    </div>
  );
}

export default SessionExpired;