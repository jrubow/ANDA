import React, { useState } from "react";
import "../css/pages/loginpage.css"; // Import CSS
import { Link } from "react-router-dom";

function LoginPage() {
  const [isAdmin, setIsAdmin] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

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
        <input type="text" placeholder="Username" className="input-field" />
        
        {/* Password Input with Show/Hide Toggle */}
        <div className="password-container">
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Password"
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
        <button className="login-button">Login</button>
        <button className="login-button">Guest</button>

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
