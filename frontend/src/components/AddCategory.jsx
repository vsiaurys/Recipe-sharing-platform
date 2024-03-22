import { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import BadWords from "./BadWords";
import "./AddCategory.css";

export default function AddCategory({ addCategory }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const [createMessage, setCreateMessage] = useState();
  const [created, setCreated] = useState(false);
  const [failed, setFailed] = useState(false);

  const resetForm = () => {
    setFailed(false);
    setCreated(false);
    document.getElementById("form-add-category").reset();
  };

  const onSubmit = async (data) => {
    const url = "http://localhost:8080/";
    resetForm();

    try {
      const response = await fetch(`${url}categories`, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Basic " +
            btoa(
              `${localStorage.getItem("email")}:${localStorage.getItem(
                "password"
              )}`
            ),
        },
      });

      console.log(response);

      if (response.ok) {
        setCreateMessage(`New category ${data.name} successfully created`);
        addCategory();
        setCreated(true);
      }
      if (response.status === 400 /*&& response.name === data.name*/) {
        setFailed(true);
        setCreateMessage(
          `Category ${data.name} already exists. Please choose another name`
        );
      }
    } catch (error) {
      console.error("Error adding new category: ", error);
      setCreateMessage("An unexpected error occurred.");
    }
  };

  useEffect(() => {
    setTimeout(() => {
      resetForm();
    }, 3000);
  }, [created]);

  return (
    <>
      <form
        id="form-add-category"
        onSubmit={handleSubmit(onSubmit)}
      >
        <div
          className="modal fade"
          id="addCategoryModal"
          tabIndex={-1}
          aria-labelledby="AddCategoryLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1
                  className="modal-title fs-5"
                  id="AddCategoryLabel"
                >
                  Add Category
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                  onClick={() => resetForm()}
                />
              </div>

              <div className="modal-body">
                <label
                  htmlFor="disabledTextInput"
                  className="form-label fw-normal"
                >
                  Category name
                </label>

                <input
                  type="text"
                  className={`form-control ${errors.name ? "is-invalid" : ""}`}
                  id="disabledTextInput"
                  placeholder="Enter new category name"
                  autoComplete="on"
                  {...register("name", {
                    required: "Please enter category name",
                    pattern: {
                      value: /^[A-Z][a-zA-Z]*( [a-zA-Z]*)*$/,
                      message:
                        "Category names must start from an uppercase letter and can contain only letters and whitespaces",
                    },
                    minLength: {
                      value: 4,
                      message:
                        "Category names must be at least 4 characters long",
                    },
                    maxLength: {
                      value: 20,
                      message:
                        "Category names must not be longer than 20 characters",
                    },
                    validate: (value) =>
                      !BadWords.some((word) =>
                        new RegExp(word, "i").test(value)
                      ) || "Display name contains offensive words!",
                  })}
                />

                {created && (
                  <div className="container mx-auto mt-3">
                    <div
                      className="alert alert-success"
                      role="alert"
                    >
                      {createMessage}
                    </div>
                  </div>
                )}
                {failed && (
                  <div className="category-name-exists text-danger">
                    {createMessage}
                  </div>
                )}
                {errors.name && (
                  <div className="invalid-feedback">{errors.name.message}</div>
                )}
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn button-close"
                  data-bs-dismiss="modal"
                  onClick={() => resetForm()}
                >
                  Close
                </button>
                <button
                  type="submit"
                  className="btn button-add-category"
                >
                  Add
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </>
  );
}
