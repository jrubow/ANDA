import React, { useState, useEffect, useContext } from 'react';
import { UserContext } from "../../components/UserProvider";
import '../../css/pages/devices/devicespage.css';
import axios from "axios";

const DevicesPage = () => {
  const [devices, setDevices] = useState([]);
  const { user } = useContext(UserContext);

  useEffect(() => {
    if (!user || !user.agency_id) return;
    console.log(user.agency_id)

    const fetchDevices = async () => {
      try {

        const response = await axios.post("/api/devices/sentinel/getbyagencyid", {
            agency_id: user.agency_id
            },
            {
              headers: {
                "X-API-KEY": "user",
                "Content-Type": "application/json"
              }
            }
          )
        console.log(response.data);
        setDevices(response.data);
      } catch (error) {
        console.error("Request Failed:", error.response ? error.response.data : error.message);
      }
    };

    fetchDevices();
  }, [user]);

  return (
    <div className="devices-page">
      <h1 className="page-title">Devices</h1>
      <div className="devices-list">
        {devices && devices.length > 0 ? devices.map((device) => (
          <div key={device.deviceId} className="device-card">
            <h2>Device ID: {device.deviceId}</h2>
            <p><strong>Agency ID:</strong> {device.agencyId}</p>
            <p><strong>Latitude:</strong> {device.latitude}</p>
            <p><strong>Longitude:</strong> {device.longitude}</p>
            <p><strong>Battery Life:</strong> {device.batteryLife}%</p>
            <p>
              <strong>Last Online:</strong>{' '}
              {new Date(device.lastOnline).toLocaleString()}
            </p>
            <p>
              <strong>Deployed:</strong> {device.deployed ? "Yes" : "No"}
            </p>
            <p>
              <strong>Deployed Date:</strong>{' '}
              {new Date(device.deployedDate).toLocaleString()}
            </p>
            <p>
              <strong>Connected Devices:</strong> {device.numConnectedDevices}
            </p>
            <p><strong>Password:</strong> {device.password}</p>
          </div>
        )) : <p>No devices found.</p>}
      </div>
    </div>
  );
};

export default DevicesPage;
