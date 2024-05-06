// Navbar.jsx
import React from "react";
import "./Navbar.css";
import { Link } from "react-scroll";

export default function Navbar() {
    return(
        <div className="navbar">
            <nav>
                <ul>
                    <li>
                        <Link to='#about' smooth={true} spy={true} duration={500}>ABOUT</Link>
                    </li>
                    <li>
                        <Link to='#solver' smooth={true} spy={true} duration={500}>SOLVER</Link>
                    </li>
                </ul>
            </nav>
        </div>
    )
}
