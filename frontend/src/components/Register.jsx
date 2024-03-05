import { useForm } from "react-hook-form";

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();
  const password = watch("password", "");

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
                    type="email"
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    id="email"
                    placeholder="Enter Your e-mail"
                    {...register("email", {
                      required: "Email is required",
                      pattern: {
                        value: /^\S+@\S+\.\S+$/,
                        message: "Invalid email address",
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
                      required: "Password is required",
                      pattern: {
                        value: /^(?=.*[A-Z])(?=.*\d).+$/,
                        message:
                          "Password must contain at least one uppercase letter and one number",
                      },
                      minLength: {
                        value: 6,
                        message: "Password must be at least 6 characters long",
                      },

                      maxLength: {
                        value: 20,
                        message: "Password must be at most 20 characters long",
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
                      number, and be between 6 and 20 characters long.
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
                        value === password.current ||
                        "The passwords do not match",
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
                      required: "Display name field is required",
                      minLength: {
                        value: 1,
                        message:
                          "Display name must be at least 1 character long",
                      },
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
                      required: "First name field is required",
                      minLength: {
                        value: 2,
                        message: "First name must be at least 2 character long",
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
                      required: "Last name field is required",
                      minLength: {
                        value: 2,
                        message: "Last name must be at least 2 character long",
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
                      Gender field is required
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
