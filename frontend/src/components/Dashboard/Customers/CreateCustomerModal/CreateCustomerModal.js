import { useEffect, useState } from "react";
import "./CreateCustomerModal.css";

import { X } from "lucide-react";

import {
    getAllShops
} from "../../Dashboard/ShopService";

import { createCustomer } from "../CustomerService";

function CreateCustomerModal({
    isOpen,
    onClose,
    onCustomerCreated,
}) {
    const [shops, setShops] =
        useState([]);

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    const [formData, setFormData] =
        useState({
            shopId: "",
            customerName: "",
            phone: "",
            email: "",
            address: "",
        });

    useEffect(() => {
        if (isOpen) {
            loadShops();
        }
    }, [isOpen]);

    const loadShops = async () => {
        try {
            const data = await getAllShops();

            setShops(data || []);

            if (data?.length === 1) {
                setFormData((prev) => ({
                    ...prev,
                    shopId: data[0].shopId,
                }));
            }
        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]:
                e.target.value,
        });

        setError("");
    };

    const handleSubmit = async (
        e
    ) => {
        e.preventDefault();

        if (!formData.shopId) {
            setError(
                "Please select a shop."
            );
            return;
        }

        if (
            !formData.customerName.trim()
        ) {
            setError(
                "Customer name is required."
            );
            return;
        }

        if (
            !formData.phone.trim()
        ) {
            setError(
                "Phone number is required."
            );
            return;
        }

        try {
            setLoading(true);

            await createCustomer(
                formData.shopId,
                {
                    customerName:
                        formData.customerName,
                    phone:
                        formData.phone,
                    email:
                        formData.email,
                    address:
                        formData.address,
                }
            );

            setFormData({
                shopId: "",
                customerName: "",
                phone: "",
                email: "",
                address: "",
            });

            onCustomerCreated();
            onClose();

        } catch (error) {

            console.log(error);

            setError(
                error?.response?.data
                    ?.message ||
                "Failed to create customer."
            );

        } finally {
            setLoading(false);
        }
    };

    if (!isOpen) return null;

    return (
        <div className="modal-overlay">

            <div className="customer-modal">

                {/* HEADER */}

                <div className="modal-header">

                    <div>
                        <h2>
                            Create Customer
                        </h2>

                        <p>
                            Add a customer to
                            your shop
                        </p>
                    </div>

                    <button
                        className="close-btn"
                        onClick={onClose}
                    >
                        <X size={18} />
                    </button>

                </div>

                <form
                    onSubmit={
                        handleSubmit
                    }
                >

                    {error && (
                        <div className="form-error">
                            {error}
                        </div>
                    )}

                    {/* SHOP */}

                    <div className="form-group">

                        <label>
                            Shop *
                        </label>

                        <select
                            name="shopId"
                            value={
                                formData.shopId
                            }
                            onChange={
                                handleChange
                            }
                        >
                            <option value="">
                                Select Shop
                            </option>

                            {shops.map(
                                (shop) => (
                                    <option
                                        key={
                                            shop.shopId
                                        }
                                        value={
                                            shop.shopId
                                        }
                                    >
                                        {shop.name}
                                    </option>
                                )
                            )}
                        </select>

                    </div>

                    {/* NAME */}

                    <div className="form-group">

                        <label>
                            Customer Name *
                        </label>

                        <input
                            type="text"
                            name="customerName"
                            value={
                                formData.customerName
                            }
                            onChange={
                                handleChange
                            }
                            placeholder="Enter customer name"
                        />

                    </div>

                    {/* PHONE */}

                    <div className="form-group">

                        <label>
                            Phone Number *
                        </label>

                        <input
                            type="text"
                            name="phone"
                            value={
                                formData.phone
                            }
                            onChange={
                                handleChange
                            }
                            placeholder="9876543210"
                        />

                    </div>

                    {/* EMAIL */}

                    <div className="form-group">

                        <label>
                            Email
                        </label>

                        <input
                            type="email"
                            name="email"
                            value={
                                formData.email
                            }
                            onChange={
                                handleChange
                            }
                            placeholder="customer@email.com"
                        />

                    </div>

                    {/* ADDRESS */}

                    <div className="form-group">

                        <label>
                            Address
                        </label>

                        <textarea
                            rows="3"
                            name="address"
                            value={
                                formData.address
                            }
                            onChange={
                                handleChange
                            }
                            placeholder="Customer address"
                        />

                    </div>

                    {/* FOOTER */}

                    <div className="modal-actions">

                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={onClose}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="submit-btn"
                            disabled={loading}
                        >
                            {loading
                                ? "Creating..."
                                : "Create Customer"}
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default CreateCustomerModal;