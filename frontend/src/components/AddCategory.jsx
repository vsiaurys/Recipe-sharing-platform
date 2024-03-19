import { useForm } from "react-hook-form";
import BadWords from "./BadWords";

export default function AddCategory() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log(data);

    // const postData = async () => {
    //   const send = await fetch("http://localhost:8080/actors", {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       Authorization: "Basic " + btoa("aaaaaaaa:bbbbbbbb"),
    //     },
    //     body: JSON.stringify(data),
    //   });
    // };

    // postData();
  };

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
          <div className="modal-dialog">
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
                  className={`form-control ${
                    errors.category ? "is-invalid" : ""
                  }`}
                  id="disabledTextInput"
                  placeholder="Enter new category"
                  {...register("category", {
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
                {errors.category && (
                  <div className="invalid-feedback">
                    {errors.category.message}
                  </div>
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
