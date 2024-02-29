export default function Footer() {
  const getCurrentYear = () => {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();

    return currentYear;
  };

  return (
    <div className="fixed-bottom bg-dark">
      <div className="container text-start p-3 text-white w-100">
        <p>&#169; {getCurrentYear()} RECIPE SHARING PLATFORM</p>
        <p>
          Contact email:&nbsp;
          <a href="mailto:vardas.pavarde@gmail.com">vardas.pavarde@gmail.com</a>
        </p>
      </div>
    </div>
  );
}
