import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import "../css/Header.css";
import HelperService from "../services/HelperService";

export default function Header() {
  const [currentUser, setCurrentUser] = useState(undefined);
  const [role, setRole] = useState("NONE");

  useEffect(() => {
    const user = HelperService.getCurrentUserData();
    if (user) {
      setCurrentUser(user);
      if (user.role === "USER") {
        setRole("USER");
      } else if (user.role === "ADMIN") {
        setRole("ADMIN");
      } else {
        setRole("NONE");
      }
    } else {
      setRole("NONE");
    }
  }, []);
  return (
    <header className="header">
      <a href="http://localhost:3000/">SkyFinder</a>

      <nav className="navigation">
        <NavLink to="/">Home</NavLink>
        <NavLink to="/about">About</NavLink>

        {currentUser ? (
          <NavLink to="/logout">Logout</NavLink>
        ) : (
          <NavLink to="/login">Login</NavLink>
        )}

        {currentUser
          ? role !== "ADMIN" && <NavLink to="/profile">Profile</NavLink>
          : role !== "ADMIN" && <NavLink to="/register">Register</NavLink>}

        {role === "USER" ? (
          <NavLink to="/displayflightbooking">Bookings</NavLink>
        ) : null}

        {role === "ADMIN" ? (
          <NavLink to="/displayAllUsers">View Users</NavLink>
        ) : null}
      </nav>
    </header>
  );
}
