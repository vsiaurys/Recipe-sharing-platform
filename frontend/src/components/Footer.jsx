export default function Footer() {
  const getCurrentYear = () => {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();

    return currentYear;
  };

  return (
    <div className="fixed-bottom bg-dark">
      <div className="container text-start p-3 text-white w-100 d-flex justify-content-between">
        <div>
          <p>&#169; {getCurrentYear()} RECIPE SHARING PLATFORM</p>
          <p>
            Contact email:&nbsp;
            <a href="mailto:vardas.pavarde@gmail.com">
              vardas.pavarde@gmail.com
            </a>
          </p>
        </div>
        <div>
          <p>
            <div className="text-secondary">Our address: </div>Trinapolio g. 2,
            Vilnius
          </p>
          <p>
            <div className="text-secondary">Call us: </div>+370 5 269 7455
          </p>
        </div>
      </div>
    </div>
  );
}
