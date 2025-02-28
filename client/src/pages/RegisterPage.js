import React, { useState } from 'react';
import "../css/pages/registerpage.css";

function RegisterPage() {
  const [formData, setFormData] = useState({
    email: '',
    firstName: '',
    lastName: '',
    dob: '',
    password: '',
    confirmPassword: '',
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
          name="firstName"
          value={formData.firstName}
          onChange={handleChange}
          required
        />

        <label>Last Name</label>
        <input
          type="text"
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
          required
        />

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

        <button type="submit" className="button">Create Account</button>
      </form>
    </div>
  );
}

export default RegisterPage;
