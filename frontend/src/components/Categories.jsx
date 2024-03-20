import { useState, useEffect } from "react";
// import { Link } from "react-router-dom";
// import { useForm } from "react-hook-form";
// import { useNavigate } from "react-router-dom";
// import { FaEye, FaEyeSlash } from "react-icons/fa";
// import "./Login.css";
import UpdateCategory from "./UpdateCategory";
// import DeleteCategory from "./DeleteCategory";
import AddCategory from "./AddCategory";

const categories = [
  {
    id: 1,
    name: "Category 1",
  },
  {
    id: 2,
    name: "Category 2",
  },
  {
    id: 3,
    name: "Category 3",
  },
  {
    id: 4,
    name: "Category 4",
  },
  {
    id: 5,
    name: "Category 5",
  },
  {
    id: 6,
    name: "Category 6",
  },
  {
    id: 7,
    name: "Category 7",
  },
  {
    id: 8,
    name: "Category 8",
  },
  {
    id: 9,
    name: "Category 9",
  },
  {
    id: 10,
    name: "Category 10",
  },
  {
    id: 11,
    name: "Category 11",
  },
];

export default function Categories() {
  //const [categories, setCategories] = useState([]);

  const getCategories = async () => {
    console.log("Get categories");
    console.log(localStorage);
    // const url = "http://localhost:8080/";
    // const response = await fetch(`${url}categories`, {
    //   method: "GET",
    //   "Content-Type": "application/json",
    // });
    // const resp = await response.json();
    //setCategories(resp);
  };

  // function updateCategory(id, name) {
  //   console.log(`Update ${id}`);
  //   //setUpdate(true);
  // }

  // function deleteCategory(id) {
  //   console.log(`Delete ${id}`);
  // }

  function addCategory() {}

  useEffect(() => {
    getCategories();
  }, []);

  //console.log(update);
  return (
    <>
      <div className="container">
        <h1 className="text-center">Manage Recipe Categories</h1>
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
              {localStorage.role === "ROLE_ADMIN" && (
                <tr>
                  {/* <th></th>
                <td></td> */}
                  <th>
                    <button
                      className="btn btn-primary"
                      type="button"
                      data-bs-toggle="modal"
                      data-bs-target="#addCategoryModal"
                      onClick={() => addCategory()}
                    >
                      Add category
                    </button>
                    <AddCategory />
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
