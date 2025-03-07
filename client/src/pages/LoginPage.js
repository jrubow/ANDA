import React, { useContext, useState } from "react";
import "../css/pages/loginpage.css"; // Import CSS
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { UserContext } from "../components/UserProvider";

function LoginPage() {
  const [isAdmin, setIsAdmin] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(""); // Store error message
  const { user, setUser, loggedIn, setLoggedIn, isGuest, setIsGuest } = useContext(UserContext);
  const navigate = useNavigate();

  async function login(e) {
    e.preventDefault();

    try {
      console.log(username);
      console.log(password);
      const response = await axios.post("/api/login", {
        username: username,
        password: password,
        },
        {
          headers: {
            "X-API-KEY": "user",
                "Content-Type": "application/json"
          }
        }
      )

      // const filterResponse = await axios.post("/api/filter", {
      //   username: username
      //   },
      //   {
      //     headers: {
      //       "X-API-KEY": "user",
      //       "Content-Type": "application/json"
      //     }
      //   }
      // )

      console.log("Login Successful:", response.data);
      setLoggedIn(true);
      setUser({
        username: username,
        first_name: response.data.first_name,
        last_name: response.data.last_name,
        password: response.data.password,
        email: response.data.email,
        address: response.data.address,
        phone_number: response.data.phone_number,
        share_location: response.data.share_location,
        agency_id: response.data.agency_id === undefined ? null : response.data.agency_id,
        // ice: filterResponse.ice,
        // flood: filterResponse.flood,
        temperature: response.data.temperature,
        snow: response.data.snow,
        rain: response.data.rain,
        humidity: response.data.humidity
      });
      setIsGuest(false)
      navigate("/home")
    } catch (error) {
      console.error("Login Failed:", error.response ? error.response.data : error.message);
      setErrorMessage(error.response.data); // Show error message
      setUsername(""); // Clear username field
      setPassword(""); // Clear password field
    }
  }

  async function loginGuest(e) {
    e.preventDefault();

    try {
      const response = await axios.post("/api/login-guest", {}, {
        headers: {
          "X-API-KEY": "guest",
          "Content-Type": "application/json"
        }
      });

      console.log("Guest Login Successful:", response.data);
      // Handle guest login
      setIsGuest(true)
      navigate("/home")
    } catch (error) {
      console.error("Guest Login Failed:", error.response ? error.response.data : error.message);
    }
  }

  return (
    <div className="container">
      <div className="login-card">
        <div className="logo-container">
          <img src="/ANDA_Logo.png" alt="Logo" className="logo" />
        </div>

        <h2 className="title">Login</h2>

        {errorMessage && <p className="error-message">{errorMessage}</p>} {/* Display error message */}

        <input
          type="text"
          placeholder="Username"
          value={username} // Controlled input
          onChange={(e) => setUsername(e.target.value)}
          className="input-field"
        />

        <div className="password-container">
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Password"
            value={password} // Controlled input
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
        <button onClick={loginGuest} className="button">Guest</button>

        <div className="links">
          <Link to="/register">Create Account</Link> | <a href="#">Forgot Password?</a>
        </div>
        <div className="terms">
          <a href="#">Terms</a> | <a href="#">Privacy Policy</a>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
