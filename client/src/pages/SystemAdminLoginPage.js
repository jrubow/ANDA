import React, { useContext, useState } from "react";
import "../css/pages/loginpage.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { UserContext } from "../components/UserProvider";
import SystemAdminRequestPage from "./SystemAdminRequestPage";

function SystemAdminLoginPage() {
    const [showPassword, setShowPassword] = useState(false);
    const [sysAdminId, setSysAdminId] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const { setUser, setLoggedIn, setIsGuest } = useContext(UserContext);
    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();

        if (!/^[0-9]{4}$/.test(sysAdminId)) {
            setErrorMessage("System Admin ID must be a 4-digit number.");
            return;
        }

        try {
            const response = await axios.post("/api/sysadmin/login", {
                    sysAdminId: sysAdminId,
                    password: password,
                },
                {
                    headers: {
                        "X-API-KEY": "user",
                        "Content-Type": "application/json"
                    }
                }
            );

            console.log("Login Successful:", response.data);
            setLoggedIn(true);
            setUser({
                sysAdminId: sysAdminId,
                first: response.data.first,
                last: response.data.last,
                email: response.data.email,
                verified: response.data.verified
            });
            setIsGuest(false);
            navigate("/sysadmin/home");
        } catch (error) {
            console.error("Login Failed:", error.response ? error.response.data : error.message);
            setErrorMessage(error.response?.data || "Login failed. Please try again.");
            setSysAdminId("");
            setPassword("");
        }
    }

    return (
        <div className="container">
            <div className="login-card">
                <div className="logo-container">
                    <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
                </div>

                <h2 className="title">System Admin Login</h2>

                {errorMessage && <p className="error-message">{errorMessage}</p>}

                <input
                    type="text"
                    placeholder="System Admin ID"
                    value={sysAdminId}
                    onChange={(e) => setSysAdminId(e.target.value.replace(/\D/g, '').slice(0, 4))}
                    className="input-field"
                />

                <div className="password-container">
                    <input
                        type={showPassword ? "text" : "password"}
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="input-field password-input"
                    />
                    <button
                        type="button"
                        className="show-password-btn"
                        onClick={() => setShowPassword(!showPassword)}
                    >
                        {showPassword ? "Hide" : "Show"}
                    </button>
                </div>

                <button onClick={login} className="button">Login</button>

                <div className="links">
                    <a href="#">Forgot Password?</a> |  <Link to="/sysadmin/request">Request Access</Link>
                </div>
            </div>
        </div>
    );
}

export default SystemAdminLoginPage;
