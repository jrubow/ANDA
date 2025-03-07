import { useState, useContext, useRef } from "react";
import { UserContext } from "../components/UserProvider";
import { Link, useNavigate } from "react-router-dom";  // Import this
import "../css/pages/settingspage.css";
import axios from "axios";

function SettingsPage() {
  const navigate = useNavigate();  // Initialize
  const { user, setUser, loggedIn, setLoggedIn } = useContext(UserContext);

  // Modal visibility state
  const [showPasswordModal, setShowPasswordModal] = useState(false);
  const [showEmailModal, setShowEmailModal] = useState(false);
  const [showPhoneModal, setShowPhoneModal] = useState(false);
  const [showAddressModal, setShowAddressModal] = useState(false);
  const [showFiltersPopup, setShowFiltersPopup] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false); // New deletion modal

  // Input states for user updates
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordConfirm, setNewPasswordConfirm] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [newPhone, setNewPhone] = useState();
  const [newAddress, setNewAddress] = useState("");

  // New state for deletion password
  const [deletePassword, setDeletePassword] = useState("");

  // Share location toggle state
  const [shareLocation, setShareLocation] = useState(user.share_location);

  function toggleShareLocation(e) {
    setShareLocation(!user.share_location);
    setUser({ ...user, share_location: !user.share_location });
  }

  // Weather filters state
  const [weatherFilters, setWeatherFilters] = useState({
    temperature: user.temperature,
    rain: user.rain,
    snow: user.snow,
    humidity: user.humidity,
  });

  // Ref for the hidden file input
  const fileInputRef = useRef(null);

  // Update the Email
  async function updateEmail(e) {
    e.preventDefault();
    try {
      const response = await axios.post(
          "/api/update",
          {
            username: user.username,
            [e.target.name]: newEmail,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      setUser({ ...user, [e.target.name]: newEmail });
      console.log("Data:", response.data);
      setShowEmailModal(false);
    } catch (error) {
      console.error("Error Posting Data :", error.message);
      alert(error.message);
    }
  }

  // Update the Password
  async function updatePassword(e) {
    e.preventDefault();
    try {
      const response = await axios.post(
          "/api/update",
          {
            username: user.username,
            [e.target.name]: newPassword,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      setUser({ ...user, [e.target.name]: newPassword });
      console.log("Data:", response.data);
      setShowPasswordModal(false);
    } catch (error) {
      console.error("Error Posting Data :", error.message);
      alert(error.message);
    }
  }

  // Update the Phone Number
  async function updatePhoneNumber(e) {
    e.preventDefault();
    try {
      console.log({
        username: user.username,
        [e.target.name]: newPhone,
      });
      const response = await axios.post(
          "/api/update",
          {
            username: user.username,
            [e.target.name]: newPhone,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      setUser({ ...user, [e.target.name]: newPhone });
      console.log("Data:", response.data);
      setShowPhoneModal(false);
    } catch (error) {
      console.error("Error Posting Data :", error.message);
      alert(error.message);
    }
  }

  // Update the Address
  async function updateAddress(e) {
    e.preventDefault();
    try {
      const response = await axios.post(
          "/api/update",
          {
            username: user.username,
            [e.target.name]: newAddress,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      setUser({ ...user, [e.target.name]: newAddress });
      console.log("Data:", response.data);
      setShowAddressModal(false);
    } catch (error) {
      console.error("Error Posting Data :", error.message);
      alert(error.message);
    }
  }

  // Delete Account function that uses the entered deletePassword
  async function deleteAccount(e) {
    e.preventDefault();
    try {
      const response = await axios.post(
          "/api/delete",
          {
            username: user.username,
            password: deletePassword,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      console.log("Delete Account Response:", response.data);
      localStorage.removeItem("user");
      setLoggedIn(false);
      navigate("/login");
      // Optionally, log the user out or redirect them after deletion.
    } catch (error) {
      console.error("Error deleting account:", error.message);
      alert("Error deleting account: " + error.message);
    }
  }

  // Handler for profile photo update
  const handleProfilePhotoClick = () => {
    fileInputRef.current.click();
  };

  const handleProfilePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      console.log("Selected profile photo:", file);
      // Optionally, create a preview URL and update user state
      // const imageUrl = URL.createObjectURL(file);
      // setUser({ ...user, profilePhoto: imageUrl });
    }
  };

  // Handlers for updating user info
  const handlePasswordChange = (e) => {
    e.preventDefault();
    if (newPassword !== newPasswordConfirm) {
      alert("Passwords do not match!");
      return;
    }
    if (newPassword) {
      console.log("Password updated to:", newPassword);
      setNewPassword("");
      setNewPasswordConfirm("");
      setShowPasswordModal(false);
    }
  };

  const handleEmailChange = (e) => {
    e.preventDefault();
    if (newEmail) {
      console.log("Email updated to:", newEmail);
      setNewEmail("");
      setShowEmailModal(false);
    }
  };

  const handlePhoneChange = (e) => {
    e.preventDefault();
    if (newPhone) {
      console.log("Phone number updated to:", newPhone);
      setNewPhone("");
      setShowPhoneModal(false);
    }
  };

  const handleAddressChange = (e) => {
    e.preventDefault();
    if (newAddress) {
      console.log("Address updated to:", newAddress);
      setNewAddress("");
      setShowAddressModal(false);
    }
  };

  // Handler for weather filters
  async function handleWeatherFilterChange(e) {
    const { name, checked } = e.target;
    e.preventDefault();
    try {
      const response = await axios.post(
          "/api/update",
          {
            username: user.username,
            [name]: checked,
          },
          {
            headers: {
              "X-API-KEY": "user",
              "Content-Type": "application/json",
            },
          }
      );
      setUser({...user, [name] : checked})
      console.log("Data:", response.data);
    } catch (error) {
      console.error("Error Posting Data :", error.message);
      alert(error.message);
    }

    setWeatherFilters((prevFilters) => ({
      ...prevFilters,
      [name]: checked,
    }));
  };

  return (
      <div className="settings-page-container">
        <div className="profile-section">
          {/* Left side: Profile Photo and options */}
          <div className="profile-photo">
            <img src="lego_photo.png" alt="Profile" className="profile-img" />
            <button className="update-btn" onClick={handleProfilePhotoClick}>
              Add Profile Photo
            </button>
            {/* Hidden file input for selecting profile photo */}
            <input
                type="file"
                accept="image/*"
                ref={fileInputRef}
                onChange={handleProfilePhotoChange}
                style={{ display: "none" }}
            />
            <button
                className="update-btn"
                onClick={() => setShowFiltersPopup(true)}
            >
              Filter Preferences
            </button>
            <button className="update-btn" onClick={toggleShareLocation}>
              Share Location: {shareLocation ? "Yes" : "No"}
            </button>
            {/* Updated Delete Account button now opens the deletion modal */}
            <button
                className="update-btn delete-btn"
                onClick={() => setShowDeleteModal(true)}
            >
              Delete Account
            </button>
          </div>

          {/* Right side: User Details */}
          <div className="user-info">
            <div className="info-row">
              <div className="label">Username</div>
              <div className="value">{user.username || "Not set"}</div>
            </div>

            <div className="info-row">
              <div className="label">Full Name</div>
              <div className="value">
                {user.first_name || "Not"} {user.last_name || "set"}
              </div>
            </div>

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

        {/* Deletion Modal */}
        {showDeleteModal && (
            <div className="modal-overlay">
              <div className="modal">
                <h3>Confirm Delete Account</h3>
                <p>Please enter your password to confirm account deletion:</p>
                <input
                    type="password"
                    value={deletePassword}
                    onChange={(e) => setDeletePassword(e.target.value)}
                    placeholder="Enter your password"
                    required
                />
                <button className="close-modal-btn" onClick={deleteAccount}>
                  Delete Account
                </button>
                <button
                    className="close-modal-btn"
                    onClick={() => {
                      setShowDeleteModal(false);
                      setDeletePassword("");
                    }}
                >
                  Cancel
                </button>
              </div>
            </div>
        )}

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
                  <input
                      type="password"
                      value={newPasswordConfirm}
                      onChange={(e) => setNewPasswordConfirm(e.target.value)}
                      placeholder="Confirm new password"
                      required
                  />
                  <button name="password" onClick={updatePassword} className="close-modal-btn">
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
                  <button name="email" onClick={updateEmail} className="close-modal-btn">
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
                      type="number"
                      value={newPhone}
                      onChange={(e) => setNewPhone(e.target.value)}
                      placeholder="Enter new phone number"
                      required
                  />
                  <button name="phone_number" onClick={updatePhoneNumber} className="close-modal-btn">
                    Update Phone Number
                  </button>
                </form>
                <button className="close-modal-btn" onClick={() => setShowPhoneModal(false)}>
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
                  <button name="address" onClick={updateAddress} className="close-modal-btn">
                    Update Address
                  </button>
                </form>
                <button className="close-modal-btn" onClick={() => setShowAddressModal(false)}>
                  Close
                </button>
              </div>
            </div>
        )}

        {/* Weather Filters Popup using homepage styling */}
        {showFiltersPopup && (
            <>
              <div className="popup-overlay" onClick={() => setShowFiltersPopup(false)}></div>
              <div className="filters-popup">
                <div className="filters-popup-content">
                  <h2 className="filters-title">Weather Filters</h2>
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
                        name="rain"
                        checked={weatherFilters.rain}
                        onChange={handleWeatherFilterChange}
                    />
                    <label>Precipitation</label>
                  </div>
                  <div className="filter-row">
                    <input
                        type="checkbox"
                        name="snow"
                        checked={weatherFilters.snow}
                        onChange={handleWeatherFilterChange}
                    />
                    <label>Snow</label>
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
                  <button className="close-btn" onClick={() => setShowFiltersPopup(false)}>
                    Close
                  </button>
                </div>
              </div>
            </>
        )}
      </div>
  );
}

export default SettingsPage;
