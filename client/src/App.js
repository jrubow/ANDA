import { BrowserRouter, Routes, Route } from "react-router-dom";
import UserProvider from "./components/UserProvider"
import HomePage from "./pages/HomePage"
import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import SettingsPage from "./pages/SettingsPage"
import NavBar from "./components/NavBar"

function App() {
  return (
    <UserProvider>
      <BrowserRouter>
        <NavBar/>
        <div className="content-body">
          <Routes>
            <Route path="/home" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/settings" element={<SettingsPage />} />
          </Routes>
        </div>
      </BrowserRouter>
    </UserProvider>
  );
}

export default App;