import axios from "axios";

const BASE_URL = process.env.REACT_APP_BASE_URL;
export const registerUser = async (payload) => {
  const response = await axios.post(
    `${BASE_URL}/register`,
    payload
  );

  return response.data;
};

export const loginUser = async (payload) => {
  const resopnse = await axios.post(`${BASE_URL}/login`, payload);
  return resopnse.data;
}

const getAuthHead = () => ({
  headers : {
    Authorization : `Bearer ${localStorage.getItem("token")}`,
  },
 });

export const checkEmailVerification = async () => {
  const response = await axios.get(`${BASE_URL}/verify/get`,
    getAuthHead()
  );
  
  return response.data;
}

export const resendVerificationEmail = async () => {
   const response = await axios.get(`${BASE_URL}/auth/verify/resend`,
    getAuthHead()
  );

  return response.data;
}