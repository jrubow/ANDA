import React, { useState } from "react";
import "../css/pages/reportpage.css";
import { SlLocationPin } from "react-icons/sl"; // Import the icon

function ReportPage() {
  const [contactPreference, setContactPreference] = useState("");
  const [category, setCategory] = useState("");
  const [subCategory, setSubCategory] = useState("");
  const [location, setLocation] = useState("");
  const [description, setDescription] = useState("");
  const [selectedPhoto, setSelectedPhoto] = useState(null);

  const handleCategoryChange = (e) => {
    const selectedCategory = e.target.value;
    setCategory(selectedCategory);
    setSubCategory(""); // Reset subcategory on category change
  };

  const handleSubCategoryChange = (e) => {
    setSubCategory(e.target.value);
  };

  const handleFileChange = (e) => {
    setSelectedPhoto(e.target.files[0]);
  };

  return (
    <div className="report-container">

      {/* Main Content */}
      <div className="content">
        <h2>Make a Report</h2>

        {/* Contact Preference */} 
        <div className="dropdown-container">
          <label>Contact Preference:</label>
          <select
            value={contactPreference}
            onChange={(e) => setContactPreference(e.target.value)}
          >
            <option value="">Select</option>
            <option value="email">Email</option> {/* Will need to display these from users account */}
            <option value="phone">Phone</option>
          </select>
        </div>

        {/* Category and Subcategory Selection */}
        <div className="category-subcategory-container">
          <div className="dropdown-container">
            <label>Category:</label>
            <select value={category} onChange={handleCategoryChange}>
              <option value="">Select Category</option>
              <option value="weather">Weather</option>
              <option value="system">System</option>
              <option value="roadblocks">Road Blocks</option>
              <option value="falseinfo">False Information</option>
            </select>
          </div>

          {/* Subcategory Based on Category */}
          {category && (
            <div className="dropdown-container">
              <label>Subcategory:</label>
              <select value={subCategory} onChange={handleSubCategoryChange}>
                <option value="">Select Subcategory</option>
                {category === "weather" && (
                  <>
                    <option value="rain">Rain</option>
                    <option value="ice">Ice</option>
                    <option value="snow">Snow</option>
                  </>
                )}
                {category === "system" && (
                  <>
                    <option value="mapnotworking">Map Not Working</option>
                    <option value="slowserver">Slow Server</option>
                  </>
                )}
                {category === "roadblocks" && (
                  <>
                    <option value="crash">Crash</option>
                    <option value="closures">Closures</option>
                  </>
                )}
                {category === "falseinfo" && (
                  <>
                    <option value="weatherinput">Weather Input</option>
                    <option value="roadblocks">Road Blocks</option>
                    <option value="closures">Closures</option>
                  </>
                )}
              </select>
            </div>
          )}
        </div>
        
        {/* Location Input */}  
        <div className="input-container location-container">
            <label>Location:</label>
                <div className="location-input-container">
                <input
                    type="text"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                    placeholder="Enter location"
                />
                <button className="location-pin-btn" onClick={() => alert("Map pin button clicked!")}>
                <SlLocationPin size={20} />
                </button>
            </div>
        </div>


        {/* Description Input */}
        <div className="input-container">
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Enter description"
          />
        </div>

        {/* Add Photo */}
        <div className="photo-container">
          <label htmlFor="photo-upload" className="photo-upload-btn">
            Add Photos
          </label>
          <input
            id="photo-upload"
            type="file"
            accept="image/*"
            onChange={handleFileChange}
          />
        </div>

        {/* Submit Button */}
        <button className="submit-btn">Submit Report</button>
      </div>
    </div>
  );
}

export default ReportPage;
