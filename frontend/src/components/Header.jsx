import { useState } from "react";
import "./Header.css";
import { Link, useNavigate } from "react-router-dom";

function Header({ isLoggedIn, setIsLoggedIn }) {
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
                  Register
                </Link>
              </li>
              <li
                onClick={() => {
                  if (isLoggedIn == true) {
                    handleLogout();
                    setIsLoggedIn(!isLoggedIn);
                  }
                }}
                className="nav-item"
              >
                <Link
                  to="/login"
                  className="nav-link"
                >
                  {isLoggedIn ? "Logout" : "Login"}
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      {showOverlay && (
        <div class="position-fixed top-0 left-0 w-100 h-100 bg-dark opacity-75 z-999"></div>
      )}
      <div
        className={`modal fade ${showModal ? "show" : ""}`}
        style={{ display: showModal ? "block" : "none" }}
        tabIndex="-1"
        role="dialog"
        aria-labelledby="exampleModalCenterTitle"
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          role="document"
        >
          <div className="modal-content">
            <div className="modal-header">
              <h2
                className="modal-title text-success"
                id="exampleModalCenterTitle"
              >
                Logout Successful!
              </h2>
            </div>
            <div className="modal-body">
              You have been successfully logged out. Thank you for using our
              platform.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Header;
