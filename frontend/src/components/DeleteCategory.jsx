import { useState } from "react";
import "./DeleteCategory.css";
import { useForm } from "react-hook-form";

export default function DeleteCategory({
  categoryId,
  categoryName,
  showModal,
  closeModal,
  changeCategory,
}) {
  const {
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  //const [deleted, setDeleted] = useState(false);

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
        console.log("AAAAAAAAAAAAAAAAAAAAAAAAA");
        changeCategory();
        //setDeleted(true);
        closeModal();
      }
      if (response.status === 400) {
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

  return (
    <>
      {showModal && (
        <div className="position-fixed top-0 left-0 w-100 h-100 bg-dark opacity-75 z-999"></div>
      )}
      <form
        id={"form-delete-category" + categoryId}
        onSubmit={handleSubmit(onSubmit)}
      >
        <div
          className={`modal fade ${showModal ? "show" : ""}`}
          style={{ display: showModal ? "block" : "none" }}
          tabIndex={-1}
          aria-labelledby={"DeleteCategoryLabel" + categoryId}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
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
                  //data-bs-dismiss="modal"
                  aria-label="Close"
                  onClick={closeModal}
                />
              </div>

              <div className="modal-body">{categoryName}</div>

              {/* {deleted && (
                <div className="container mx-auto mt-3">
                  <div
                    className="alert alert-success"
                    role="alert"
                  >
                    Successfully deleted
                  </div>
                </div>
              )} */}

              <div className="modal-footer">
                <button
                  type="button"
                  className="btn button-close"
                  data-bs-dismiss="modal"
                  onClick={closeModal}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn button-delete-category"
                  // onClick={onSubmit}
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
