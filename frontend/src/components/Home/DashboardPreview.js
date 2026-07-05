import "../../pages/Home/home.css"

function DashboardPreview() {
  return (
    <div className="dashboard-preview">

      <div className="glass-card preview-main">
        <h4>Outstanding Balance</h4>

        <h2>₹54,300</h2>

        <span className="success-text">
          +12% this month
        </span>
      </div>

      <div className="glass-card preview-small customers-card">
        <h5>Customers</h5>
        <h3>143</h3>
      </div>

      <div className="glass-card preview-small due-card">
        <h5>Due Accounts</h5>
        <h3>12</h3>
      </div>

      <div className="glass-card preview-small payment-card">
        <h5>Payments</h5>
        <h3>₹32K</h3>
      </div>

    </div>
  );
}

export default DashboardPreview;