import { useForm } from "react-hook-form";
import { useState } from "react";
import "./DeleteCategory.css";

export default function DeleteCategory({
  categoryId,
  categoryName,
  closeModal,
  changeCategory,
}) {
  const { handleSubmit, setError } = useForm();

  const [deleteMessage, setDeleteMessage] = useState();

  const onSubmit = async () => {
    const url = "http://localhost:8080/";

    try {
      const response = await fetch(`${url}categories/${categoryId}`, {
        method: "DELETE",
        headers: {
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
        setDeleteMessage(
          <span>
            Category <strong>{categoryName}</strong> successfully deleted
          </span>
        );

        setTimeout(() => {
          clearMessage();
          closeModal(`deleteCategoryModal${categoryId}`);
          changeCategory();
        }, 3000);
      }
      if (response.status === 404 || response.status === 400) {
        const responseData = await response.json();
        setError("name", {
          type: "server",
          message: `${responseData.name}`,
        });
      }
    } catch (error) {
      console.error("Error deleting category: ", error);
    }
  };

  const clearMessage = () => {
    setDeleteMessage("");
  };

  return (
    <>
      <form
        id={"form-delete-category" + categoryId}
        onSubmit={handleSubmit(onSubmit)}
      >
        <div
          className={"modal fade"}
          id={"deleteCategoryModal" + categoryId}
          tabIndex={-1}
          aria-labelledby={"DeleteCategoryLabel" + categoryId}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            {deleteMessage && (
              <div className="modal-content">
                <div className="modal-header">
                  <h1
                    className="modal-title fs-5"
                    id={"DeleteCategoryLabel" + categoryId}
                  >
                    Delete Category
                  </h1>
                </div>
                <div className="modal-body">
                  <div className="alert alert-success">{deleteMessage}</div>
                </div>
              </div>
            )}

            {!deleteMessage && (
              <div className="modal-content">
                <div className="modal-header">
                  <h1
                    className="modal-title fs-5"
                    id={"DeleteCategoryLabel" + categoryId}
                  >
                    Are you sure you want to delete category?
                  </h1>
                  <button
                    type="button"
                    className="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                    onClick={clearMessage}
                  />
                </div>

                <div className="modal-body">
                  <h1 className="modal-title fs-3">{categoryName}</h1>
                </div>

                <div className="modal-footer">
                  <button
                    type="button"
                    className="btn button-close"
                    data-bs-dismiss="modal"
                    onClick={clearMessage}
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="btn button-delete-category"
                  >
                    Delete
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
