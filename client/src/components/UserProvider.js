import { createContext, useState, useEffect } from "react";

export const UserContext = createContext(null);

const UserProvider = ({ children }) => {
  const storedUser = localStorage.getItem("user");
  const storedLoggedIn = localStorage.getItem("loggedIn");
  const storedIsGuest = localStorage.getItem("isGuest");

  const [user, setUser] = useState(storedUser ? JSON.parse(storedUser) : {
    username: "",
    first_name: "",
    last_name: "",
    password: "",
    email: "",
    address: "",
    phone_number: 0,
    share_location: 0,
    agency_id: null,
    snow: 0,
    rain : 0,
    ice : 0,
    flood : 0,
  });

  const [loggedIn, setLoggedIn] = useState(storedLoggedIn === "true");
  const [isGuest, setIsGuest] = useState(storedIsGuest === "true");

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(user));
  }, [user]);

  useEffect(() => {
    localStorage.setItem("loggedIn", loggedIn);
  }, [loggedIn]);

  useEffect(() => {
    localStorage.setItem("isGuest", isGuest);
  }, [isGuest]);

  return (
    <UserContext.Provider value={{ user, setUser, loggedIn, setLoggedIn, isGuest, setIsGuest }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
