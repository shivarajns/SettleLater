import "./EmailVerification.css";

import { useState } from "react";
import Alert from "../../components/Alert/Alert";
import Loader from "../../components/Loader/Loader";
import { checkEmailVerification, resendVerificationEmail } from "../../api/authApi";
import { useNavigate } from "react-router-dom";

function EmailVerification() {
  const [loading, setLoading] = useState(false);
  const [resendLoading, setResendLoading] = useState(false);

  const navigate = useNavigate();

  const [alert, setAlert] = useState({
    type: "",
    message: "",
  });

  const handleVerifiedClick = async () => {
    setAlert({
      type: "",
      message: "",
    });

    try {
      setLoading(true);

      const response =
        await checkEmailVerification();

        console.log(response)

      setAlert({
        type: response.isVerified
          ? "success"
          : "error",
        message:
          response.message ||
          (response.isVerified
            ? "Email verified successfully."
            : "Email verification is pending."),
      });

      if (response.verified) {
        navigate("/home");
      }
    } catch (error) {
      setAlert({
        type: "error",
        message:
          error?.response?.data?.message ||
          error?.message ||
          "Unable to verify email status.",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleResendVerification = async () => {
    setAlert({
      type: "",
      message: "",
    });

    try {
      setResendLoading(true);

      const response = await resendVerificationEmail();

      setAlert({
        type: "success",
        message:
          response.message ||
          "Verification email sent successfully.",
      });
    } catch (error) {
      setAlert({
        type: "error",
        message:
          error?.response?.data?.message ||
          error?.message ||
          "Unable to resend verification email.",
      });
    } finally {
      setResendLoading(false);
    }
  };

  return (
    <div className="verify-page">

      {/* Background Ledger SVG */}
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
            stroke="rgba(79,70,229,0.20)"
            strokeWidth="2"
          />
        </svg>
      </div>

      <div className="bg-orb orb1"></div>
      <div className="bg-orb orb2"></div>

      <div className="verify-layout">

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

          <h2>Almost There.</h2>

          <p>
            Secure your account by verifying your email
            address before continuing.
          </p>
        </section>

        <section className="form-section">

          <div className="verify-card">

            <div className="mail-icon-wrapper">
              <svg
                className="mail-svg"
                viewBox="0 0 120 120"
                xmlns="http://www.w3.org/2000/svg"
              >
                <circle
                  cx="60"
                  cy="60"
                  r="55"
                  fill="rgba(79,70,229,0.08)"
                />

                <rect
                  x="25"
                  y="35"
                  width="70"
                  height="50"
                  rx="8"
                  fill="white"
                  stroke="#4f46e5"
                  strokeWidth="2"
                />

                <path
                  d="M25 40 L60 65 L95 40"
                  fill="none"
                  stroke="#4f46e5"
                  strokeWidth="2"
                />

                <circle
                  cx="90"
                  cy="30"
                  r="10"
                  fill="#10b981"
                />

                <path
                  d="M85 30 L89 34 L96 26"
                  fill="none"
                  stroke="white"
                  strokeWidth="2"
                  strokeLinecap="round"
                />
              </svg>
            </div>

            <div className="verify-header">
              <h2>Verify Your Email</h2>

              <p>
                We've sent a verification link to your
                registered email address.
              </p>

              <p className="verify-note">
                Please check your inbox and spam folder,
                then click the verification link to
                activate your account.
              </p>
            </div>

            <Alert
              type={alert.type}
              message={alert.message}
            />

            <div className="verify-actions">

              <button
                className="primary-btn"
                onClick={handleVerifiedClick}
                disabled={loading}
              >
                {loading ? (
                  <Loader />
                ) : (
                  "I Verified My Email"
                )}
              </button>

              <button
                className="secondary-btn"
                onClick={handleResendVerification}
                disabled={resendLoading}
              >
                {resendLoading ? (
                  <Loader />
                ) : (
                  "Resend Verification Link"
                )}
              </button>

            </div>

          </div>

        </section>

      </div>

    </div>
  );
}

export default EmailVerification;