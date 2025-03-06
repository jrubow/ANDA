import { BrowserRouter, Routes, Route } from "react-router-dom";
import UserProvider from "./components/UserProvider"
import HomePage from "./pages/HomePage"
import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import SettingsPage from "./pages/SettingsPage"
import NavBar from "./components/NavBar"
import ReportPage from "./pages/ReportPage"
import { default as RegisterDevicePage } from "./pages/Devices/RegisterPage";

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
            <Route path="/report" element={<ReportPage />} />
            <Route path="/devices/register" element={<RegisterDevicePage />} />
          </Routes>
        </div>
      </BrowserRouter>
    </UserProvider>
  );
}

export default App;