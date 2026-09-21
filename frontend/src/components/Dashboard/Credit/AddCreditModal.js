import { useState } from "react";
import { X } from "lucide-react";
import { createCredit } from "../Credit/creditService";
import "./AddCreditModal.css";

function AddCreditModal({
    isOpen,
    onClose,
    customer,
    onSuccess,
}) {
    const [amount, setAmount] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [description, setDescription] = useState("");
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState("");

    if (!isOpen || !customer) {
        return null;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            setSubmitting(true);
            setError("");

            const creditData = {
                amount: Number(amount),
                dueDate,
                description: description.trim() || null,
            };

            const response = await createCredit(
                customer.shopId,
                customer.customerId,
                creditData
            );

            console.log("Credit created:", response);

            onSuccess(
                `Credit of ₹${amount} added successfully to ${customer.customerName}.`
            );

            setAmount("");
            setDueDate("");
            setDescription("");

            onClose();

        } catch (error) {
            console.error("Failed to create credit:", error);

            const message =
                error?.response?.data?.message ||
                "Failed to create credit. Please try again.";

            setError(message);

        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="credit-modal-overlay">
            <div className="credit-modal">

                {/* Header */}
                <div className="credit-modal-header">
                    <div>
                        <h2>Add Credit</h2>
                        <p>Create a new credit for this customer</p>
                    </div>

                    <button
                        type="button"
                        className="credit-modal-close"
                        onClick={onClose}
                    >
                        <X size={20} />
                    </button>
                </div>

                {/* Customer Information */}
                <div className="credit-customer-info">

                    <div>
                        <span>Customer</span>
                        <strong>
                            {customer.customerName}
                        </strong>
                    </div>

                    <div>
                        <span>Shop</span>
                        <strong>
                            {customer.ShopName}
                        </strong>
                    </div>

                </div>

                {/* Form */}
                <form onSubmit={handleSubmit}>

                    {/* Amount */}
                    <div className="credit-form-group">
                        <label>
                            Credit Amount
                        </label>

                        <input
                            type="number"
                            min="0.01"
                            step="0.01"
                            placeholder="Enter credit amount"
                            value={amount}
                            onChange={(e) =>
                                setAmount(e.target.value)
                            }
                            required
                        />
                    </div>

                    {/* Due Date */}
                    <div className="credit-form-group">
                        <label>
                            Due Date
                        </label>

                        <input
                            type="date"
                            value={dueDate}
                            onChange={(e) =>
                                setDueDate(e.target.value)
                            }
                            required
                        />
                    </div>

                    {/* Description */}
                    <div className="credit-form-group">
                        <label>
                            Description
                        </label>

                        <textarea
                            placeholder="Example: Grocery items"
                            maxLength={500}
                            value={description}
                            onChange={(e) =>
                                setDescription(e.target.value)
                            }
                            rows={3}
                        />
                    </div>

                    {error && (
                        <div className="credit-form-error">
                            {error}
                        </div>
                    )}

                    {/* Actions */}
                    <div className="credit-modal-actions">

                        <button
                            type="button"
                            className="credit-cancel-btn"
                            onClick={onClose}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="credit-submit-btn"
                            disabled={submitting}
                        >
                            {submitting ? "Creating..." : "Add Credit"}
                        </button>

                    </div>

                </form>
            </div>
        </div>
    );
}

export default AddCreditModal;