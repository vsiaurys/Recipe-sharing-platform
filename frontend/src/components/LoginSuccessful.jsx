function LoginSuccessful() {
  const role = localStorage.getItem("role");
  const email = localStorage.getItem("email");
  const displayName = localStorage.getItem("displayName");

  return (
    <div className="container mx-auto mt-5">
      <div
        className="alert alert-success"
        role="alert"
      >
        Congrats! Your login was successful!
      </div>
      <div>
        <h2>Welcome {displayName}!</h2>
      </div>
      <div>Your email is: {email}</div>
      <div>Your role is: {role}</div>
    </div>
  );
}

export default LoginSuccessful;
