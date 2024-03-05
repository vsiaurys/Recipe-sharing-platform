import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import Login from "./components/Login";
import { Route, Routes, NavLink } from "react-router-dom";

function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
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
      </Routes>
      <footer className="mt-auto footer">
        <Footer />
      </footer>
    </div>
  );
}

export default App;
