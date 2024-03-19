import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";
import { useState } from "react";

import { Route, Routes, NavLink } from "react-router-dom";

function App() {
  const [forceRender, setForceRender] = useState(false);
  const checkRole = () => {
    const role = localStorage.getItem("role");
    return role !== null && role !== "";
  };
  return (
    <div className="d-flex flex-column min-vh-100">
      <Header
        checkRole={checkRole}
        forceRender={forceRender}
        setForceRender={setForceRender}
      />
      <Routes>
        <Route
          path="/"
          element={
            <Main
              checkRole={checkRole}
              forceRender={forceRender}
            />
          }
        />
        <Route
          path="/login"
          element={<Login checkRole={checkRole} />}
        />
        <Route
          path="/register"
          element={<Register />}
        />
      </Routes>
      <footer className="mt-auto footer">
        <Footer />
      </footer>
    </div>
  );
}

export default App;
