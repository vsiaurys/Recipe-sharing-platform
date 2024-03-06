import { useForm } from "react-hook-form";
import BadWords from "./BadWords";

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();
  const password = watch("password");

  const onSubmit = (data) => {
    console.log(data);
  };

  return (
    <div className="container mt-5">
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
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    id="email"
                    placeholder="Enter Your e-mail"
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
                    placeholder="Enter Your password"
                    {...register("password", {
                      required: "Password is required!",
                      pattern: {
                        // value: /^(?=.*[A-Z])(?=.*\d)(?=.*\W)(?!.* ).+$/,
                        value: /^(?!.* ).+$/,
                        //message:
                        //  "Password must contain at least one uppercase letter, one number and one special symbol. Cannot have empty spaces.",
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
                    placeholder="Repeat Your password"
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
                        !BadWords.some((word) =>
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
                    type="text"
                    className={`form-control ${
                      errors.lastName ? "is-invalid" : ""
                    }`}
                    id="lastName"
                    placeholder="Your last name"
                    {...register("lastName", {
                      required: "Last name is required!",
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
                    name="gender"
                    className={`form-select ${
                      errors.gender && errors.gender.type === "required"
                        ? "is-invalid"
                        : ""
                    }`}
                    {...register("gender", { required: true })}
                  >
                    <option value="">Select...</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
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
                {/* <input type="submit" /> */}
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
