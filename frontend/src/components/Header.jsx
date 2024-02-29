import "bootstrap/dist/css/bootstrap.min.css";

function Header() {
  return (
    <nav className="container navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <p className="navbar-brand justify-content-end">
          Recipe Sharing Platform
        </p>

        <div
          className="justify-content-end"
          id="navbarNav"
        >
          <ul className="navbar-nav">
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
      </div>
    </nav>
  );
}
export default Header;
