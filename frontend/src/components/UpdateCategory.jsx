export default function UpdateCategory({ id, name, updateCategory }) {
  console.log(name);
  return (
    <>
      {/* //{" "}
      <div className="container">
        // <h1 className="text-center">Update Category</h1>
        //{" "}
      </div> */}
    </>
  );
}

//////////////////////////////////////////////////////////

//import React from "react";

// export default function UpdateCategory({ id, name }) {
//   const closeModal = () => {
//     document.getElementById("updateCategoryModal").style.display = "none";
//   };
//   return (
//     <>
//       <div>
//         <div
//           id="updateCategoryModal"
//           className="modal"
//         >
//           <div className="modal-dialog">
//             <div className="modal-content">
//               <div className="modal-header">
//                 <h4 className="modal-title">Update category</h4>
//               </div>

//               <div className="modal-body">
//                 <form>
//                   <div className="mb-3">
//                     <input
//                       name="name"
//                       id="category-name"
//                       type="text"
//                     />
//                   </div>
//                 </form>
//               </div>

//               <div className="modal-footer">
//                 <button
//                   type="button"
//                   className="btn btn-secondary"
//                   onClick={closeModal}
//                 >
//                   Close
//                 </button>
//                 <button
//                   type="button"
//                   className="btn btn-primary"
//                   onClick={closeModal}
//                 >
//                   Update
//                 </button>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </>
//   );
// }
