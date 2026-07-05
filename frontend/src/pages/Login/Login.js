import { useState } from "react";
import "./Login.css";

import Loader from "../../components/Loader/Loader";
import Alert from "../../components/Alert/Alert";

import { loginUser } from "../../api/authApi";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";


function Login() {

  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);

  const [showPassword, setShowPassword] = useState(false);

  const [alert, setAlert] = useState({
    type: "",
    message: "",
  });

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [touched, setTouched] = useState({
    email: false,
    password: false,
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

  const emailValid =
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email);

  const passwordValid =
    formData.password.trim().length > 0;

  const validateForm = () => {
    if (!emailValid)
      return "Please enter a valid email address.";

    if (!passwordValid)
      return "Password is required.";

    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setAlert({
      type: "",
      message: "",
    });

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

      const response = await loginUser(formData);

      if (response?.emailVerified === false) {
        navigate('/verify-email');
      }

      if(response?.emailVerified === true){
        navigate('/home')
      }


      setAlert({
        type: "success",
        message:
          response.message || "Login successful.",
      });


      localStorage.setItem("token", response.token);
      window.location.reload();

    } catch (error) {
      setAlert({
        type: "error",
        message:
          error?.response?.data?.message ||
          "Invalid email or password.",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">

      <div className="ledger-bg">
        <svg
          viewBox="0 0 800 600"
          className="ledger-svg"
          xmlns="http://www.w3.org/2000/svg"
        >

          <path
            d="M150 120 Q400 40 650 120 L650 480 Q400 560 150 480 Z"
            fill="none"
            stroke="rgba(79,70,229,0.15)"
            strokeWidth="2"
          />

          <path
            d="M150 120 Q400 200 400 300 Q400 400 150 480 Z"
            fill="rgba(79,70,229,0.04)"
            stroke="rgba(79,70,229,0.12)"
            strokeWidth="1"
          />

          <path
            d="M650 120 Q400 200 400 300 Q400 400 650 480 Z"
            fill="rgba(6,182,212,0.04)"
            stroke="rgba(6,182,212,0.12)"
            strokeWidth="1"
          />

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

      <div className="login-layout">

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

          <h2>Welcome Back.</h2>

          <p>
            Access your digital credit ledger and
            manage customer balances seamlessly.
          </p>
        </section>

        <section className="form-section">
          <div className="login-card">

            <div className="login-header">
              <h2>Sign In</h2>
              <p>
                Continue managing customer credits.
              </p>
            </div>

            <Alert
              type={alert.type}
              message={alert.message}
            />

            <form onSubmit={handleSubmit}>

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
                <div className="password-container">

                  <input
                    type={
                      showPassword
                        ? "text"
                        : "password"
                    }
                    name="password"
                    placeholder=" "
                    value={formData.password}
                    onChange={handleChange}
                    onBlur={() =>
                      handleBlur("password")
                    }
                  />

                  <label>Password</label>

                  <button
                    type="button"
                    className="password-toggle"
                    onClick={() =>
                      setShowPassword(
                        !showPassword
                      )
                    }
                  >
                    {showPassword
                      ? "🙈"
                      : "👁"}
                  </button>

                </div>
              </div>

              <button
                type="submit"
                className="login-btn"
                disabled={loading}
              >
                {loading ? (
                  <Loader />
                ) : (
                  "Sign In"
                )}
              </button>

              <Link className="signin-text" to="/signup">
                Don't have an account?
                <span> Create Account</span>
              </Link>

            </form>

          </div>
        </section>

      </div>
    </div>
  );
}

export default Login;