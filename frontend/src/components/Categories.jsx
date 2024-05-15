import { useState, useEffect } from "react";
import AddCategory from "./AddCategory";
import UpdateCategory from "./UpdateCategory";
import DeleteCategory from "./DeleteCategory";
import ToggleCategoryVisibility from "./ToggleCategoryVisibility";
import "./Categories.css";

export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [categoryChanged, setCategoryChanged] = useState();

  const role = localStorage.getItem("role");

  useEffect(() => {
    getCategories();
  }, [categoryChanged]);

  const getCategories = async () => {
    const url = "http://localhost:8080/";
    const response = await fetch(`${url}categories`, {
      method: "GET",
      "Content-Type": "application/json",
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

    const resp = await response.json();
    setCategories(resp);
    setCategoryChanged(false);
  };

  const changeCategory = () => {
    setCategoryChanged(true);
  };

  const closeModal = (id) => {
    const modal = document.getElementById(id);
    modal.classList.remove("show");

    // Check if modalBackdrop exists before attempting to remove it
    const modalBackdrop = document.getElementsByClassName("modal-backdrop")[0];
    if (modalBackdrop) {
      modalBackdrop.parentNode.removeChild(modalBackdrop); // Remove the modal backdrop
    }
  };

  return (
    <>
      <div className="container">
        <h1 className="text-center mt-3">Recipe Categories</h1>
        <div className="col-12 col-lg-10 offset-lg-1 col-xl-8 offset-xl-2">
          <table className="table">
            <tbody>
              {categories.map((category) => {
                return (
                  <tr key={category.id}>
                    <th
                      scope="row"
                      className={category.enabled ? "" : "text-body-tertiary"}
                    >
                      {category.name}
                    </th>

                    {role === "ROLE_ADMIN" && (
                      <td className="d-flex justify-content-end">
                        <button
                          className="btn button-category mx-1"
                          type="button"
                          data-bs-toggle="modal"
                          data-bs-target={"#updateCategoryModal" + category.id}
                        >
                          Update
                        </button>
                        <UpdateCategory
                          categoryId={category.id}
                          categoryName={category.name}
                          closeModal={closeModal}
                          changeCategory={changeCategory}
                        />
                        <button
                          className="btn button-category mx-1"
                          type="button"
                          data-bs-toggle="modal"
                          data-bs-target={"#deleteCategoryModal" + category.id}
                        >
                          Delete
                        </button>
                        <DeleteCategory
                          categoryId={category.id}
                          categoryName={category.name}
                          closeModal={closeModal}
                          changeCategory={changeCategory}
                        />
                        <button
                          className="btn button-category mx-1"
                          type="button"
                          data-bs-toggle="modal"
                          data-bs-target={"#toggleCategoryModal" + category.id}
                        >
                          {category.enabled ? "Disable" : "Enable"}
                        </button>
                        <ToggleCategoryVisibility
                          categoryId={category.id}
                          categoryName={category.name}
                          categoryEnabled={category.enabled}
                          closeModal={closeModal}
                          changeCategory={changeCategory}
                        />
                      </td>
                    )}
                  </tr>
                );
              })}

              {role === "ROLE_ADMIN" && (
                <tr>
                  <th scope="row"></th>
                  <td className="d-flex justify-content-end">
                    <button
                      className="btn button-category mx-1"
                      type="button"
                      data-bs-toggle="modal"
                      data-bs-target="#addCategoryModal"
                    >
                      Add category
                    </button>

                    <AddCategory
                      changeCategory={changeCategory}
                      closeModal={closeModal}
                    />
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
