import { createContext, useState, useEffect } from "react";

export const UserContext = createContext(null);

const UserProvider = ({ children }) => {
  const storedUser = localStorage.getItem("user");
  const storedLoggedIn = localStorage.getItem("loggedIn");

  // If agency_id is null then this is a normal user otherwise it is an admin
  const [user, setUser] = useState(storedUser ? JSON.parse(storedUser) : {
    username: "",
    first_name: "",
    last_name: "",
    password: "",
    email: "",
    address: "",
    phone_number: 0,
    share_location: 0,
    agency_id: null
  });

  const [loggedIn, setLoggedIn] = useState(storedLoggedIn === "true");

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(user));
  }, [user]);

  useEffect(() => {
    localStorage.setItem("loggedIn", loggedIn);
  }, [loggedIn]);

  return (
    <UserContext.Provider value={{ user, setUser, loggedIn, setLoggedIn }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
