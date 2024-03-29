import React, { useState } from "react";
import { useForm } from "react-hook-form";
import badWords from "./BadWords";
import { useNavigate } from "react-router-dom";
import "./UpdateProfile.css";

export default function UpdateProfile() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
    reset,
  } = useForm();

  const password = watch("password");
  const [showModal, setShowModal] = useState(false);
  const [showOverlay, setShowOverlay] = useState(false);
  const [emailExistsError, setEmailExistsError] = useState("");
  const [displayNameExistsError, setDisplayNameExistsError] = useState("");
  const [file, setFile] = useState();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    const updateData = async () => {
      try {
        const formData = new FormData();

        formData.append("file", file);
        formData.append(
          "userDto",
          new Blob([JSON.stringify(data)], {
            type: "application/json",
          })
        );
        const url = "http://localhost:8080/";

        const response = await fetch(
          `${url}update-user/${localStorage.getItem("id")}`,
          {
            method: "PUT",
            headers: {
              Authorization:
                "Basic " +
                btoa(
                  `${localStorage.getItem("email")}:${localStorage.getItem(
                    "password"
                  )}`
                ),
            },
            body: formData,
          }
        );
        if (response.status === 200) {
          const responseData = await response.json();

          localStorage.setItem("id", responseData.id);
          localStorage.setItem("displayName", responseData.displayName);
          localStorage.setItem("role", responseData.role);
          localStorage.setItem("email", responseData.email);
          localStorage.setItem("firstName", responseData.firstName);
          localStorage.setItem("lastName", responseData.lastName);
          localStorage.setItem("gender", responseData.gender);
          localStorage.setItem("password", data.password);

          setShowModal(true);
          setShowOverlay(true);
          reset();
          setEmailExistsError("");
          setDisplayNameExistsError("");
          setTimeout(() => {
            setShowOverlay(false);
            navigate("/");
          }, 3000);
        } else {
          const responseData = await response.json();
          if (responseData.error === "User with this email already exists.") {
            setEmailExistsError(responseData.error);
          }
          if (
            responseData.error === "User with this display name already exists."
          ) {
            setDisplayNameExistsError(responseData.error);
          }
        }
      } catch (error) {
        console.error("Error updating profile:", error);
      }
    };
    updateData();
  };

  function handleUploadOnChange(event) {
    event.preventDefault();
    setFile(event.target.files[0]);
  }

  const handleEmailChange = () => {
    setEmailExistsError("");
  };

  const handleDisplayNameChange = () => {
    setDisplayNameExistsError("");
  };

  return (
    <div className="container mt-4 mb-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h1 className="card-title display-6">Update your profile</h1>
              <p>*All fields are required!</p>
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
                      errors.email || emailExistsError ? "is-invalid" : ""
                    }`}
                    id="email"
                    placeholder="Enter your e-mail"
                    defaultValue={localStorage.getItem("email")}
                    {...register("email", {
                      required: "Email is required!",
                      pattern: {
                        value:
                          /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                        message: "Invalid email address!",
                      },
                    })}
                    onChange={handleEmailChange}
                  />
                  {(errors.email || emailExistsError) && (
                    <div className="invalid-feedback">
                      {errors.email ? errors.email.message : emailExistsError}
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
                      errors.displayName || displayNameExistsError
                        ? "is-invalid"
                        : ""
                    }`}
                    id="displayName"
                    placeholder="Your display name"
                    defaultValue={localStorage.getItem("displayName")}
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
                    onChange={handleDisplayNameChange}
                  />
                  {(errors.displayName || displayNameExistsError) && (
                    <div className="invalid-feedback">
                      {errors.displayName
                        ? errors.displayName.message
                        : displayNameExistsError}
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
                    defaultValue={localStorage.getItem("firstName")}
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
                    defaultValue={localStorage.getItem("lastName")}
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
                    defaultValue={localStorage.getItem("gender")}
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

                <div className="mb-3">
                  <label
                    htmlFor="picture"
                    className="form-label"
                  >
                    Upload profile picture
                  </label>
                  <input
                    className="form-control"
                    id="picture"
                    type="file"
                    name="picture"
                    accept="image/png, image/jpeg"
                    onChange={handleUploadOnChange}
                  />
                </div>
                <div className="mb-2 mt-2">
                  <button
                    type="submit"
                    className="btn btn-primary button-update-user text-dark"
                  >
                    Update
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      {showOverlay && <div className="overlay"></div>}
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
              <h2
                className="modal-title text-success"
                id="exampleModalCenterTitle"
              >
                Profile update successful!
              </h2>
            </div>
            <div className="modal-body">
              You successfully updated your profile.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
