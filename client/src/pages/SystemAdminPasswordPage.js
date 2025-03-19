import React, { useState, useContext } from "react";
import "../css/pages/registerpage.css";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { UserContext } from "../components/UserProvider";

function SystemAdminPasswordPage() {
    const navigate = useNavigate();
    const { setLoggedIn, setIsGuest } = useContext(UserContext);
    const [errorMessage, setErrorMessage] = useState(""); // Store error message
    const [showPopup, setShowPopup] = useState(false); // Control popup visibility

    const [formData, setFormData] = useState({
        systemAdminId: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    async function register(e) {
        e.preventDefault();

        // Check if passwords match
        if (formData.password !== formData.confirmPassword) {
            setErrorMessage("Passwords do not match");
            setShowPopup(true);
            return;
        }

        try {
            const userJson = {
                systemAdminId: formData.systemAdminId,
                email: formData.email,
                password: formData.password,
            };

            const response = await axios.post("/api/register", userJson, {
                headers: {
                    "X-API-KEY": "admin",
                    "Content-Type": "application/json",
                },
            });
            console.log("Register Successful:", response.data);
            setLoggedIn(false);
            setIsGuest(false);
            navigate("/login");
        } catch (error) {
            if (error.response) {
                setErrorMessage(error.response.data);
                setShowPopup(true);
            } else {
                setErrorMessage("Server error. Please try again.");
                setShowPopup(true);
            }
        }
    }

    return (
        <div className="register-container">
            <form className="register-form">
                <div className="logo-container">
                    <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
                </div>
                <h2>Set Password</h2>

                <label>System Admin ID</label>
                <input
                    type="text"
                    name="systemAdminId"
                    value={formData.systemAdminId}
                    onChange={handleChange}
                    required
                />

                <label>Email</label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                <label>Password</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                <label>Confirm Password</label>
                <input
                    type="password"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                />

                <button type="submit" className="button" onClick={register}>
                    Set Password
                </button>

                <div className="links">
                    <Link to="/login">Login</Link> | <Link to="/sysadmin/request">Request Access</Link>
                </div>

            </form>

            {/* Popup Modal */}
            {showPopup && (
                <div className="popup">
                    <div className="popup-content">
            <span className="close-btn" onClick={() => setShowPopup(false)}>
              &times;
            </span>
                        <p>{errorMessage}</p>
                    </div>
                </div>
            )}
        </div>
    );
}

export default SystemAdminPasswordPage;
