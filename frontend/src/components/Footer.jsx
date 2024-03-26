import "./Footer.css";

export default function Footer() {
  const getCurrentYear = () => {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();

    return currentYear;
  };

  return (
    <div className="container text-start p-3 w-100">
      <div className="row">
        <div className="col-8 col-sm-6 col-md-8 col-lg-9">
          <ul className="list-unstyled">
            {" "}
            <li>&#169; {getCurrentYear()} RECIPE SHARING PLATFORM</li>
            <li>
              <span className="fw-semibold">Contact email: </span>
              <a
                className="contact-email"
                href="mailto:vardas.pavarde@gmail.com"
              >
                vardas.pavarde@gmail.com
              </a>
            </li>
          </ul>
        </div>

        <div className="col-4 col-sm-6 col-md-4 col-lg-3">
          <ul className="list-unstyled">
            <li>
              <span className="fw-semibold">Our address:</span> Trinapolio g. 2,
              Vilnius
            </li>
            <li>
              <span className="fw-semibold">Call us:</span> +370 5 269 7455
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
