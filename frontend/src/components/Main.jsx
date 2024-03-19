export default function Main({ checkRole }) {
  const role = localStorage.getItem("role");
  const email = localStorage.getItem("email");
  const displayName = localStorage.getItem("displayName");

  return (
    <>
      {checkRole() ? (
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
      ) : (
        <div className="container">
          <h1>Welcome</h1>
          <p>
            Welcome to the Recipe sharing platform. Here you can share and look
            for favorite recipes.
          </p>
        </div>
      )}
    </>
  );
}
