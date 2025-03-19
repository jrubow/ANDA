import React, { useState, useContext } from "react";
import "../css/pages/registerpage.css";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { UserContext } from "../components/UserProvider";

function SystemAdminRequestPage() {
    const navigate = useNavigate();
    const { setLoggedIn, setIsGuest } = useContext(UserContext);
    const [errorMessage, setErrorMessage] = useState(""); // Store error message
    const [showPopup, setShowPopup] = useState(false); // Control popup visibility

    const [formData, setFormData] = useState({
        email: "",
        first: "",
        last: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const validateEmail = (email) => {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailPattern.test(email);
    };

    async function submitRequest(e) {
        e.preventDefault();
        if (!validateEmail(formData.email)) {
            setErrorMessage("Please enter a valid email address.");
            setShowPopup(true);
            return;
        }

        try {
            const userJson = {
                email: formData.email,
                first: formData.first,
                last: formData.last,
            };

            const response = await axios.post("/api/sysadmin/request", userJson, {
                headers: {
                    "X-API-KEY": "user",
                    "Content-Type": "application/json",
                },
            });

            console.log("Request Successful:", response.data);
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
                <h2>System Admin Request</h2>

                <label>Email</label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                <label>First Name</label>
                <input
                    type="text"
                    name="first"
                    value={formData.first}
                    onChange={handleChange}
                    required
                />

                <label>Last Name</label>
                <input
                    type="text"
                    name="last"
                    value={formData.last}
                    onChange={handleChange}
                    required
                />

                <button type="submit" className="button" onClick={submitRequest}>
                    Submit Request
                </button>

                <div className="links">
                    <Link to="/login">Login</Link> | <Link to="/sysadmin/password">Set Password</Link>
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

export default SystemAdminRequestPage;
