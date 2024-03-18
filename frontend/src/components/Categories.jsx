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
  //const [update, setUpdate] = useState(false);

  function getCategories() {
    console.log("Get categories");
    //   //console.log(`Update ${id}`);
    //   //const url = "http://localhost:8080/";
    //   // const getCategories = async () => {
    //   //   const response = await fetch(`${url}categories`, {
    //   //     method: "GET",
    //   //     headers: { Authorization: "Basic " + btoa("aaaaaaaa:bbbbbbbb") },
    //   //   });
    //   //   const resp = await response.json();
    //   //   setCategoires(resp);
    //   // };
  }

  function updateCategory(id, name) {
    console.log(`Update ${id}`);
    //setUpdate(true);
  }

  function deleteCategory(id) {
    console.log(`Delete ${id}`);
  }

  function addCategory() {
    console.log(`Add new category`);
  }

  // useEffect(() => {
  //   getCategories();
  // }, [update]);

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
                    <td className="text-end">
                      <button
                        className="btn btn-primary"
                        type="button"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal"
                        onClick={() =>
                          updateCategory(category.id, category.name)
                        }
                      >
                        Update
                      </button>

                      <UpdateCategory
                        categoryId={category.id}
                        categoryName={category.name}
                      />
                    </td>
                    <td className="text-end">
                      <button
                        className="btn btn-primary"
                        onClick={() => deleteCategory(category.id)}
                      >
                        Delete
                      </button>
                      {/* <DeleteCategory
                      categoryId={category.id}
                      deleteCategory={deleteCategory}
                    /> */}
                    </td>
                  </tr>
                );
              })}
              <tr>
                <th></th>
                <td></td>
                <td className="text-end">
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
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}

/////////////////////////
// import { useState, useEffect } from "react";

// export default function Movies() {
//   const url = "http://localhost:8080/";
//   const [movies, setMovies] = useState([]);
//   const [deleteId, setDeleteId] = useState(0);
//   const [update, setUpdate] = useState(false);

//   const getMovies = async () => {
//     const response = await fetch(`${url}movies`, {
//       method: "GET",
//       headers: { Authorization: "Basic " + btoa("aaaaaaaa:bbbbbbbb") },
//     });
//     const resp = await response.json();
//     setMovies(resp);
//   };

//   const deleteMovie = async (id) => {
//     try {
//       const response = await fetch(`${url}movies/${id}`, {
//         method: "DELETE",
//         headers: { Authorization: "Basic " + btoa("aaaaaaaa:bbbbbbbb") },
//       });
//     } catch (error) {
//       console.error("Error deleting movie:", error);
//     }

//     setDeleteId(id);
//   };

//   // const openModal = (movie) => {
//   //   document.getElementById("myModal").style.display = "block";

//   //   document.getElementById("title").defaultValue = movie.title;
//   //   document.getElementById("dateReleased").defaultValue = movie.dateReleased;
//   //   document.getElementById("lengthMinutes").defaultValue = movie.lengthMinutes;
//   // };

//   // const updateMovie = async (id) => {
//   //   try {
//   //     const response = await fetch(`${url}movies/${id}`, {
//   //       method: "PUT",
//   //       headers: { Authorization: "Basic " + btoa("aaaaaaaa:bbbbbbbb") },
//   //     });
//   //   } catch (error) {
//   //     console.error("Error updating movie:", error);
//   //   }
//   // };

//   // useEffect(() => {
//   //   getMovies();
//   // }, [deleteId]);

//   return (
//     <div
//       className="alert alert-success"
//       role="alert"
//     >
//       <table className="table">
//         <thead>
//           <tr>
//             <th scope="col">Id</th>
//             <th scope="col">Title</th>
//             <th scope="col">Date released</th>
//             <th scope="col">Length (minutes)</th>
//             <th scope="col"></th>
//             <th scope="col"></th>
//           </tr>
//         </thead>
//         <tbody>
//           {movies.map((movie) => {
//             return (
//               <tr key={movie.id}>
//                 <th scope="row">{movie.id}</th>
//                 <td>{movie.title}</td>
//                 <td>{movie.dateReleased}</td>
//                 <td>{movie.lengthMinutes}</td>
//                 <td>
//                   <button
//                     className="btn btn-primary"
//                     onClick={() => openModal(movie)}
//                   >
//                     Update
//                   </button>
//                   <UpdateMovie
//                     movieId={movie.id}
//                     title={movie.title}
//                     dateReleased={movie.dateReleased}
//                     lengthMinutes={movie.lengthMinutes}
//                     updateMovie={updateMovie}
//                   />
//                 </td>
//                 <td>
//                   <button
//                     className="btn btn-primary"
//                     onClick={() => deleteMovie(movie.id)}
//                   >
//                     Delete
//                   </button>
//                 </td>
//               </tr>
//             );
//           })}
//         </tbody>
//       </table>
//     </div>
//   );
// }
