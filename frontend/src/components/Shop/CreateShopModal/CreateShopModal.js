import { useState } from "react";
import "./CreateShopModal.css";
import { X } from "lucide-react";
import { createShop } from "../ShopService";
import { useNavigate } from "react-router-dom";

function CreateShopModal({
  isOpen,
  onClose,
  onShopCreated
}) {
  const initialForm = {
    name: "",
    phoneNumber: "",
    email: "",
    businessType: "",
    gstNumber: "",
    addressLine1: "",
    addressLine2: "",
    city: "",
    state: "",
    pincode: "",
    country: "",
    currency: "INR",
  };

  const navigate = useNavigate();

  const [formData, setFormData] =
    useState(initialForm);

  const [loading, setLoading] =
    useState(false);

  const [errors, setErrors] =
    useState({});

  if (!isOpen) return null;

  

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]:
        e.target.value,
    });
  };

  const validate = () => {
    const newErrors = {};

    Object.keys(formData).forEach(
      (key) => {
        if (!formData[key]?.trim()) {
          newErrors[key] =
            "Required";
        }
      }
    );

    if (
      formData.email &&
      !/^\S+@\S+\.\S+$/.test(
        formData.email
      )
    ) {
      newErrors.email =
        "Invalid Email";
    }

    if (
      formData.phoneNumber &&
      !/^\d{10}$/.test(
        formData.phoneNumber
      )
    ) {
      newErrors.phoneNumber =
        "Enter valid phone";
    }

    if (
      formData.pincode &&
      !/^\d{6}$/.test(
        formData.pincode
      )
    ) {
      newErrors.pincode =
        "Invalid pincode";
    }

    setErrors(newErrors);

    return (
      Object.keys(newErrors)
        .length === 0
    );
  };

  const handleSubmit =
    async (e) => {
      e.preventDefault();

      if (!validate()) return;

      try {
        setLoading(true);

        await createShop(
          formData
        );

        onShopCreated();

        setFormData(
          initialForm
        );

        onClose();
      } catch (error) {
        console.error(error);
        navigate("/login")

      } finally {
        setLoading(false);
      }
    };

  return (
    <div className="shop-modal-overlay">

      <div className="shop-modal">

        <div className="shop-modal-header">

          <h2>
            Create Shop
          </h2>

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
          className="shop-form"
        >

          <div className="form-grid">
            <Input
              placeholder="Shop Name"
              name="name"
              value={formData.name}
              onChange={
                handleChange
              }
              error={
                errors.name
              }
            />

            <Input
              placeholder="Phone Number"
              name="phoneNumber"
              value={
                formData.phoneNumber
              }
              onChange={
                handleChange
              }
              error={
                errors.phoneNumber
              }
            />

            <Input
              placeholder="Email"
              name="email"
              value={
                formData.email
              }
              onChange={
                handleChange
              }
              error={
                errors.email
              }
            />

            <Input
              placeholder="Business Type"
              name="businessType"
              value={
                formData.businessType
              }
              onChange={
                handleChange
              }
              error={
                errors.businessType
              }
            />

            <Input
              placeholder="GST Number"
              name="gstNumber"
              value={
                formData.gstNumber
              }
              onChange={
                handleChange
              }
              error={
                errors.gstNumber
              }
            />

            <Input
              placeholder="Address Line 1"
              name="addressLine1"
              value={
                formData.addressLine1
              }
              onChange={
                handleChange
              }
              error={
                errors.addressLine1
              }
            />

            <Input
              placeholder="Address Line 2"
              name="addressLine2"
              value={
                formData.addressLine2
              }
              onChange={
                handleChange
              }
              error={
                errors.addressLine2
              }
            />

            <Input
              placeholder="City"
              name="city"
              value={
                formData.city
              }
              onChange={
                handleChange
              }
              error={
                errors.city
              }
            />

            <Input
              placeholder="State"
              name="state"
              value={
                formData.state
              }
              onChange={
                handleChange
              }
              error={
                errors.state
              }
            />

            <Input
              placeholder="Pincode"
              name="pincode"
              value={
                formData.pincode
              }
              onChange={
                handleChange
              }
              error={
                errors.pincode
              }
            />

            <Input
              placeholder="Country"
              name="country"
              value={
                formData.country
              }
              onChange={
                handleChange
              }
              error={
                errors.country
              }
            />

            <Input
              placeholder="Currency"
              name="currency"
              value={
                formData.currency
              }
              onChange={
                handleChange
              }
              error={
                errors.currency
              }
            />

          </div>

          <button
            type="submit"
            className="create-shop-btn"
            disabled={loading}
          >
            {loading
              ? "Creating..."
              : "Create Shop"}
          </button>

        </form>

      </div>

    </div>
  );
}

function Input({
  label,
  error,
  ...props
}) {
  return (
    <div className="input-group">
      <label>
        {label}
      </label>

      <input
        {...props}
      />

      {error && (
        <span className="error">
          {error}
        </span>
      )}
    </div>
  );
}

export default CreateShopModal;