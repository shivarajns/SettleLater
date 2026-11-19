import axios from "axios";

const BASE_URL = process.env.REACT_APP_BASE_URL;

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

export const createCredit = async (
  shopId,
  customerId,
  creditData
) => {
  const response = await axios.post(
    `${BASE_URL}/api/shops/${shopId}/customers/${customerId}/credits`,
    creditData,
    getAuthHeader()
  );

  return response.data;
};

export const getAllCredits = async (shopId, customerId) => {
  const response = await axios.get(
    `${BASE_URL}/api/shops/${shopId}/customers/${customerId}/credits`,
    getAuthHeader()
  );

  return response.data;
};