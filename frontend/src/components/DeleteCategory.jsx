import { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import "./AddCategory.css";

export default function DeleteCategory({
  categoryId,
  categoryName,
  deleteCategory,
}) {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  const [createMessage, setCreateMessage] = useState();
  const [created, setCreated] = useState(false);

  const onSubmit = async (data) => {
    const url = "http://localhost:8080/";

    try {
      const response = await fetch(`${url}categories/${categoryId}`, {
        method: "DELETE",
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
        deleteCategory();
        setCreated(true);
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

  return (
    <>
      <form
        id="form-add-category"
        onSubmit={handleSubmit(onSubmit)}
      >
        <div
          className="modal fade"
          id="deleteCategoryModal"
          tabIndex={-1}
          aria-labelledby="DeleteCategoryLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1
                  className="modal-title fs-5"
                  id="DeleteCategoryLabel"
                >
                  Are you sure you want to delete category?
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                />
              </div>

              <div class="modal-body">
                <p>{categoryName}</p>
              </div>

              <div className="modal-footer">
                <button
                  type="button"
                  className="btn button-close"
                  data-bs-dismiss="modal"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn button-add-category"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </>
  );
}
