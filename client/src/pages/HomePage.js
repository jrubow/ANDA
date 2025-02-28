import React from "react";
import "../css/pages/homepage.css"; // Import CSS
import { Link } from "react-router-dom";

function HomePage() {
  return (
    <div className="container">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="alert-box">
          <h2>West Lafayette</h2>
          <p>2 Alerts</p>
        </div>
        <nav>
          <ul>
            <li><Link to="/report">Make a Report</Link></li>
            <li><Link to="/navigate">Navigate</Link></li>
            <li><Link to="/layers">Map Layers</Link></li>
          </ul>
        </nav>
        <div className="bottom-menu">
          <ul>
            <li><Link to="/settings">Account</Link></li>
            <li><Link to="/logout">Log Out</Link></li>
          </ul>
        </div>
      </div>

      {/* Map Section */}
      <div className="map-container">
        <img src="/MainMap.png" alt="Map" className="map-image" />
      </div>
    </div>
  );
}

export default HomePage;
