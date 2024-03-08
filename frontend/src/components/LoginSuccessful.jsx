import React from "react";

function LoginSuccessful() {
  const role = localStorage.getItem("role");

  return (
    <div className="container mx-auto mt-5">
      <div
        className="alert alert-success"
        role="alert"
      >
        Congrats! Your login was successful!
      </div>
      <div>Your role is: {role}</div>
    </div>
  );
}

export default LoginSuccessful;
