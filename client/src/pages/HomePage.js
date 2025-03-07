import React, { useState, useContext, useEffect, useRef } from "react";
import "../css/pages/homepage.css";
import { Link, useNavigate } from "react-router-dom";
import { UserContext } from "../components/UserProvider";
import { LoadScript, GoogleMap, Marker, InfoWindow } from "@react-google-maps/api";
import axios from "axios"


function HomePage() {
  // User Context
  const { user, setUser, loggedIn, setLoggedIn, isGuest, setIsGuest } = useContext(UserContext);


  // State for popup, weather filters, and heatmap data
  const [showFiltersPopup, setShowFiltersPopup] = useState(false);
  const [weatherFilters, setWeatherFilters] = useState({
    temperature: user.temperature,
    humidity: user.humidity,
    rain: user.rain,
    snow: user.snow,
  });
  const [heatmapData, setHeatmapData] = useState({
    temperature: [],
    humidity: [],
    precipitation: [],
    snow: [],
  });

  // New state to control the visibility of ESP32 device markers
  const [showESP32Devices, setShowESP32Devices] = useState(false);

  // New state to control whether the user's location should be displayed
  const [showUserLocation, setShowUserLocation] = useState(user.share_location);

  // State to track if the Google Maps API has loaded and the map instance
  const [mapApiLoaded, setMapApiLoaded] = useState(false);
  const [map, setMap] = useState(null);

  // State for user's location (set via geolocation)
  const [userLocation, setUserLocation] = useState(null);

  // Other state and context
  const navigate = useNavigate();

  // Map configuration
  const center = { lat: 40.4246, lng: -86.9081 };
  const zoom = 12;
  const mapContainerStyle = {
    marginLeft: "280px",
    width: "calc(100% - 280px)",
    height: "100vh",
  };

  // Function to update user's location using geolocation API
  const updateUserLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
          (position) => {
            setUserLocation({
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            });
          },
          (error) => console.error("Error getting user's location:", error)
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }
  };

  // Handle the toggle button click for showing/hiding user's location
  const handleToggleLocation = () => {
    if (!showUserLocation) {
      // Toggle ON: get the current location
      updateUserLocation();
      setShowUserLocation(true);
    } else {
      // Toggle OFF: clear the location
      setUserLocation(null);
      setShowUserLocation(false);
    }
  };

  // ESP32 Marker Component with InfoWindow
  const ESP32Marker = ({ lat, lng, device, isSentinel }) => {
    const [infoOpen, setInfoOpen] = useState(false);
    return (
        <>
          <Marker
              position={{ lat, lng }}
              icon={{
                url: isSentinel
                    ? "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
                    : "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
                scaledSize: new window.google.maps.Size(40, 40),
              }}
              onClick={() => setInfoOpen(true)}
          />
          {infoOpen && (
              <InfoWindow position={{ lat, lng }} onCloseClick={() => setInfoOpen(false)}>
                <div>
                  <h3>Device: {device}</h3>
                  <p>Type: {isSentinel ? "Sentinel" : "Regular"}</p>
                </div>
              </InfoWindow>
          )}
        </>
    );
  };

  // Refs for manual heatmap layers for each weather type
  const heatmapLayersRef = useRef({
    temperature: null,
    humidity: null,
    precipitation: null,
    snow: null,
  });

  // Define custom gradients for each weather type
  const temperatureGradient = [
    "rgba(0, 0, 255, 0)",
    "rgba(0, 0, 255, 0.5)",
    "rgba(255, 0, 0, 1)",
  ];
  const humidityGradient = [
    "rgba(0, 255, 255, 0)",
    "rgba(0, 255, 255, 0.5)",
    "rgba(0, 128, 128, 1)",
  ];
  const precipitationGradient = [
    "rgba(173, 216, 230, 0)",
    "rgba(173, 216, 230, 0.5)",
    "rgba(25, 25, 112, 1)",
  ];
  const snowGradient = [
    "rgba(255, 165, 0, 0)",
    "rgba(255, 165, 0, 0.5)",
    "rgba(255, 69, 0, 1)",
  ];

  // Map weather type to gradient array for legend display
  const gradientMap = {
    temperature: temperatureGradient,
    humidity: humidityGradient,
    rain: precipitationGradient,
    snow: snowGradient,
  };

  // Manage heatmap layers manually based on weatherFilters, heatmapData, and map
  useEffect(() => {
    if (!map) return;
    const types = ["temperature", "humidity", "precipitation", "snow"];
    types.forEach((type) => {
      if (weatherFilters[type]) {
        // If the filter is enabled, create or update the layer.
        if (!heatmapLayersRef.current[type]) {
          let gradient;
          if (type === "temperature") gradient = temperatureGradient;
          else if (type === "humidity") gradient = humidityGradient;
          else if (type === "precipitation") gradient = precipitationGradient;
          else if (type === "snow") gradient = snowGradient;

          const newLayer = new window.google.maps.visualization.HeatmapLayer({
            data: heatmapData[type],
            map: map,
            options: {
              gradient: gradient,
              radius: 30,
              opacity: 0.9,
            },
          });
          heatmapLayersRef.current[type] = newLayer;
        } else {
          // Update existing layer's data if needed.
          heatmapLayersRef.current[type].setData(heatmapData[type]);
        }
      } else {
        // If the filter is off, remove the layer if it exists.
        if (heatmapLayersRef.current[type]) {
          heatmapLayersRef.current[type].setMap(null);
          heatmapLayersRef.current[type] = null;
        }
      }
    });
  }, [weatherFilters, heatmapData, map]);

  // Get user's current location if allowed (this effect can be kept to run when user.shareLocation changes)
  useEffect(() => {
    if (user.share_location && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
          (position) => {
            setUserLocation({
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            });
          },
          (error) => console.error("Error getting user's location:", error)
      );
    }
  }, [user.share_location]);

  // Fetch ESP32 device locations
  const [devices, setDevices] = useState([]);
  useEffect(() => {
    fetch("/ESP32Locations.txt")
        .then((res) => res.text())
        .then((text) => {
          const lines = text.split("\n").filter((line) => line.trim() !== "");
          const parsedDevices = lines !== undefined ? lines.map((line) => {
            const [lat, lng, device, isSentinelStr] = line.split(",").map((str) => str.trim());
            return {
              lat: parseFloat(lat),
              lng: parseFloat(lng),
              device,
              isSentinel: isSentinelStr.toLowerCase() === "true",
            };
          }) : [];
          setDevices(parsedDevices);
        })
        .catch((err) => console.error("Error loading ESP32 locations:", err));
  }, []);

  // Function to load weather data from text files
  const loadWeatherData = (file, key) => {
    fetch(file)
        .then((res) => {
          if (!res.ok) throw new Error(`Failed to fetch ${key} data`);
          return res.text();
        })
        .then((text) => {
          const lines = text.split("\n").filter((line) => line.trim() !== "");
          const data = lines != undefined ? lines
              .map((line) => {
                const [lat, lng, value] = line.split(",");
                const latNum = parseFloat(lat);
                const lngNum = parseFloat(lng);
                const valueNum = parseFloat(value);
                if (isNaN(latNum) || isNaN(lngNum) || isNaN(valueNum)) {
                  console.error("Invalid data:", line);
                  return null;
                }
                return {
                  location: new window.google.maps.LatLng(latNum, lngNum),
                  weight: valueNum,
                };
              })
              .filter((item) => item !== null) : [];
          setHeatmapData((prevData) => ({
            ...prevData,
            [key]: data,
          }));
        })
        .catch((err) => console.error(`Error fetching ${key} data`, err));
  };

  // Load weather data when the Google Maps API is ready
  useEffect(() => {
    if (mapApiLoaded) {
      loadWeatherData("/temperatureData.txt", "temperature");
      loadWeatherData("/humidityData.txt", "humidity");
      loadWeatherData("/precipitationData.txt", "precipitation");
      loadWeatherData("/snowData.txt", "snow");
    }
  }, [mapApiLoaded]);

  useEffect(() => {
    if (user.share_location && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setUserLocation({
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          });
        },
        (error) => console.error("Error getting user's location:", error)
      );
    }
  }, [user.share_location]);

  // Google Map onLoad and onUnmount handlers
  const onLoad = React.useCallback((mapInstance) => {
    setMap(mapInstance);
  }, []);

  const onUnmount = React.useCallback(() => {
    setMap(null);
  }, []);

  // Toggle the filters popup
  const toggleFiltersPopup = () => setShowFiltersPopup(!showFiltersPopup);

  // Logout function
  const logout = (e) => {
    e.preventDefault();
    localStorage.removeItem("user")
    setLoggedIn(false)
    setIsGuest(false)
    navigate("/login")
  };

  // Handle weather filter changes via checkboxes
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
  }

  // Compute legend data for each active weather filter
  const getMinMax = (data) => {
    if (!data || data.length === 0) return { min: 0, max: 0 };
    const values = data !== undefined ? data.map((point) => point.weight) : [];
    return {
      min: Math.min(...values),
      max: Math.max(...values),
    } ;
  };

  const legendData = Object.keys(weatherFilters)
      .filter((key) => weatherFilters[key])
      .map((key) => ({
        type: key,
        ...getMinMax(heatmapData[key]),
      }));

  console.log(legendData)

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
              {!isGuest ? (
                  <li>
                    <Link to="/report">Make a Report</Link>
                  </li>
              ) : (
                  ""
              )}
              <li>
                <Link to="/navigate">Navigate</Link>
              </li>
              <li>
                <button className="sidebar-btn" onClick={toggleFiltersPopup}>
                  Map Layers
                </button>
              </li>
              {user.agency_id != null ? (
                  <>
                    <li>
                      <Link to="/devices/register">Register Device</Link>
                    </li>
                    <li>
                      <Link to="/devices">View Devices</Link>
                    </li>
                  </>
              ) : (
                  ""
              )}
              {/* New toggle for user location */}
              {isGuest ? <li>
                <button className="sidebar-btn" onClick={handleToggleLocation}>
                  {showUserLocation ? "Hide Location" : "Show Location"}
                </button>
              </li> : ""}
            </ul>
          </nav>
          <div className="bottom-menu">
            <ul>
              {!isGuest ? (
                  <>
                    <Link to="/settings">
                      <button className="button">Account</button>
                    </Link>
                    <button className="button" onClick={logout}>
                      Log Out
                    </button>
                  </>
              ) : (
                  <Link to="/login">
                    <button className="button">Login</button>
                  </Link>
              )}
            </ul>
          </div>
        </div>

        {/* Map Section */}
        <div className="map-container" style={mapContainerStyle}>
          <LoadScript
              googleMapsApiKey={process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY}
              libraries={["visualization"]}
              onLoad={() => setMapApiLoaded(true)}
          >
            <GoogleMap
                center={center}
                zoom={zoom}
                mapContainerStyle={{ height: "100%", width: "100%" }}
                onLoad={onLoad}
                onUnmount={onUnmount}
                options={{
                  mapTypeControl: true,
                  streetViewControl: true,
                  fullscreenControl: true,
                  zoomControl: true,
                }}
            >
              {/* User's location marker (conditionally render based on toggle state) */}
              {showUserLocation && mapApiLoaded && userLocation && (
                  <Marker
                      position={userLocation}
                      icon={{
                        path: window.google.maps.SymbolPath.CIRCLE,
                        fillColor: "#4285F4",
                        fillOpacity: 1,
                        strokeColor: "white",
                        strokeWeight: 2,
                        scale: 8,
                      }}
                  />
              )}
              {/* Render ESP32 device markers when toggled on */}
              {showESP32Devices && devices !== undefined &&
                  devices.map((device) => (
                      <ESP32Marker
                          key={device.device}
                          lat={device.lat}
                          lng={device.lng}
                          device={device.device}
                          isSentinel={device.isSentinel}
                      />
                  ))}
              {/* Note: Weather heatmap layers are managed via useEffect */}
            </GoogleMap>
          </LoadScript>
        </div>

        {/* Dynamic Legend for Active Weather Filters */}
        {legendData.length > 0 && (
            <div className="legend">
              {legendData !== undefined ? legendData.map(({ type, min, max }) => (
                  <div key={type} className="legend-item">
                    <div
                        className="gradient-box"
                        style={{
                          background: `linear-gradient(to right, ${gradientMap[type].join(", ")})`,
                        }}
                    ></div>
                    <div className="legend-labels">
                <span>
                  {type.charAt(0).toUpperCase() + type.slice(1)}: {min.toFixed(1)} - {max.toFixed(1)}
                </span>
                    </div>
                  </div>
              )) : ""}
            </div>
        )}

      {/* Map Layers Popup */}
      {showFiltersPopup && (
        <>
          <div className="popup-overlay" onClick={() => setShowFiltersPopup(false)}></div>
          <div className="filters-popup">
            <div className="filters-popup-content">
              <h2 className="filters-title">Map Layers</h2>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="temperature"
                  checked={user.temperature}
                  onChange={handleWeatherFilterChange}
                />
                <label>Temperature</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="rain"
                  checked={user.rain}
                  onChange={handleWeatherFilterChange}
                />
                <label>Precipitation</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="snow"
                  checked={user.snow}
                  onChange={handleWeatherFilterChange}
                />
                <label>Snow</label>
              </div>
              <div className="filter-row">
                <input
                  type="checkbox"
                  name="humidity"
                  checked={user.humidity}
                  onChange={handleWeatherFilterChange}
                />
                <label>Humidity</label>
              </div>
              {/* <div className="filter-row">
                <input
                  type="checkbox"
                  name="esp32"
                  checked={showESP32Devices}
                  onChange={(e) => setShowESP32Devices(e.target.checked)}
                />
                <label>ESP32 Devices</label>
              </div> */}
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

export default HomePage;
