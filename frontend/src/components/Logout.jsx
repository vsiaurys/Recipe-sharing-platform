function Logout() {
  const role = localStorage.getItem("role");
  const email = localStorage.getItem("email");

  return (
    <div className="container mx-auto mt-5">
      <div
        className="alert alert-success"
        role="alert"
      >
        Logout Successful
      </div>
      <div></div>
      <div>Your email is: {email}</div>
      <div>Your role is: {role}</div>
    </div>
  );
}

export default Logout;
