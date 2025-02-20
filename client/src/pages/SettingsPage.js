import { useState } from "react";
import "../css/pages/settingspage.css"

function SettingsPage() {
  const [isAdmin, setIsAdmin] = useState(false); // Toggle between User/Admin
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    address: "",
    phone_number: "",
    share_location: false,
    work_id: isAdmin ? "" : null, // Admin-only field
    verified: isAdmin ? false : null, // Admin-only field
  });

  // Handle Input Changes
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  return (
    <div className="settings-container">
      <h2>Settings</h2>
      <button onClick={() => setIsAdmin(!isAdmin)}>
        Switch to {isAdmin ? "User" : "Admin"} Mode
      </button>

      <form>
        <label>
          Username:
          <input type="text" name="username" value={formData.username} onChange={handleChange} />
        </label>

        <label>
          Password:
          <input type="password" name="password" value={formData.password} onChange={handleChange} />
        </label>

        <label>
          Email:
          <input type="email" name="email" value={formData.email} onChange={handleChange} />
        </label>

        <label>
          Address:
          <input type="text" name="address" value={formData.address} onChange={handleChange} />
        </label>

        <label>
          Phone Number:
          <input type="text" name="phone_number" value={formData.phone_number} onChange={handleChange} />
        </label>

        <label>
          Share Location:
          <input type="checkbox" name="share_location" checked={formData.share_location} onChange={handleChange} />
        </label>

        {/* Admin-only fields */}
        {isAdmin && (
          <>
            <label>
              Work ID:
              <input type="number" name="work_id" value={formData.work_id} onChange={handleChange} />
            </label>

            <label>
              Verified:
              <input type="checkbox" name="verified" checked={formData.verified} onChange={handleChange} />
            </label>
          </>
        )}

        <button type="submit">Save Settings</button>
      </form>
    </div>
  );
}

export default SettingsPage;
