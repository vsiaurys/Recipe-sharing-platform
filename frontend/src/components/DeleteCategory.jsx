import "./DeleteCategory.css";

export default function DeleteCategory({
  categoryId,
  categoryName,
  changeCategory,
}) {
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
        changeCategory();
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

            <div className="modal-body">{categoryName}</div>

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
                className="btn button-delete-category"
                onClick={() => onSubmit()}
              >
                Delete
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
