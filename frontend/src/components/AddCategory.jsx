import { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import BadWords from "./BadWords";

export default function AddCategory({ addCategory }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const [createMessage, setCreateMessage] = useState();
  const [created, setCreated] = useState(false);

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

      const responseData = await response.json();

      if (response.ok) {
        setCreateMessage(`New category ${data.name} successfully created`);
        console.log("AAAAAAAAAAAAAAAAA");
        addCategory();
        setCreated(true);
      } else {
        setCreateMessage(responseData.message);
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
                <input
                  type="text"
                  className={`form-control ${errors.name ? "is-invalid" : ""}`}
                  id="disabledTextInput"
                  placeholder="Enter new category"
                  {...register("name", {
                    required: "Please enter category name",
                    pattern: {
                      value: /^[A-Z][a-zA-Z\s]*$/,
                      message:
                        "Category names can only contain letters and whitespaces, and must start with an uppercase letter",
                    },
                    minLength: {
                      value: 3,
                      message:
                        "Category names must be at least 3 characters long",
                    },
                    validate: (value) =>
                      !BadWords.some((word) =>
                        new RegExp(word, "i").test(value)
                      ) || "Display name contains offensive words!",
                  })}
                />
                {created && (
                  <div className="container mx-auto mt-5">
                    <div
                      className="alert alert-success"
                      role="alert"
                    >
                      {createMessage}
                    </div>
                  </div>
                )}
                {errors.name && (
                  <div className="invalid-feedback">{errors.name.message}</div>
                )}
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
                <button
                  type="submit"
                  className="btn btn-primary"
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
