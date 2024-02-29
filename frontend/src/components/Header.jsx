import { useState } from "react";
import "./Header.css";
function Header() {
  const [collapsed, setCollapsed] = useState(true);

  const toggleNavbar = () => {
    setCollapsed(!collapsed);
  };

  return (
    <div className="header-container">
      <nav className="container navbar navbar-expand-lg navbar-dark header-container">
        <div className="container d-flex justify-content-between">
          <p className="logo my-auto navbar-brand">
            <img
              src="./images/logo.png"
              alt="Logo"
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
          className={`collapse navbar-collapse ${
            collapsed ? "" : "show"
          } ml-lg-2`}
          id="navbarNav"
        >
          <ul className="navbar-nav px-4">
            <li className="nav-item">
              <a
                className="nav-link"
                href="#"
              >
                Home
              </a>
            </li>
            <li className="nav-item">
              <a
                className="nav-link"
                href="#"
              >
                Register
              </a>
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
