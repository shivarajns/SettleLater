import axios from "axios";

const BASE_URL = process.env.REACT_APP_BASE_URL;

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

export const getAllShops = async () => {
  const response = await axios.get(
    `${BASE_URL}/shops`,
    getAuthHeader()
  );

  return response.data;
};

export const createShop = async (shopData) => {
  const response = await axios.post(
    `${BASE_URL}/shops`,
    shopData,
    getAuthHeader()
  );

  return response.data;
};