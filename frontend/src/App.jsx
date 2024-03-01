import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Register from "./components/Register";
import { Route, Routes, NavLink } from "react-router-dom";

function App() {
  return (
    <>
      <div>
        <Header />
        <Main />
        <Footer />
      </div>
      <p>
        <Routes>
          <Route
            path="/register"
            element={<Register />}
          />
        </Routes>
      </p>
    </>
  );
}

export default App;
