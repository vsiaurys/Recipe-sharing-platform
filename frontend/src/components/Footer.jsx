import "./Footer.css";

export default function Footer() {
  const getCurrentYear = () => {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();

    return currentYear;
  };

  return (
    <div className="footer p-3 text-start bg-dark text-white">
      <p>&#169; {getCurrentYear()} RECIPE SHARING PLATFORM</p>
      <p>
        Contact email:
        <a href="mailto:vardas.pavarde@gmail.com">vardas.pavarde@gmail.com </a>
      </p>
    </div>
  );
}
