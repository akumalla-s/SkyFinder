import React from "react";
import "../css/Body.css";
import SearchBar from "./SearchBar";
import { useEffect } from "react";
import HelperService from "../services/HelperService";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ViewBookings from "./ADMIN/ViewBookings";

function Body() {
  const [currentUser, setCurrentUser] = useState(undefined);
  const [role, setRole] = useState("NONE");
  let navigate = useNavigate();

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

  //After 2 mins of inactivity of user call logout
  const [lastActivity, setLastActivity] = useState(Date.now());
  useEffect(() => {
    console.log("checking user activity");
    const interval = setInterval(() => {
      if (Date.now() - lastActivity > 2 * 60 * 1000) {
        navigate("/logout");
      }
    }, 10000); // Check every 10 seconds
    return () => clearInterval(interval);
  }, [lastActivity, navigate]);
  const updateLastActivity = () => {
    setLastActivity(Date.now());
  };

  return (
    <div onMouseMove={updateLastActivity}>
      <div>
        {currentUser && (
          <div>
            <p>Welcome {currentUser.username}!</p>
          </div>
        )}
      </div>
      {role !== "ADMIN" ? <SearchBar /> : null}
      {role === "ADMIN" ? <ViewBookings /> : null}
    </div>
  );
}

export default Body;
