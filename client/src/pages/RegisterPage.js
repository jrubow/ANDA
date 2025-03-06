import React, { useState, useContext } from "react";
import "../css/pages/registerpage.css";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { UserContext } from "../components/UserProvider";

function RegisterPage() {
  const navigate = useNavigate();
  const { setLoggedIn , loggedIn, user, setUser, isGuest, setIsGuest} = useContext(UserContext);
  const [admin, setAdmin] = useState(false);
  const [errorMessage, setErrorMessage] = useState(""); // Store error message
  const [showPopup, setShowPopup] = useState(false); // Control popup visibility

  const [formData, setFormData] = useState({
    username: "",
    email: "",
    first_name: "",
    last_name: "",
    address: "",
    phone_number: "",
    password: "",
    confirmPassword: "",
    agency_id: "",
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
    try {
      var userJson = {}
      if (admin) {
        userJson = {
          username: formData.username,
          password: formData.password,
          email: formData.email,
          first_name: formData.first_name,
          last_name: formData.first_name,
          address: formData.address,
          phone_number: formData.phone_number,
          share_location: 0,
          agency_id: parseInt(formData.agency_id)
        }
      } else {
        userJson = {
          username: formData.username,
          password: formData.password,
          email: formData.email,
          first_name: formData.first_name,
          last_name: formData.first_name,
          address: formData.address,
          phone_number: formData.phone_number,
          share_location: 0
        }
      }
      console.log(userJson)
      const response = await axios.post("/api/register", userJson, {
        headers: {
          "X-API-KEY": "user",
          "Content-Type": "application/json"
        }
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
        <h2>Sign Up</h2>

        <label>Username</label>
        <input type="text" name="username" value={formData.username} onChange={handleChange} required />

        <label>Email</label>
        <input type="email" name="email" value={formData.email} onChange={handleChange} required />

        <label>First Name</label>
        <input type="text" name="first_name" value={formData.first_name} onChange={handleChange} required />

        <label>Last Name</label>
        <input type="text" name="last_name" value={formData.last_name} onChange={handleChange} required />

        <label>Address</label>
        <input type="text" name="address" value={formData.address} onChange={handleChange} required />

        <label>Phone Number</label>
        <input type="text" name="phone_number" value={formData.phone_number} onChange={handleChange} required />

        {admin && (
          <>
            <label>Agency ID</label>
            <input type="number" name="agency_id" value={formData.agency_id} onChange={handleChange} required />
          </>
        )}

        <label>Password</label>
        <input type="password" name="password" value={formData.password} onChange={handleChange} required />

        <label>Confirm Password</label>
        <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} required />

        <button type="submit" className="button" onClick={register}>
          Create Account
        </button>

        <div className="links">
          <Link to="/login">Login</Link> |{" "}
          <a href="#" onClick={() => setAdmin(!admin)}>
            {admin ? "General User?" : "Administrator?"}
          </a>
        </div>

        <div className="terms">
          <a href="#">Terms</a> | <a href="#">Privacy Policy</a>
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

export default RegisterPage;
