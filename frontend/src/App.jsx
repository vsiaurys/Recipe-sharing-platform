import "./App.css";
import { useState } from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";

import { Route, Routes, NavLink } from "react-router-dom";
import LoginSuccessful from "./components/LoginSuccessful";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <div className="d-flex flex-column min-vh-100">
      <Header
        isLoggedIn={isLoggedIn}
        setIsLoggedIn={setIsLoggedIn}
      />
      <Routes>
        <Route
          path="/"
          element={<Main />}
        />
        <Route
          path="/login"
          element={
            <Login
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn}
            />
          }
        />
        <Route
          path="/register"
          element={<Register />}
        />
        <Route
          path="/login-successful"
          element={<LoginSuccessful />}
        />
      </Routes>
      <footer className="mt-auto footer">
        <Footer />
      </footer>
    </div>
  );
}

export default App;
