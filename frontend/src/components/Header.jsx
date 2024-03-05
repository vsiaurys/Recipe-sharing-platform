import { useState } from "react";
import "./Header.css";
import { Route, Routes, NavLink } from "react-router-dom";
import Register from "./Register.jsx";
function Header() {
  const [collapsed, setCollapsed] = useState(true);

  const toggleNavbar = () => {
    setCollapsed(!collapsed);
  };

  return (
    <div className="header-container">
      <nav className="container navbar navbar-expand-lg navbar-dark">
        <div className="container d-flex justify-content-between align-items-center">
          <p className="logo my-auto navbar-brand">
            <img
              src="./images/logo_2.png"
              alt="Logo with words: Recipe Sharing Platform stacked on each other with pot with steam on top. "
            />
          </p>
          <button
            className="navbar-toggler"
            type="button"
            onClick={toggleNavbar}
          >
            <span className="navbar-toggler-icon"></span>
          </button>
        </div>

        <div
          className={`collapse navbar-collapse ${collapsed ? "" : "show"}`}
          id="navbarNav"
        >
          <ul className="navbar-nav px-4">
            <li className="nav-item">
              <NavLink
                to="/"
                className="nav-link"
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                to="/register"
                className="nav-link"
              >
                Register
              </NavLink>
            </li>
            <li className="nav-item">
              <a
                className="nav-link"
                href="#"
              >
                Login
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
}

export default Header;
