import React, { useState, useContext } from "react";
import "../css/pages/homepage.css";
import { Link } from "react-router-dom";
import { UserContext } from "../components/UserProvider"
import { LoadScript, GoogleMap, Marker, InfoWindow } from "@react-google-maps/api";

function HomePage() {
  // State to toggle the filters popup
  const [showFiltersPopup, setShowFiltersPopup] = useState(false);
  const {user, setUser, loggedIn, setLoggedIn} = useContext(UserContext)

  const toggleFiltersPopup = () => {
    setShowFiltersPopup(!showFiltersPopup);
  };

  function logout(e) {
    e.preventDefault()
    localStorage.removeItem("user");
    setLoggedIn(false)
    console.log("Logging out!")
  }

  const position = { lat: 40.4273, lng: -86.9141};
  const [open, setOpen] = useState(false);

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
            <Link to="/settings">
              <button className="button">
                Account
              </button>
            </Link>
            <Link to="/login">
              <button className="button" onClick={logout}>
                Log Out
              </button>
            </Link>
          </ul>
        </div>
      </div>

      {/* Map Section
      <div className="map-container">
        <img src="/MainMap.png" alt="Map" className="map-image" />
      </div>
      */}
      <div className="map-container">
        <LoadScript googleMapsApiKey={process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY}>
          <GoogleMap
            zoom={14.567829}
            center={position}
            mapContainerStyle={{ height: "100%", width: "100%" }}
            mapId={process.env.NEXT_PUBLIC_MAP_ID}
          >
            <Marker position={position} onClick={() => setOpen(true)} />
            {open && (
              <InfoWindow position={position} onCloseClick={() => setOpen(false)}>
                <div>
                  <p>Clock tower</p>
                </div>
              </InfoWindow>
            )}
          </GoogleMap>
        </LoadScript>
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
