import React, { useState } from "react";
import "../css/pages/homepage.css";
import { Link } from "react-router-dom";

function HomePage() {
  // State to toggle the filters popup
  const [showFiltersPopup, setShowFiltersPopup] = useState(false);

  const toggleFiltersPopup = () => {
    setShowFiltersPopup(!showFiltersPopup);
  };

  return (
    <div className="homepage-container">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="alert-box">
          <h2>West Lafayette</h2>
          <p>2 Alerts</p>
        </div>
        <nav>
          <ul>
            <li>
              <Link to="/report">Make a Report</Link>
            </li>
            <li>
              <Link to="/navigate">Navigate</Link>
            </li>
            <li>
              {/* Replace the Map Layers link with a button that opens the filters popup */}
              <button className="sidebar-btn" onClick={toggleFiltersPopup}>
                Map Layers
              </button>
            </li>
          </ul>
        </nav>
        <div className="bottom-menu">
          <ul>
            <li>
              <Link to="/settings">Account</Link>
            </li>
            <li>
              <Link to="/logout">Log Out</Link>
            </li>
          </ul>
        </div>
      </div>

      {/* Map Section */}
      <div className="map-container">
        <img src="/MainMap.png" alt="Map" className="map-image" />
      </div>

      {/* Filters Popup */}
      {showFiltersPopup && (
        <div className="filters-popup">
          <div className="filters-popup-content">
            <h3>Weather Filters</h3>
            <label>
              <input type="checkbox" name="temperature" /> Temperature
            </label>
            <label>
              <input type="checkbox" name="precipitation" /> Precipitation
            </label>
            <label>
              <input type="checkbox" name="windSpeed" /> Wind Speed
            </label>
            <label>
              <input type="checkbox" name="humidity" /> Humidity
            </label>
            <button className="close-btn" onClick={toggleFiltersPopup}>
              Close
            </button>
          </div>
          <div className="popup-overlay" onClick={toggleFiltersPopup}></div>
        </div>
      )}
    </div>
  );
}

export default HomePage;
