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
        <div className="col-sm-12 col-md-8 col-lg-9">
          <p>&#169; {getCurrentYear()} RECIPE SHARING PLATFORM</p>
          <p className="fw-semibold">Contact email:</p>
          <p>
            <a
              className="contact-email"
              href="mailto:vardas.pavarde@gmail.com"
            >
              vardas.pavarde@gmail.com
            </a>
          </p>
        </div>

        <div className="col-sm-12 col-md-4 col-lg-3">
          <p className="fw-semibold">Our address: </p>
          <p>Trinapolio g. 2, Vilnius</p>
          <p className="fw-semibold">Call us: </p>
          <p>+370 5 269 7455</p>
        </div>
      </div>
    </div>
  );
}