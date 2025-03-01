import { useState, useContext } from "react";
import { UserContext } from "../components/UserProvider";
import "../css/pages/settingspage.css";

function SettingsPage() {
  const { user, setUser, loggedIn, setLoggedIn } = useContext(UserContext);

  // State for showing modals
  const [showPasswordModal, setShowPasswordModal] = useState(false);
  const [showEmailModal, setShowEmailModal] = useState(false);
  const [showPhoneModal, setShowPhoneModal] = useState(false);
  const [showAddressModal, setShowAddressModal] = useState(false);
  const [showFiltersPopup, setShowFiltersPopup] = useState(false);

  // State for new inputs
  const [newPassword, setNewPassword] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [newPhone, setNewPhone] = useState("");
  const [newAddress, setNewAddress] = useState("");

  // State for share location toggle
  const [shareLocation, setShareLocation] = useState(false);
  const toggleShareLocation = () => setShareLocation(!shareLocation);

  // Updated state for weather filters
  const [weatherFilters, setWeatherFilters] = useState({
    temperature: false,
    precipitation: false,
    windSpeed: false,
    humidity: false,
  });

  // Handlers for updating user info
  const handlePasswordChange = (e) => {
    e.preventDefault();
    if (newPassword) {
      console.log("Password updated to:", newPassword);
      setShowPasswordModal(false);
    }
  };

  const handleEmailChange = (e) => {
    e.preventDefault();
    if (newEmail) {
      console.log("Email updated to:", newEmail);
      setShowEmailModal(false);
    }
  };

  const handlePhoneChange = (e) => {
    e.preventDefault();
    if (newPhone) {
      console.log("Phone number updated to:", newPhone);
      setShowPhoneModal(false);
    }
  };

  const handleAddressChange = (e) => {
    e.preventDefault();
    if (newAddress) {
      console.log("Address updated to:", newAddress);
      setShowAddressModal(false);
    }
  };

  // Handler for weather filters
  const handleWeatherFilterChange = (e) => {
    const { name, checked } = e.target;
    setWeatherFilters((prevFilters) => ({
      ...prevFilters,
      [name]: checked,
    }));
  };

  return (
    <div className="settings-page-container">
      <div className="profile-section">
        {/* Left side: Profile Photo + "Add Photo" */}
        <div className="profile-photo">
          <img src="lego_photo.png" alt="Profile" className="profile-img" />
          <button className="update-btn">Add Profile Photo</button>
          <button className="update-btn" onClick={() => setShowFiltersPopup(true)}>Filter Preferences</button>
          <button className="update-btn" onClick={toggleShareLocation}>Share Location: {shareLocation ? "Yes" : "No"}</button>
        </div>

        {/* Right side: User Details */}
        <div className="user-info">
          {/* Username */}
          <div className="info-row">
            <div className="label">Username</div>
            <div className="value">{user.username || "Not set"}</div>
          </div>

          {/* Full Name */}
          <div className="info-row">
            <div className="label">Full Name</div>
            <div className="value">
              {user.first_name || "Not "} {user.last_name || "set"}
            </div>
          </div>

          {/* Update Password */}
          <div className="info-row">
            <div className="label">Password</div>
            <div className="value">**********</div>
            <button
              className="update-btn"
              onClick={() => setShowPasswordModal(true)}
            >
              Update Password
            </button>
          </div>

          {/* Update Email */}
          <div className="info-row">
            <div className="label">Email</div>
            <div className="value">{user.email || "Not set"}</div>
            <button
              className="update-btn"
              onClick={() => setShowEmailModal(true)}
            >
              Update Email
            </button>
          </div>

          {/* Update Phone Number */}
          <div className="info-row">
            <div className="label">Phone Number</div>
            <div className="value">{user.phone_number || "Not set"}</div>
            <button
              className="update-btn"
              onClick={() => setShowPhoneModal(true)}
            >
              Update Phone #
            </button>
          </div>

          {/* Current Address Row */}
          <div className="info-row">
            <div className="label">Current Address</div>
            <div className="value">{user.address || "Not set"}</div>
            <button
              className="update-btn"
              onClick={() => setShowAddressModal(true)}
            >
              Update Address
            </button>
          </div>
        </div>
      </div>

      {/* Password Modal */}
      {showPasswordModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Update Password</h3>
            <form onSubmit={handlePasswordChange}>
              <input
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                placeholder="Enter new password"
                required
              />
              <button type="submit" className="close-modal-btn">
                Update Password
              </button>
            </form>
            <button
              className="close-modal-btn"
              onClick={() => setShowPasswordModal(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}

      {/* Email Modal */}
      {showEmailModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Update Email</h3>
            <form onSubmit={handleEmailChange}>
              <input
                type="email"
                value={newEmail}
                onChange={(e) => setNewEmail(e.target.value)}
                placeholder="Enter new email"
                required
              />
              <button type="submit" className="close-modal-btn">
                Update Email
              </button>
            </form>
            <button
              className="close-modal-btn"
              onClick={() => setShowEmailModal(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}

      {/* Phone Modal */}
      {showPhoneModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Update Phone Number</h3>
            <form onSubmit={handlePhoneChange}>
              <input
                type="tel"
                value={newPhone}
                onChange={(e) => setNewPhone(e.target.value)}
                placeholder="Enter new phone number"
                required
              />
              <button type="submit" className="close-modal-btn">
                Update Phone Number
              </button>
            </form>
            <button
              className="close-modal-btn"
              onClick={() => setShowPhoneModal(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}

      {/* Address Modal */}
      {showAddressModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Update Address</h3>
            <form onSubmit={handleAddressChange}>
              <input
                type="text"
                value={newAddress}
                onChange={(e) => setNewAddress(e.target.value)}
                placeholder="Enter new address"
                required
              />
              <button type="submit" className="close-modal-btn">
                Update Address
              </button>
            </form>
            <button
              className="close-modal-btn"
              onClick={() => setShowAddressModal(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}

      {/* Weather Filters Popup Modal */}
      {showFiltersPopup && (
        <div className="modal-overlay">
          <div className="modal">
            <h2 className="filters-title">Weather Filters</h2>
            <div className="checkbox-group">
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="temperature"
                  checked={weatherFilters.temperature}
                  onChange={handleWeatherFilterChange}
                />
                <label>Temperature</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="precipitation"
                  checked={weatherFilters.precipitation}
                  onChange={handleWeatherFilterChange}
                />
                <label>Precipitation</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="windSpeed"
                  checked={weatherFilters.windSpeed}
                  onChange={handleWeatherFilterChange}
                />
                <label>Wind Speed</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="humidity"
                  checked={weatherFilters.humidity}
                  onChange={handleWeatherFilterChange}
                />
                <label>Humidity</label>
              </div>
            </div>
            <button
              className="close-filters-btn"
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
