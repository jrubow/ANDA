import React, { useState, useContext } from 'react';
import "../css/pages/registerpage.css";
import axios from "axios"
import {useNavigate, Link} from "react-router-dom"
import { UserContext } from "../components/UserProvider";

function RegisterPage() {
  const navigate = useNavigate();
  const {user, setUser, loggedIn, setLoggedIn} = useContext(UserContext)
  const [admin, setAdmin] = useState(false)

  const [formData, setFormData] = useState({
    email: '',
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    agency_id:  '',
  });

  const [errors, setErrors] = useState({
    password: false,
    confirmPassword: false,
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
    console.log({
      username: formData.username,
      password: formData.password,
      email: formData.email,
      first_name: formData.first_name,
      last_name: formData.first_name,
      address: formData.address,
      phone_number: formData.phone_number,
      share_location: true
    })
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
      setLoggedIn(true)
      navigate('/home');
    } catch (error) {
      console.error("Register Failed:", error.response.data);
      if (error.response.data == "USER ALREADY EXISTS") {
        alert("Username already exists")
      } else if (error.response.data == "MAXIMUM PASSWORD ATTEMPTS REACHED") {
        alert("Maximum password attempts reached. Reset your password.")
      }
    }
  }

  const validatePassword = () => {
    const passwordValid =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
        formData.password
      );
    const confirmPasswordMatch = formData.password === formData.confirmPassword;
    setErrors({
      password: !passwordValid,
      confirmPassword: !confirmPasswordMatch,
    });
  };

  return (
    <div className="register-container">
      <form className="register-form">
        <div className="logo-container">
          <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
        </div>
        <h2>Sign Up</h2>

        <label>Username</label>
        <input
          type="username"
          name="username"
          value={formData.username}
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

        <label>First Name</label>
        <input
          type="text"
          name="first_name"
          value={formData.first_name}
          onChange={handleChange}
          required
        />

        <label>Last Name</label>
        <input
          type="text"
          name="last_name"
          value={formData.last_name}
          onChange={handleChange}
          required
        />

        <label>Address</label>
        <input
          type="text"
          name="address"
          value={formData.address}
          onChange={handleChange}
          required
        />

        <label>Phone Number</label>
        <input
          type="text"
          name="phone_number"
          value={formData.phone_number}
          onChange={handleChange}
          required
        />

        {
          admin ? 
          <>
          <label>Agency Id</label>
          <input
            type="number"
            name="agency_id"
            value={formData.agency_id}
            onChange={handleChange}
            required
          />
          </>
          :
          ""
        }

        <label>Password</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          onBlur={validatePassword}
          required
        />
        {errors.password && (
          <p className="error-text">Password must contain at least 8 characters, 1 lowercase, 1 uppercase, 1 number, and 1 special character.</p>
        )}

        <label>Confirm Password</label>
        <input
          type="password"
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          onBlur={validatePassword}
          required
        />
        {errors.confirmPassword && (
          <p className="error-text">Passwords do not match.</p>
        )}

        <div className="password-requirements">
          <ul>
            <li>8 characters minimum</li>
            <li>1 lowercase letter</li>
            <li>1 uppercase letter</li>
            <li>1 number</li>
            <li>1 special character</li>
          </ul>
        </div>

        <button type="submit" className="button" onClick={register}>Create Account</button>

        {/* Links */}
        <div className="links">
          <Link to="/login">Login</Link > | {admin ? <a href="#" onClick={() => setAdmin(false)}>General User?</a>  : <a href="#" onClick={() => setAdmin(true)}>Administrator?</a>}
        </div>
        <div className="terms">
          <a href="#">Terms</a> | <a href="#">Privacy Policy</a>
        </div>
      </form>
    </div>
  );
}

export default RegisterPage;
