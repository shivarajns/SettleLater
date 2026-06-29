import { useState } from "react";
import "./Signup.css";

import Loader from "../../components/Loader/Loader";
import Alert from "../../components/Alert/Alert";
import { registerUser } from "../../api/authApi";

function Signup() {
  const [loading, setLoading] = useState(false);

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [confirmPassword, setConfirmPassword] = useState("");

  const [alert, setAlert] = useState({
    type: "",
    message: "",
  });

  const [formData, setFormData] = useState({
    userName: "",
    email: "",
    phoneNumber: "",
    password: "",
  });

  const [touched, setTouched] = useState({
    userName: false,
    email: false,
    phoneNumber: false,
    password: false,
    confirmPassword: false,
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleBlur = (field) => {
    setTouched({
      ...touched,
      [field]: true,
    });
  };

  // =========================
  // PASSWORD STRENGTH (FIXED)
  // =========================
  const getPasswordStrength = () => {
    const password = formData.password;

    if (!password) {
      return {
        score: 0,
        text: "",
        className: "",
      };
    }

    let score = 0;

    const hasLower = /[a-z]/.test(password);
    const hasUpper = /[A-Z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecial = /[^A-Za-z0-9]/.test(password);
    const length = password.length;

    // length scoring (important FIX)
    if (length >= 6) score += 20;
    if (length >= 8) score += 20;
    if (length >= 10) score += 10;

    if (hasLower) score += 15;
    if (hasUpper) score += 15;
    if (hasNumber) score += 15;
    if (hasSpecial) score += 15;

    // cap at 100
    score = Math.min(score, 100);

    let text = "";
    let className = "";

    if (score === 0) {
      text = "";
      className = "";
    } else if (score <= 30) {
      text = "Weak";
      className = "weak";
    } else if (score <= 60) {
      text = "Fair";
      className = "fair";
    } else if (score <= 85) {
      text = "Good";
      className = "good";
    } else {
      text = "Strong";
      className = "strong";
    }

    return { score, text, className };
  };

  const strength = getPasswordStrength();

  // =========================
  // VALIDATIONS
  // =========================
  const usernameValid = formData.userName.trim().length >= 3;

  const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email);

  const phoneValid = /^[0-9]{10}$/.test(formData.phoneNumber);

  const passwordValid = formData.password.length >= 6;

  const passwordsMatch =
    confirmPassword.length > 0 &&
    formData.password === confirmPassword;

  const validateForm = () => {
    if (!usernameValid) return "Username must be at least 3 characters.";
    if (!emailValid) return "Please enter a valid email address.";
    if (!phoneValid) return "Enter a valid 10 digit phone number.";
    if (!passwordValid) return "Password must be at least 6 characters.";
    if (!passwordsMatch) return "Passwords do not match.";
    return null;
  };

  // =========================
  // SUBMIT
  // =========================
  const handleSubmit = async (e) => {
    e.preventDefault();

    setAlert({ type: "", message: "" });

    const error = validateForm();

    if (error) {
      setAlert({
        type: "error",
        message: error,
      });
      return;
    }

    try {
      setLoading(true);

      const response = await registerUser(formData);

      setAlert({
        type: "success",
        message: response.message || "Registration successful.",
      });

      setFormData({
        userName: "",
        email: "",
        phoneNumber: "",
        password: "",
      });

      setConfirmPassword("");

      setTouched({
        userName: false,
        email: false,
        phoneNumber: false,
        password: false,
        confirmPassword: false,
      });
    } catch (error) {
      setAlert({
        type: "error",
        message:
          error?.response?.data?.message ||
          error?.message ||
          "Something went wrong.",
      });
    } finally {
      setLoading(false);
    }
  };



  return (


    <div className="signup-page">

      <div className="ledger-bg">
        <svg
          viewBox="0 0 800 600"
          className="ledger-svg"
          xmlns="http://www.w3.org/2000/svg"
        >
          {/* Book base */}
          <path
            d="M150 120 Q400 40 650 120 L650 480 Q400 560 150 480 Z"
            fill="none"
            stroke="rgba(79,70,229,0.15)"
            strokeWidth="2"
          />

          {/* Left page */}
          <path
            d="M150 120 Q400 200 400 300 Q400 400 150 480 Z"
            fill="rgba(79,70,229,0.04)"
            stroke="rgba(79,70,229,0.12)"
            strokeWidth="1"
          />

          {/* Right page */}
          <path
            d="M650 120 Q400 200 400 300 Q400 400 650 480 Z"
            fill="rgba(6,182,212,0.04)"
            stroke="rgba(6,182,212,0.12)"
            strokeWidth="1"
          />

          {/* Ledger lines */}
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((i) => (
            <line
              key={i}
              x1="180"
              y1={140 + i * 35}
              x2="620"
              y2={140 + i * 35}
              stroke="rgba(100,116,139,0.12)"
              strokeWidth="1"
            />
          ))}

          {/* Center spine */}
          <line
            x1="400"
            y1="120"
            x2="400"
            y2="480"
            stroke="rgba(79,70,229,0.2)"
            strokeWidth="2"
          />
        </svg>
      </div>

      <div className="bg-orb orb1"></div>
      <div className="bg-orb orb2"></div>

      <div className="signup-layout">
        <section className="brand-section">
          <div className="logo-wrapper">
            <div className="logo-box">
              <span></span>
              <span></span>
              <span></span>
            </div>

            <h1>
              Settle<span>Later</span>
            </h1>
          </div>

          <h2>Your Traditional Khata, Reimagined Digitally.</h2>

          <p>
            Manage customer credits, track balances, and grow your business.
          </p>
        </section>

        <section className="form-section">
          <div className="signup-card">
            <div className="signup-header">
              <h2>Create Account</h2>
              <p>Start managing customer credits today.</p>
            </div>

            <Alert type={alert.type} message={alert.message} />

            <form onSubmit={handleSubmit}>
              <div className="input-group">
                <input
                  type="text"
                  name="userName"
                  placeholder=" "
                  value={formData.userName}
                  onChange={handleChange}
                  onBlur={() => handleBlur("userName")}
                />
                <label>Username</label>
              </div>

              <div className="input-group">
                <input
                  type="email"
                  name="email"
                  placeholder=" "
                  value={formData.email}
                  onChange={handleChange}
                  onBlur={() => handleBlur("email")}
                />
                <label>Email</label>
              </div>

              <div className="input-group">
                <input
                  type="tel"
                  name="phoneNumber"
                  placeholder=" "
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  onBlur={() => handleBlur("phoneNumber")}
                />
                <label>Phone</label>
              </div>

              {/* PASSWORD */}
              <div className="input-group">
                <div className="password-container">
                  <input
                    type={showPassword ? "text" : "password"}
                    name="password"
                    placeholder=" "
                    value={formData.password}
                    onChange={handleChange}
                    onBlur={() => handleBlur("password")}
                  />
                  <label>Password</label>

                  <button
                    type="button"
                    className="password-toggle"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? "🙈" : "👁"}
                  </button>
                </div>

                {/* FIXED BAR */}
                <div className="password-strength">
                  <div className="strength-track">
                    <div
                      className={`strength-fill ${strength.className}`}
                      style={{
                        width: `${strength.score}%`,
                      }}
                    />
                  </div>
                  <span>{strength.text}</span>
                </div>
              </div>

              {/* CONFIRM PASSWORD */}
              <div className="input-group">
                <div className="password-container">
                  <input
                    type={showConfirmPassword ? "text" : "password"}
                    placeholder=" "
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    onBlur={() => handleBlur("confirmPassword")}
                  />
                  <label>Confirm Password</label>

                  <button
                    type="button"
                    className="password-toggle"
                    onClick={() =>
                      setShowConfirmPassword(!showConfirmPassword)
                    }
                  >
                    {showConfirmPassword ? "🙈" : "👁"}
                  </button>
                </div>

                {confirmPassword && (
                  <small
                    className={
                      passwordsMatch ? "success-text" : "error-text"
                    }
                  >
                    {passwordsMatch
                      ? "✓ Passwords match"
                      : "✕ Passwords do not match"}
                  </small>
                )}
              </div>

              <button type="submit" className="signup-btn" disabled={loading}>
                {loading ? <Loader /> : "Create Account"}
              </button>

              <div className="signin-text">
                Already have an account? <span>Sign In</span>
              </div>
            </form>
          </div>
        </section>
      </div>
    </div>
  );
}

export default Signup;