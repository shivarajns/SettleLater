import axios from "axios";

const BASE_URL = process.env.REACT_APP_BASE_URL;

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

export const getAllCustomers = async (
  page = 0,
  size = 10
) => {
  const response = await axios.get(
    `${BASE_URL}/shops/customers?page=${page}&size=${size}`,
    getAuthHeader()
  );

  return response.data;
};

export const createCustomer = async (
  shopId,
  customerData
) => {
  const response = await axios.post(
    `${BASE_URL}/shops/${shopId}/customer`,
    customerData,
    getAuthHeader()
  );

  return response.data;
};