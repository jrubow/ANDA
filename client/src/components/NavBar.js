import React, {useContext} from 'react'
import { Link, useLocation } from 'react-router-dom';
import "../css/components/NavBar.css"
import { MdHome} from "react-icons/md";
import { UserContext } from "./UserProvider";

function NavBar() {
    const {user, setUser, loggedIn, setLoggedIn} = useContext(UserContext)
    const location = useLocation();

    return (
        <>{ (location.pathname !== "/home") ? 
        <ul className="navbar">
            <h1>ANDA</h1>
            <li><Link to="/home"><MdHome className="react-icon"/></Link></li> 
            <div className="navbar-right">
                {!loggedIn ? <li className="nav-item"><Link to="/login">Login</Link></li> : ''}
                { !loggedIn ? <li className="nav-item"><Link to="/register">Register</Link></li> : ''}
                {loggedIn ? <li className="nav-item"><Link to="/settings">Settings</Link></li> : ''}
            </div>
        </ul>
        :
        ""
        }</>
    );
}

export default NavBar