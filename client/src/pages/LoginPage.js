import React, { useState } from "react";
import "../css/pages/loginpage.css"; // Import CSS

function LoginPage() {
  const [isTrusted, setIsTrusted] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  return (
    <div className="container">
      {/* Container */}
      <div className="login-card">
        {/* Logo */}
        <div className="logo-container">
          <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
        </div>

        {/* Title */}
        <h2 className="title">User Login</h2>

        {/* Input Fields */}
        <input
          type="text"
          placeholder="Username"
          className="input-field"
        />
        <input
          type="password"
          placeholder="Password"
          className="input-field"
        />

        {/* Trust Checkbox */}
        <label className="checkbox-label">
          <input
            type="checkbox"
            checked={isTrusted}
            onChange={() => setIsTrusted(!isTrusted)}
            className="checkbox"
          />
          Trust this device
        </label>

        {/* Admin Toggle */}
        <label className="checkbox-label">
          <input
            type="checkbox"
            checked={isAdmin}
            onChange={() => setIsAdmin(!isAdmin)}
            className="checkbox"
          />
          Admin
        </label>

        {/* Login Button */}
        <button className="login-button">
          Login
        </button>

        {/* Links */}
        <div className="links">
          <a href="#">Create Account</a> | 
          <a href="#">Forgot Password?</a>
        </div>
        <div className="terms">
          <a href="#">Terms</a> | 
          <a href="#">Privacy Policy</a>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
