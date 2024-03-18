import "./App.css";
import { useState } from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";

import { Route, Routes, NavLink } from "react-router-dom";
import LoginSuccessful from "./components/LoginSuccessful";

function App() {
  const checkRole = () => {
    const role = localStorage.getItem("role");
    return role !== null && role !== "";
  };
  return (
    <div className="d-flex flex-column min-vh-100">
      <Header checkRole={checkRole} />
      <Routes>
        <Route
          path="/"
          element={<Main />}
        />
        <Route
          path="/login"
          element={<Login />}
        />
        <Route
          path="/register"
          element={<Register />}
        />
        <Route
          path="/login-successful"
          element={<LoginSuccessful checkRole={checkRole} />}
        />
      </Routes>
      <footer className="mt-auto footer">
        <Footer />
      </footer>
    </div>
  );
}

export default App;
