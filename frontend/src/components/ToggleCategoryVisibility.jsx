import { useForm } from "react-hook-form";

export default function ToggleCategoryVisibility({
  categoryId,
  categoryName,
  categoryEnabled,
  closeModal,
  changeCategory,
}) {
  const { handleSubmit, setError } = useForm();

  const onSubmit = async (data) => {
    const url = "http://localhost:8080/";

    try {
      const response = await fetch(`${url}categories/${categoryId}`, {
        method: "PUT",
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
        closeModal(`toggleCategoryModal${categoryId}`);
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
      console.error("Error toggling category visibility: ", error);
    }
  };

  return (
    <>
      <form
        id={"form-toggle-category" + categoryId}
        onSubmit={handleSubmit(onSubmit)}
      >
        <div
          className={"modal fade"}
          id={"toggleCategoryModal" + categoryId}
          tabIndex={-1}
          aria-labelledby={"ToggleCategoryLabel" + categoryId}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1
                  className="modal-title fs-5"
                  id={"ToggleCategoryLabel" + categoryId}
                >
                  {categoryEnabled ? "Disable category?" : "Enable category?"}
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
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
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn button-delete-category"
                  onClick={onSubmit}
                >
                  {categoryEnabled ? "Disable" : "Enable"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </>
  );
}
