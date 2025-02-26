import React, {useState} from 'react'
import { Link } from 'react-router-dom';
import "../css/components/NavBar.css"
import { MdHome} from "react-icons/md";

function NavBar() {
    const [loggedIn, setLoggedIn] = useState(false)
    return (
        <ul className="navbar">
            <h1>ANDA</h1>
            <li><Link to="/home"><MdHome className="react-icon"/></Link></li>
            <div className="navbar-right">
                {!loggedIn ? <li className="nav-item"><Link to="/login">Login</Link></li> : ''}
                { !loggedIn ? <li className="nav-item"><Link to="/register">Register</Link></li> : ''}
                {loggedIn ? <li className="nav-item"><Link to="/settings">Settings</Link></li> : ''}
            </div>
        </ul>
    );
}

export default NavBar