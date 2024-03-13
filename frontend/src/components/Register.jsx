import React, { useState } from "react";
import { useForm } from "react-hook-form";
import badWords from "./BadWords";
import { useNavigate } from "react-router-dom";
//import { useNavigate, useParams } from "react-router-dom";

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
    reset,
  } = useForm();
  const password = watch("password");
  const [showModal, setShowModal] = useState(false);
  const [emailExistsError, setEmailExistsError] = useState("");
  const navigate = useNavigate();
  //const { displayName } = useParams();

  //const [resgisterMessage, setRegisterMessage] = useState("");

  const onSubmit = async (data) => {
    setShowModal(true);

    const postData = async () => {
      try {
        const response = await fetch("http://localhost:8080/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        });

        if (response.ok) {
          reset();
          setTimeout(() => {
            navigate("/");
          }, 3000);
        } else {
        }
      } catch (error) {
        console.error("Error registering user:", error);
      }
    };

    postData();
  };

  return (
    <div className="container mt-5 mb-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h3 className="card-title">Register</h3>
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
                    autoComplete="on"
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    id="email"
                    placeholder="Enter your e-mail"
                    {...register("email", {
                      required: "Email is required!",
                      pattern: {
                        value:
                          /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                        message: "Invalid email address!",
                      },
                    })}
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
                    className="form-label"
                  >
                    Password
                  </label>
                  <input
                    type="password"
                    className={`form-control ${
                      errors.password ? "is-invalid" : ""
                    }`}
                    id="password"
                    placeholder="Enter your password"
                    {...register("password", {
                      required: "Password is required!",
                      pattern: {
                        value: /^(?!.* ).+$/,
                        message: "Password cannot have empty spaces.",
                      },
                      validate: {
                        mustContainUppercaseLetter: (value) =>
                          /^(?=.*[A-Z])/.test(value) ||
                          "Password must contain at least one uppercase letter!",
                        mustContainNumber: (value) =>
                          /^(?=.*\d)/.test(value) ||
                          "Password must contain at least one number!",
                        mustContainSpecialSymbol: (value) =>
                          /^(?=.*\W)/.test(value) ||
                          "Password must contain at least one special symbol!",
                      },
                      minLength: {
                        value: 6,
                        message: "Password must be at least 6 characters long!",
                      },
                      maxLength: {
                        value: 20,
                        message:
                          "Password must be not longer than 20 characters!",
                      },
                    })}
                  />
                  {errors.password && (
                    <div className="invalid-feedback">
                      {errors.password.message}
                    </div>
                  )}
                  {!errors.password && (
                    <div className="form-text">
                      Password must contain at least one uppercase letter, one
                      number, one special symbol and be between 6 and 20
                      characters long. Cannot have empty spaces.
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="confirmPassword"
                    className="form-label"
                  >
                    Repeat Your Password
                  </label>
                  <input
                    type="password"
                    className={`form-control ${
                      errors.confirmPassword ? "is-invalid" : ""
                    }`}
                    id="confirmPassword"
                    placeholder="Repeat your password"
                    {...register("confirmPassword", {
                      validate: (value) =>
                        value === password || "The passwords do not match!",
                    })}
                  />
                  {errors.confirmPassword && (
                    <div className="invalid-feedback">
                      {errors.confirmPassword.message}
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="displayName"
                    className="form-label"
                  >
                    Display Name
                  </label>
                  <input
                    type="text"
                    className={`form-control ${
                      errors.displayName ? "is-invalid" : ""
                    }`}
                    id="displayName"
                    placeholder="Your display name"
                    {...register("displayName", {
                      required: "Display name is required!",
                      pattern: {
                        value: /^[^\W_]+$/,
                        message:
                          "Display name cannot have spaces and special symbols!",
                      },
                      minLength: {
                        value: 3,
                        message:
                          "Display name must be at least 3 characters long!",
                      },
                      maxLength: {
                        value: 15,
                        message:
                          "Display name must be not longer than 15 characters!",
                      },
                      validate: (value) =>
                        !badWords.some((word) =>
                          new RegExp(word, "i").test(value)
                        ) || "Display name contains offensive words!",
                    })}
                  />
                  {errors.displayName && (
                    <div className="invalid-feedback">
                      {errors.displayName.message}
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="firstName"
                    className="form-label"
                  >
                    First Name
                  </label>
                  <input
                    type="text"
                    autoComplete="on"
                    className={`form-control ${
                      errors.firstName ? "is-invalid" : ""
                    }`}
                    id="firstName"
                    placeholder="Your first name"
                    {...register("firstName", {
                      required: "First name is required!",
                      pattern: {
                        value: /^[A-Za-z]{1,}$/,
                        message:
                          "First name cannot have special symbols or spaces!",
                      },
                      validate: {
                        hasNoRepeatedCharacters: (value) =>
                          !/(.)\1{4,}/.test(value) ||
                          "First name cannot have the same character repeated 5 times in a row!",
                        startsWithUppercase: (value) =>
                          /^[A-Z]/.test(value) ||
                          "First name must start with an uppercase letter!",
                      },
                      minLength: {
                        value: 2,
                        message:
                          "First name must be at least 2 characters long!",
                      },
                    })}
                  />
                  {errors.firstName && (
                    <div className="invalid-feedback">
                      {errors.firstName.message}
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="lastName"
                    className="form-label"
                  >
                    Last Name
                  </label>
                  <input
                    autoComplete="on"
                    type="text"
                    className={`form-control ${
                      errors.lastName ? "is-invalid" : ""
                    }`}
                    id="lastName"
                    placeholder="Your last name"
                    {...register("lastName", {
                      required: "Last name is required!",
                      pattern: {
                        value: /^[A-Za-z]{1,}$/,
                        message:
                          "Last name cannot have special symbols or spaces!",
                      },
                      validate: {
                        hasNoRepeatedCharacters: (value) =>
                          !/(.)\1{4,}/.test(value) ||
                          "Last name cannot have the same character repeated 5 times in a row!",
                        startsWithUppercase: (value) =>
                          /^[A-Z]/.test(value) ||
                          "Last name must start with an uppercase letter!",
                      },
                      minLength: {
                        value: 2,
                        message:
                          "Last name must be at least 2 characters long!",
                      },
                    })}
                  />
                  {errors.lastName && (
                    <div className="invalid-feedback">
                      {errors.lastName.message}
                    </div>
                  )}
                </div>
                <div className="mb-3">
                  <label
                    htmlFor="gender"
                    className="form-label"
                  >
                    Gender
                  </label>
                  <select
                    id="gender"
                    className={`form-select ${
                      errors.gender && errors.gender.type === "required"
                        ? "is-invalid"
                        : ""
                    }`}
                    {...register("gender", { required: true })}
                  >
                    <option value="">Select...</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                  </select>
                  {errors.gender && errors.gender.type === "required" && (
                    <div className="invalid-feedback">
                      Gender field is required!
                    </div>
                  )}
                </div>
                <div className="mb-2 mt-2">
                  <button
                    type="submit"
                    className="btn btn-primary"
                  >
                    Submit
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div
        className={`modal fade ${showModal ? "show" : ""}`}
        style={{ display: showModal ? "block" : "none" }}
        tabIndex="-1"
        role="dialog"
        aria-labelledby="exampleModalCenterTitle"
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          role="document"
        >
          <div className="modal-content">
            <div className="modal-header">
              <h5
                className="modal-title text-success"
                id="exampleModalCenterTitle"
              >
                Registration Successful!
              </h5>
              {/* <button
                type="button"
                className="close"
                onClick={() => setShowModal(false)}
                aria-label="Close"
              >
                <span aria-hidden="true">&times;</span>
              </button> */}
            </div>
            <div className="modal-body">
              Your registration was successful. Now you can login.
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-primary"
                onClick={() => setShowModal(false)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
