import { useState, useEffect } from "react";
import AddCategory from "./AddCategory";
import UpdateCategory from "./UpdateCategory";
import DeleteCategory from "./DeleteCategory";
import "./Categories.css";

export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [categoryAdded, setCategoryAdded] = useState(false);
  const role = localStorage.getItem("role");

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
    setCategoryAdded(false);
  };

  const addCategory = () => {
    setCategoryAdded(true);
  };

  useEffect(() => {
    getCategories();
  }, [categoryAdded]);

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
                    <th scope="row">{category.name}</th>
                    {role === "ROLE_ADMIN" && (
                      <td className="d-flex justify-content-end">
                        <button
                          className="btn button-category mx-1"
                          type="button"
                          data-bs-toggle="modal"
                          data-bs-target="#updateCategoryModal"
                        >
                          Update
                        </button>
                        <UpdateCategory addCategory={addCategory} />
                        <button
                          className="btn button-category mx-1"
                          type="button"
                          data-bs-toggle="modal"
                          data-bs-target="#deleteCategoryModal"
                        >
                          Delete
                        </button>
                        <DeleteCategory
                          categoryId={category.id}
                          categoryName={category.name}
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
                    <AddCategory addCategory={addCategory} />
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
