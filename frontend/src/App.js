import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";

import Home from "./pages/Home/Home";
import Signup from "./pages/Signup/Signup";
import Login from "./pages/Login/Login";
import EmailVerification from "./pages/EmailVerification/EmailVerification"
import Dashboard from "./pages/Dashboard/Dashboard";
import SessionExpired from "./components/sessionExpired/SessionExpired";
import ProtectedRoute from "./routs/ProtectedRoute";
import ShopSection from "./pages/Dashboard/ShopSection/ShopSection";
import CustomerSection from "./pages/Dashboard/Customer/Customer";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <MainLayout>
              <Home />
            </MainLayout>
          }
        />

        <Route
          path="/home"
          element={
            <MainLayout>
              <Home />
            </MainLayout>
          }
        />

        <Route
          path="/signup"
          element={
            <MainLayout>
              <Signup />
            </MainLayout>
          }
        />

        <Route
          path="/login"
          element={
            <MainLayout>
              <Login />
            </MainLayout>
          }
        />

        <Route
          path="verify-email"
          element={
            <MainLayout>
              <EmailVerification />
            </MainLayout>
          }
        />

        <Route
          path="/dashboard"
          element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>  
        
        }
        />
        
        <Route
          path="/sessionexpired"
          element={
            <SessionExpired />
          }
        />

        <Route
          path="/shops"
          element={
            <ProtectedRoute>
              <ShopSection/>
            </ProtectedRoute>
          }
        />

        <Route
          path="/customers"
          element={
            <ProtectedRoute>
              <CustomerSection/>
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;