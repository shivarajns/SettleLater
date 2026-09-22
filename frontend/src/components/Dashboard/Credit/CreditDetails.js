import { useLocation, useNavigate } from "react-router-dom";
import {
  ArrowLeft,
  Copy,
  CreditCard,
  CalendarDays,
  FileText,
  Tag,
  CheckCircle2,
  CircleDollarSign,
  WalletCards,
  PieChart,
} from "lucide-react";

import "./CreditDetails.css";

function CreditDetails() {
  const location = useLocation();
  const navigate = useNavigate();

  const {
    credit,
    customerName,
    shopId,
    customerId,
  } = location.state || {};

  if (!credit) {
    return (
      <div className="credit-details-page">
        <div className="credit-details-empty">
          <h2>Credit information is not available</h2>

          <button
            type="button"
            onClick={() => navigate(-1)}
          >
            Back
          </button>
        </div>
      </div>
    );
  }

  const originalAmount = Number(
    credit.originalAmount || 0
  );

  const outstandingAmount = Number(
    credit.outstandingAmount || 0
  );

  const paidAmount =
    originalAmount - outstandingAmount;

  const formattedAmount = (amount) =>
    amount.toLocaleString("en-IN");

  const customerInitials = customerName
    ? customerName
        .split(" ")
        .map((name) => name[0])
        .join("")
        .slice(0, 2)
        .toUpperCase()
    : "CU";

  const handleCopy = async (value) => {
    try {
      await navigator.clipboard.writeText(value);
    } catch (error) {
      console.error("Failed to copy:", error);
    }
  };

  return (
    <div className="credit-details-page">

      {/* ================= HEADER ================= */}

      <div className="credit-details-container">

        <button
          type="button"
          className="credit-details-back-btn"
          onClick={() => navigate(-1)}
        >
          <ArrowLeft size={17} />
          Back to Credits
        </button>

        <div className="credit-customer-banner">

          <div className="credit-customer-info">

            <div className="credit-customer-avatar">
              {customerInitials}
            </div>

            <div className="credit-customer-content">

              <h1>{customerName}</h1>

              <div className="credit-shop-name">
                <CreditCard size={16} />
                Shop ID: {shopId}
              </div>

              <div className="credit-customer-id">

                <span>
                  Customer ID: {customerId}
                </span>

                <button
                  type="button"
                  onClick={() =>
                    handleCopy(customerId)
                  }
                  title="Copy customer ID"
                >
                  <Copy size={14} />
                </button>

              </div>

            </div>

          </div>

          <div className="credit-id-card">

            <span>Credit ID</span>

            <div>
              <strong>{credit.creditId}</strong>

              <button
                type="button"
                onClick={() =>
                  handleCopy(credit.creditId)
                }
                title="Copy credit ID"
              >
                <Copy size={15} />
              </button>
            </div>

          </div>

        </div>

        {/* ================= PAGE TITLE ================= */}

        <div className="credit-details-title">

          <h2>Credit Details</h2>

          <p>
            Detailed information about this credit
            and payment activity
          </p>

        </div>

        {/* ================= CREDIT OVERVIEW ================= */}

        <section className="credit-overview-card">

          <div className="credit-overview-header">

            <div className="credit-overview-icon">
              <PieChart size={22} />
            </div>

            <div>
              <h3>Credit Overview</h3>

              <p>
                Key information about this credit
              </p>
            </div>

          </div>

          {/* Amount Summary */}

          <div className="credit-overview-stats">

            {/* Original Amount */}

            <div className="credit-overview-stat">

              <div className="credit-stat-heading">
                <span>Original Amount</span>
              </div>

              <div className="credit-stat-value original">
                ₹{formattedAmount(originalAmount)}

                <div className="credit-stat-icon">
                  <CircleDollarSign size={19} />
                </div>
              </div>

            </div>

            {/* Outstanding */}

            <div className="credit-overview-stat">

              <div className="credit-stat-heading">
                <span>Outstanding Amount</span>
              </div>

              <div className="credit-stat-value outstanding">
                ₹{formattedAmount(outstandingAmount)}

                <div className="credit-stat-icon">
                  <WalletCards size={19} />
                </div>
              </div>

            </div>

            {/* Paid */}

            <div className="credit-overview-stat">

              <div className="credit-stat-heading">
                <span>Paid Amount</span>
              </div>

              <div className="credit-stat-value paid">
                ₹{formattedAmount(paidAmount)}

                <div className="credit-stat-icon">
                  <CheckCircle2 size={19} />
                </div>
              </div>

            </div>

            {/* Status */}

            <div className="credit-overview-stat">

              <div className="credit-stat-heading">
                <span>Status</span>
              </div>

              <div className="credit-status-wrapper">

                <span
                  className={`credit-details-status ${credit.status
                    ?.toLowerCase()
                    .replace("_", "-")}`}
                >
                  {credit.status}
                </span>

              </div>

            </div>

          </div>

          {/* Divider */}

          <div className="credit-overview-divider" />

          {/* Credit Information */}

          <div className="credit-overview-details">

            {/* Credit Date */}

            <div className="credit-detail-item">

              <div className="credit-detail-icon">
                <CalendarDays size={20} />
              </div>

              <div>
                <span>Credit Date</span>
                <strong>{credit.creditDate}</strong>
              </div>

            </div>

            {/* Due Date */}

            <div className="credit-detail-item">

              <div className="credit-detail-icon">
                <CalendarDays size={20} />
              </div>

              <div>
                <span>Due Date</span>
                <strong>{credit.dueDate}</strong>
              </div>

            </div>

            {/* Description */}

            <div className="credit-detail-item">

              <div className="credit-detail-icon">
                <FileText size={20} />
              </div>

              <div>
                <span>Description</span>

                <strong>
                  {credit.description ||
                    "No description provided"}
                </strong>
              </div>

            </div>

            {/* Credit Reference */}

            <div className="credit-detail-item">

              <div className="credit-detail-icon">
                <Tag size={20} />
              </div>

              <div>
                <span>Credit Reference</span>
                <strong>{credit.creditId}</strong>
              </div>

            </div>

          </div>

        </section>

      </div>

    </div>
  );
}

export default CreditDetails;