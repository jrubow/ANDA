import React, { useContext, useState } from "react";
import "../css/pages/loginpage.css"; // Import CSS
import { Link, useNavigate } from "react-router-dom";
import axios from "axios"
import { UserContext } from "../components/UserProvider";

function LoginPage() {
  const [isAdmin, setIsAdmin] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const {user, setUser, loggedIn, setLoggedIn} = useContext(UserContext)
  const navigate = useNavigate();

  async function login(e) {
    e.preventDefault(); 

    try {
      console.log(username)
      console.log(password)
      const response = await axios.post("/api/login", {
        username: username,
        password: password
      });

      console.log("Login Successful:", response.data);
      setLoggedIn(true)
      setUser({
        username: username,
        first_name: response.data.first_name,
        last_name: response.data.last_name,
        password: response.data.password,
        email: response.data.email,
        address: response.data.address,
        phone_number: response.data.phone_number,
        share_location: response.data.share_location,
        agency_id: response.data.agency_id === undefined ? null : response.data.agency_id
      })  
      navigate('/home');
    } catch (error) {
      console.error("Login Failed:", error.response ? error.response.data : error.message);
    }
  }

  async function loginGuest(e) {
    e.preventDefault(); // Prevent default action

    try {
      const response = await axios.post("https://your-api-url.com/login-guest", {
        guest: true
      });

      console.log("Guest Login Successful:", response.data);
      // Handle guest login
    } catch (error) {
      console.error("Guest Login Failed:", error.response ? error.response.data : error.message);
    }
  }

  return (
    <div className="container">
      {/* Container */}
      <div className="login-card">
        {/* Logo */}
        <div className="logo-container">
          <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
        </div>

        {/* Title */}
        <h2 className="title">Login</h2>

        {/* Input Fields */}
        <input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)} className="input-field" />
        
        {/* Password Input with Show/Hide Toggle */}
        <div className="password-container">
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Password"
            className="input-field password-input"
            onChange={(e) => setPassword(e.target.value)}
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
        <button onClick={loginGuest} className="button">Guest</button>

        {/* Links */}
        <div className="links">
          <Link to="/register">Create Account</Link > | <a href="#">Forgot Password?</a>
        </div>
        <div className="terms">
          <a href="#">Terms</a> | <a href="#">Privacy Policy</a>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
