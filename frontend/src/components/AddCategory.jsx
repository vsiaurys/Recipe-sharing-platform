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

  const onSubmit = async (data) => {
    const url = "http://localhost:8080/";
    setFailed(false);

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
        setCreateMessage(`New category ${data.name} successfully created`);
        addCategory();
        setCreated(true);
      }
      if (response.status === 400) {
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
      setCreated(false);
    }, 3000);
  }, [created]);

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
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
                      value: /^[a-zA-Z]+( [a-zA-Z]+)*$/,
                      message:
                        "Category names can only contain letters and whitespaces",
                    },
                    minLength: {
                      value: 4,
                      message:
                        "Category names must be at least 4 characters long",
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
