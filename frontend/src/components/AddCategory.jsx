import { useForm } from "react-hook-form";
import { useState } from "react";
import BadWords from "./BadWords";
import "./AddCategory.css";

export default function AddCategory({ changeCategory, closeModal }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    setError,
    clearErrors,
  } = useForm();

  const [createMessage, setCreateMessage] = useState();

  const onSubmit = async (data) => {
    const url = "http://localhost:8080/";

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

      if (response.ok) {
        setCreateMessage(
          <span>
            New category <strong>{data.name}</strong> successfully created
          </span>
        );
        setTimeout(() => {
          clearFields();
          closeModal("addCategoryModal");
          changeCategory();
        }, 3000);
      }
      if (response.status === 400) {
        const responseData = await response.json();
        setError("name", {
          type: "server",
          message: `${responseData.name}`,
        });
      }
    } catch (error) {
      console.error("Error adding new category: ", error);
    }
  };

  const clearFields = () => {
    reset();
    clearErrors();
    setCreateMessage("");
  };

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
            {createMessage && (
              <div className="modal-content">
                <div className="modal-header">
                  <h1
                    className="modal-title fs-5"
                    id="AddCategoryLabel"
                  >
                    Add Category
                  </h1>
                </div>
                <div className="modal-body">
                  <div className="alert alert-success">{createMessage}</div>
                </div>
              </div>
            )}

            {!createMessage && (
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
                    onClick={clearFields}
                  />
                </div>

                <div className="modal-body">
                  <label
                    htmlFor="categoryName"
                    className="form-label fw-normal"
                  >
                    Category name:
                  </label>

                  <input
                    type="text"
                    className={`form-control ${
                      errors.name ? "is-invalid" : ""
                    }`}
                    id="categoryName"
                    placeholder="Enter new category name"
                    autoComplete="on"
                    {...register("name", {
                      required: "Please enter category name",
                      pattern: {
                        value: /^[A-Z][a-zA-Z]*( [a-zA-Z]*)*$/,
                        message:
                          "Category name must start from an uppercase letter and can contain only letters and single whitespaces",
                      },
                      minLength: {
                        value: 4,
                        message:
                          "Category name must be at least 4 characters long",
                      },
                      maxLength: {
                        value: 20,
                        message:
                          "Category name must not be longer than 20 characters",
                      },
                      validate: (value) =>
                        !BadWords.some((word) =>
                          new RegExp(word, "i").test(value)
                        ) || "Display name contains offensive words!",
                    })}
                  />
                  {errors.name && (
                    <div className="invalid-feedback">
                      {errors.name ? errors.name.message : ""}
                    </div>
                  )}
                </div>
                <div className="modal-footer">
                  <button
                    type="button"
                    className="btn button-close"
                    data-bs-dismiss="modal"
                    onClick={clearFields}
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
            )}
          </div>
        </div>
      </form>
    </>
  );
}
