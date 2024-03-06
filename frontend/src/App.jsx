import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";
import { Route, Routes, NavLink } from "react-router-dom";
import LoginSuccessful from "./components/LoginSuccessful";

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route
          path="/"
          element={<Main />}
        />
        <Route
          path="/login"
          element={<Login />}
        />
        <Route
          path="/register"
          element={<Register />}
        />
        <Route
          path="/LoginSuccessful"
          element={<LoginSuccessful />}
        />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
