import { useState, useContext } from "react";
import {UserContext} from "../components/UserProvider"
import "../css/pages/settingspage.css";


function SettingsPage() {
  const {user, setUser, loggedIn, setLoggedIn} = useContext(UserContext)

  const [showFiltersPopup, setShowFiltersPopup] = useState(false);

  const [weatherFilters, setWeatherFilters] = useState({
    rain: false,
    sunny: false,
    cloudy: false,
    windy: false,
  });

  // Handle share location toggle (Y/N)
  const handleShareLocationChange = (e) => {
    const value = e.target.value === "Y";
    // setFormData({ ...formData, shareLocation: value });
  };

  // Handle checkbox changes for weather filters
  const handleWeatherFilterChange = (e) => {
    const { name, checked } = e.target;
    setWeatherFilters({
      ...weatherFilters,
      [name]: checked,
    });
  };

  return (
    <div className="settings-page-container">
      <div className="profile-section">
        {/* Left side: Profile Photo + "Add Photo" */}
        <div className="profile-photo">
          <img
            src="lego_photo.png"
            alt="Profile"
            className="profile-img"
          />
          <button className="add-photo-btn">Add Profile Photo</button>
        </div>

        {/* Right side: User Details */}
        <div className="user-info">
          {/* Full Name + Update Password */}
          <div className="info-row">
            <div className="label">Username</div>
            <div className="value">{user.username || "Not set"}</div>
          </div>
          <div className="info-row">
            <div className="label">Full Name</div>
            <div className="value">{user.first_name || "Not "} {user.last_name || "set"}</div>
            <button className="update-btn">Update Password</button>
          </div>

          {/* Current Address + Share Location (Y/N) */}
          <div className="info-row">
            <div className="label">Current Address</div>
            <div className="value">{user.address || "Not set"}</div>
            <div className="share-location">
              <span>Share location:</span>
              <select
                value={user.share_location ? "Y" : "N"}
                onChange={handleShareLocationChange}
              >
                <option value="Y">Y</option>
                <option value="N">N</option>
              </select>
            </div>
          </div>

          {/* Email + Update Email */}
          <div className="info-row">
            <div className="label">Email</div>
            <div className="value">{user.email || "Not set"}</div>
            <button className="update-btn">Update Email</button>
          </div>

          {/* Primary Phone + Update # */}
          <div className="info-row">
            <div className="label">Primary Phone</div>
            <div className="value">{user.phone_number || "Not set"}</div>
            <button className="update-btn">Update #</button>
          </div>

          {/* Filter Preferences */}
          <div className="info-row">
            <div className="label">Filter Preferences</div>
            <button
              className="update-btn"
              onClick={() => setShowFiltersPopup(true)}
            >
              Edit Filters
            </button>
          </div>
        </div>
      </div>
      {/* Modal Popup for Weather Filters */}
      {showFiltersPopup && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Weather Filters</h3>
            <div className="checkbox-group">
              <label>
                <input
                  type="checkbox"
                  name="rain"
                  checked={weatherFilters.rain}
                  onChange={handleWeatherFilterChange}
                />
                Rain
              </label>
              <label>
                <input
                  type="checkbox"
                  name="sunny"
                  checked={weatherFilters.sunny}
                  onChange={handleWeatherFilterChange}
                />
                Sunny
              </label>
              <label>
                <input
                  type="checkbox"
                  name="cloudy"
                  checked={weatherFilters.cloudy}
                  onChange={handleWeatherFilterChange}
                />
                Cloudy
              </label>
              <label>
                <input
                  type="checkbox"
                  name="windy"
                  checked={weatherFilters.windy}
                  onChange={handleWeatherFilterChange}
                />
                Windy
              </label>
            </div>
            <button
              className="close-modal-btn"
              onClick={() => setShowFiltersPopup(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default SettingsPage;
