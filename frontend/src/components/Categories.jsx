import { useState, useEffect } from "react";
// import { Link } from "react-router-dom";
// import { useForm } from "react-hook-form";
// import { useNavigate } from "react-router-dom";
// import { FaEye, FaEyeSlash } from "react-icons/fa";
// import "./Login.css";
import UpdateCategory from "./UpdateCategory";
// import DeleteCategory from "./DeleteCategory";
import AddCategory from "./AddCategory";

export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [categoryAdded, setCategoryAdded] = useState(false);

  const getCategories = async () => {
    console.log("Get categories");

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

  // function updateCategory(id, name) {
  //   console.log(`Update ${id}`);
  //   //setUpdate(true);
  // }

  // function deleteCategory(id) {
  //   console.log(`Delete ${id}`);
  // }

  const addCategory = () => {
    console.log("BBBBBBBBBBBBBBBBB");
    setCategoryAdded(true);
  };

  useEffect(() => {
    getCategories();
  }, [categoryAdded]);

  return (
    <>
      <div className="container">
        <h1 className="text-center">Recipe Categories</h1>
        <div className="col-12 col-lg-10 offset-lg-1 col-xl-8 offset-xl-2">
          <table className="table">
            <tbody>
              {categories.map((category) => {
                return (
                  <tr key={category.id}>
                    <th scope="row">{category.name}</th>
                    {/* <td className="text-end"> */}
                    {/* <button
                        className="btn btn-primary"
                        type="button"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal"
                        onClick={() =>
                          updateCategory(category.id, category.name)
                        }
                      >
                        Update
                      </button> */}
                    {/* 
                      <UpdateCategory
                        categoryId={category.id}
                        categoryName={category.name}
                      /> */}
                    {/* </td> */}
                    {/* <td className="text-end"> */}
                    {/* <button
                        className="btn btn-primary"
                        onClick={() => deleteCategory(category.id)}
                      >
                        Delete
                      </button> */}
                    {/* <DeleteCategory
                      categoryId={category.id}
                      deleteCategory={deleteCategory}
                    /> */}
                    {/* </td> */}
                  </tr>
                );
              })}
              {localStorage.getItem("role") === "ROLE_ADMIN" && (
                <tr>
                  {/* <th></th>
                <td></td> */}
                  <th>
                    <button
                      className="btn btn-primary"
                      type="button"
                      data-bs-toggle="modal"
                      data-bs-target="#addCategoryModal"
                      // onClick={() => addCategory()}
                    >
                      Add category
                    </button>
                    <AddCategory addCategory={addCategory} />
                  </th>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
