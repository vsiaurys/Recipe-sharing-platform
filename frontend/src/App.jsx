import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import { Route, Routes, NavLink } from "react-router-dom";

function App() {
  return (
    <body className="d-flex flex-column min-vh-100">
      <Header />
      <Routes>
        <Route
          path="/"
          element={<Main />}
        />
        <Route
          path="/register"
          element={<Register />}
        />
      </Routes>
      <footer class="mt-auto footer">
        <Footer />
      </footer>
    </body>
  );
}

export default App;
