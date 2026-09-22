import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getAllCredits } from "../Credit/creditService";
import "./CreditView.css"

function CreditView() {
    const location = useLocation();
    const navigate = useNavigate();

    const {
        shopId,
        customerId,
        customerName,
    } = location.state || {};

    const [credits, setCredits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const handleViewDetails = (credit) => {
        navigate("/credit-details", {
            state: {
                credit,
                shopId,
                customerId,
                customerName,
            },
        });
    };

    useEffect(() => {
        const loadCredits = async () => {
            try {
                setLoading(true);
                setError("");

                const response = await getAllCredits(
                    shopId,
                    customerId
                );

                console.log("Credits response:", response);

                setCredits(response.data || []);

            } catch (error) {
                console.error("Failed to fetch credits:", error);

                setError(
                    error?.response?.data?.message ||
                    "Failed to load credits."
                );
            } finally {
                setLoading(false);
            }
        };

        if (shopId && customerId) {
            loadCredits();
        } else {
            setLoading(false);
            setError("Customer information is missing.");
        }
    }, [shopId, customerId]);

    if (loading) {
        return <div>Loading credits...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    const totalCredit = credits.reduce(
        (total, credit) =>
            total + Number(credit.originalAmount || 0),
        0
    );

    const totalOutstanding = credits.reduce(
        (total, credit) =>
            total + Number(credit.outstandingAmount || 0),
        0
    );

    const totalPaid = totalCredit - totalOutstanding;

    const activeCredits = credits.filter(
        (credit) =>
            Number(credit.outstandingAmount || 0) > 0
    ).length;

    const settledCredits = credits.filter(
        (credit) =>
            Number(credit.outstandingAmount || 0) === 0
    ).length;

    return (
        <div className="credit-view-page">

            <div className="credit-view-header">

                <button
                    type="button"
                    className="credit-back-btn"
                    onClick={() => window.history.back()}
                >
                    ← Back to Customers
                </button>

                <div className="credit-customer-header">
                    <div>
                        <h1>{customerName}</h1>
                        <p>Credit account overview</p>
                    </div>

                    <div className="credit-customer-meta">
                        <span>Customer ID</span>
                        <strong>{customerId}</strong>
                    </div>
                </div>

            </div>

            <div className="credit-summary">
                <div className="credit-summary-header">
                    <div>
                        <h2>Credit Summary</h2>
                        <p>Overview of this customer's credit activity</p>
                    </div>
                </div>

                <div className="credit-summary-grid">

                    <div className="credit-summary-card">
                        <span>Total Credit</span>
                        <strong>
                            ₹{totalCredit.toLocaleString("en-IN")}
                        </strong>
                    </div>

                    <div className="credit-summary-card">
                        <span>Outstanding</span>
                        <strong>
                            ₹{totalOutstanding.toLocaleString("en-IN")}
                        </strong>
                    </div>

                    <div className="credit-summary-card">
                        <span>Total Paid</span>
                        <strong>
                            ₹{totalPaid.toLocaleString("en-IN")}
                        </strong>
                    </div>

                    <div className="credit-summary-card">
                        <span>Active Credits</span>
                        <strong>{activeCredits}</strong>
                    </div>

                    <div className="credit-summary-card">
                        <span>Settled Credits</span>
                        <strong>{settledCredits}</strong>
                    </div>

                </div>
            </div>

            <div className="credits-list-section">

                <div className="credits-list-header">
                    <div>
                        <h2>Your Credits</h2>
                        <p>All credit records for this customer</p>
                    </div>

                    <span className="credit-count">
                        {credits.length}{" "}
                        {credits.length === 1 ? "Credit" : "Credits"}
                    </span>
                </div>

                {credits.length === 0 ? (
                    <div className="no-credits">
                        <h3>No credits found</h3>
                        <p>
                            This customer does not have any credit records yet.
                        </p>
                    </div>
                ) : (
                    <div className="credits-grid">
                        {credits.map((credit) => (
                            <div
                                className="credit-card"
                                key={credit.creditId}
                            >

                                <div className="credit-card-top">

                                    <div>
                                        <span className="credit-label">
                                            Credit
                                        </span>

                                        <h3>
                                            ₹{Number(
                                                credit.originalAmount
                                            ).toLocaleString("en-IN")}
                                        </h3>
                                    </div>

                                    <span
                                        className={`credit-status ${credit.status
                                            ?.toLowerCase()
                                            .replace("_", "-")}`}
                                    >
                                        {credit.status}
                                    </span>

                                </div>

                                <div className="credit-outstanding">
                                    <span>Outstanding</span>

                                    <strong>
                                        ₹{Number(
                                            credit.outstandingAmount
                                        ).toLocaleString("en-IN")}
                                    </strong>
                                </div>

                                <div className="credit-card-details">

                                    <div>
                                        <span>Credit Date</span>
                                        <strong>{credit.creditDate}</strong>
                                    </div>

                                    <div>
                                        <span>Due Date</span>
                                        <strong>{credit.dueDate}</strong>
                                    </div>

                                </div>

                                {credit.description && (
                                    <div className="credit-description">
                                        <span>Description</span>
                                        <p>{credit.description}</p>
                                    </div>
                                )}

                                <div className="credit-card-footer">

                                    <span className="credit-id">
                                        ID: {credit.creditId}
                                    </span>

                                    <button
                                        type="button"
                                        className="credit-details-btn"
                                        onClick={()=> handleViewDetails(credit)}
                                    >
                                        View Details
                                    </button>

                                </div>

                            </div>
                        ))}
                    </div>
                )}

            </div>

        </div>
    );
}

export default CreditView;