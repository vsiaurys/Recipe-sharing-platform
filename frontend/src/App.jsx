import "./App.css";
import { useState } from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";
import Logout from "./components/Logout";
import { Route, Routes, NavLink } from "react-router-dom";
import LoginSuccessful from "./components/LoginSuccessful";

function App() {
  const [loginState, setLoginState] = useState(true);

  return (
    <div className="d-flex flex-column min-vh-100">
      <Header
        loginState={loginState}
        setLoginState={setLoginState}
      />
      <Routes>
        <Route
          path="/"
          element={<Main />}
        />
        <Route
          path="/login"
          element={
            <Login
              loginState={loginState}
              setLoginState={setLoginState}
            />
          }
        />
        <Route
          path="/register"
          element={<Register />}
        />
        <Route
          path="/login-successful"
          element={<LoginSuccessful />}
        />
        <Route
          path="/logout"
          element={<Logout />}
        />
      </Routes>
      <footer className="mt-auto footer">
        <Footer />
      </footer>
    </div>
  );
}

export default App;
