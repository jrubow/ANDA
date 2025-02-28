import { createContext, useState } from "react";

export const UserContext = createContext(null);

const UserProvider = ({ children }) => {
  const [user, setUser] = useState({
    username: "",
    first_name: "",
    last_name: "",
    password: "",
    email: "",
    address: "",
    phone_number: 0,
    share_location: 0
  });

  const [loggedIn, setLoggedIn] = useState(false);

  return (
    <UserContext.Provider value={{ user, setUser, loggedIn, setLoggedIn }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
