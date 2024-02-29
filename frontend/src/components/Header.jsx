function Header() {
  return (
    <div className="bg-dark">
      <nav className="container navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container d-flex">
          <p className="my-auto navbar-brand">Recipe Sharing Platform</p>

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
    </div>
  );
}
export default Header;
