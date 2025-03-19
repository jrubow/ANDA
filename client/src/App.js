import { BrowserRouter, Routes, Route } from "react-router-dom";
import UserProvider from "./components/UserProvider"
import HomePage from "./pages/HomePage"
import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import SettingsPage from "./pages/SettingsPage"
import NavBar from "./components/NavBar"
import ReportPage from "./pages/ReportPage"
import DevicesPage from "./pages/Devices/DevicesPage"
import { default as RegisterDevicePage } from "./pages/Devices/RegisterPage";
import SystemAdminLoginPage from "./pages/SystemAdminLoginPage";
import SystemAdminRequestPage from "./pages/SystemAdminRequestPage";
import SystemAdminPasswordPage from "./pages/SystemAdminPasswordPage";
import SystemAdminHomePage from "./pages/SystemAdminHomePage";

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
            <Route path="/devices" element={<DevicesPage />}/>

            <Route path="/sysadmin/login" element={<SystemAdminLoginPage/>}/>
            <Route path="/sysadmin/request" element={<SystemAdminRequestPage/>}/>
            <Route path="/sysadmin/password" element={<SystemAdminPasswordPage/>}/>
            <Route path="/sysadmin/home" element={<SystemAdminHomePage/>}/>
          </Routes>
        </div>
      </BrowserRouter>
    </UserProvider>
  );
}

export default App;