import React, {useContext} from 'react';
import Form from "../../components/Form";
import { UserContext } from "../../components/UserProvider";

function RegisterPage() {
  const { user } = useContext(UserContext);
  // Define the fields needed for device registration.
  const fields = [
    { name: "device_id", placeholder: "Device ID", type: "number" },
    { name: "password", placeholder: "Device Password", type: "text" },
    // Add more fields here if necessary.
  ];

  const additionalFields = [
    { agency_id : user.agency_id}
  ]

  // Optional: Define additional links or terms if needed.
  const links = [
    { text: "Back to Home", href: "/home" }
  ];
  
  return (
    <div>
      <Form
        title="Register Device"
        fields={fields}
        additionalFields={additionalFields}
        postRoute="/api/devices/sentinel/claim"
        submitButtonText="Register"
        links={links}
        redirect="/devices"
      />
    </div>
  );
}

export default RegisterPage;
