import { useState } from "react";
import "./Header.css";
import { Link, useNavigate } from "react-router-dom";
import ModalLogout from "./ModalLogout";

function Header({ checkRole }) {
  const [collapsed, setCollapsed] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showOverlay, setShowOverlay] = useState(false);

  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    setShowModal(true);
    setShowOverlay(true);
    setTimeout(() => {
      setShowModal(false);
      setShowOverlay(false);
    }, 3000);
    navigate("/");
    checkRole();
  };

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
          <div className="container d-flex justify-content-end">
            <ul className="navbar-nav px-4">
              <li className="nav-item">
                <Link
                  to="/"
                  className="nav-link"
                >
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to="/register"
                  className="nav-link"
                >
                  {!checkRole() && "Register"}
                </Link>
              </li>
              <li
                onClick={() => {
                  if (checkRole()) {
                    handleLogout();
                  }
                }}
                className="nav-item"
              >
                <Link
                  to="/login"
                  className="nav-link"
                >
                  {checkRole() ? "Logout" : "Login"}
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <ModalLogout
        showModal={showModal}
        showOverlay={showOverlay}
      />
    </div>
  );
}

export default Header;
