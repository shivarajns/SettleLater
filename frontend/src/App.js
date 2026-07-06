import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";

import Home from "./pages/Home/Home";
import Signup from "./pages/Signup/Signup";
import Login from "./pages/Login/Login";
import EmailVerification from "./pages/EmailVerification/EmailVerification"
import Dashboard from "./pages/Dashboard/Dashboard";

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
              <Login/>
            </MainLayout>
          }
        />

        <Route
          path="verify-email"
          element={
            <MainLayout>
              <EmailVerification/>
            </MainLayout>
          }
        />

        <Route
          path="/dashboard"
          element={<Dashboard/>}
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;