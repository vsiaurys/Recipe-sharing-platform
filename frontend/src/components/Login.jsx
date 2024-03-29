import { useState } from "react";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import "./Login.css";

function Login({ checkRole }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const [loginMessage, setLoginMessage] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);

  const navigate = useNavigate();

  const handleLoginResponse = (status, message) => {
    if (status === "success") {
      setLoginMessage(message);
    } else {
      setLoginMessage("Error: " + message);
    }
  };

  const onSubmit = async (data) => {
    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        body: JSON.stringify(data),
        headers: { "Content-Type": "application/json" },
      });

      const responseData = await response.json();

      if (response.ok) {
        handleLoginResponse("success", responseData.message);
        localStorage.setItem("id", responseData.id);
        localStorage.setItem("displayName", responseData.displayName);
        localStorage.setItem("role", responseData.role);
        localStorage.setItem("email", responseData.email);
        localStorage.setItem("firstName", responseData.firstName);
        localStorage.setItem("lastName", responseData.lastName);
        localStorage.setItem("gender", responseData.gender);
        localStorage.setItem("password", data.password);
        navigate("/");
        checkRole();
      } else {
        handleLoginResponse("error", responseData.message);
      }
    } catch (error) {
      console.error("Error:", error);
      setLoginMessage("An unexpected error occurred.");
    }
  };

  return (
    <div className="container mt-4 mb-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h1 className="card-title display-6">Login</h1>
              <form onSubmit={handleSubmit(onSubmit)}>
                <div className="mb-3">
                  <label
                    htmlFor="email"
                    className="form-label"
                  >
                    Email address
                  </label>
                  <input
                    type="text"
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    id="email"
                    placeholder="Enter email"
                    {...register("email", {
                      required: "Email is required",
                      pattern: {
                        value: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                        message: "Invalid email address",
                      },
                    })}
                    autoComplete="email"
                  />
                  {errors.email && (
                    <div className="invalid-feedback">
                      {errors.email.message}
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="password"
                    className="form-label input-group"
                  >
                    Password
                  </label>
                  <div className="input-group">
                    <input
                      type={passwordVisible ? "text" : "password"}
                      className={`form-control ${
                        errors.password ? "is-invalid" : ""
                      }`}
                      id="password"
                      placeholder="Password"
                      {...register("password", {
                        required: "Password is required",
                        minLength: {
                          value: 6,
                          message:
                            "Password must be at least 6 characters long",
                        },

                        maxLength: {
                          value: 20,
                          message:
                            "Password must be at most 20 characters long",
                        },
                      })}
                      autoComplete="current-password"
                    />
                    <button
                      type="button"
                      className="btn btn-outline-secondary"
                      onClick={() => setPasswordVisible(!passwordVisible)}
                    >
                      {passwordVisible ? <FaEyeSlash /> : <FaEye />}
                    </button>

                    {errors.password && (
                      <div className="invalid-feedback">
                        {errors.password.message}
                      </div>
                    )}
                  </div>
                </div>
                <button
                  type="submit"
                  className="btn button-login"
                >
                  Login
                </button>
              </form>
              <div className="mt-3">
                <p className="login">{loginMessage}</p>
                <p>
                  Don't have an account?{" "}
                  <Link
                    to="/register"
                    className="special-link"
                  >
                    Register
                  </Link>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
