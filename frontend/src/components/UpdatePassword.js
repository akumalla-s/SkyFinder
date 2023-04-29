import React, { useState } from "react";
import "../css/Profile.css";
import HelperService from "../services/HelperService";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function UpdatePassword() {
  let navigate = useNavigate();
  const [user, setUser] = useState({
    password: "",
    confirmPassword: "",
  });
  const [formErrors, setFormErrors] = useState({
    password: "",
    confirmPassword: "",
  });

  const validateForm = () => {
    let errors = {};

    // Validate password
    if (!user.password.trim()) {
      errors.password = "Password is required";
    } else if (user.password.length < 8) {
      errors.password = "Password must be at least 8 characters long";
    }

    // Validate confirm password
    if (!user.confirmPassword.trim()) {
      errors.confirmPassword = "Confirm password is required";
    } else if (user.confirmPassword !== user.password) {
      errors.confirmPassword = "Passwords do not match";
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const [isEditing, setIsEditing] = useState(false);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleCancelClick = () => {
    setIsEditing(false);
    setUser(user);
  };

  const handleSaveClick = (event) => {
    // save changes to user profile
    event.preventDefault();
    if (validateForm()) {
      setIsEditing(false);
      console.log(user);
      const currentUser = HelperService.getCurrentUserData();
      let id = currentUser ? currentUser.username : "";
      let token = currentUser ? currentUser.token : "";
      const url = `http://localhost:8080/user/updateUserPassword/${id}`;
      const config = {
        headers: { Authorization: `Bearer ${token}` },
      };
      const body = {
        password: user.password,
      };
      updateUserPassword(url, body, config);
    }
  };
  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const updateUserPassword = async (url, body, config) => {
    try {
      console.log("updatePassword " + url + " " + config);
      const response = await axios.put(url, body, config);
      console.log(response);
      navigate("/password-update-success");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="profile-container">
      <h2>Update Password</h2>
      <form onSubmit={handleSaveClick}>
        <div className="input-container">
          <label htmlFor="password">Password: </label>
          {isEditing ? (
            <input
              className="input"
              type="password"
              id="password"
              name="password"
              onChange={handleChange}
            />
          ) : (
            <span className="span">****</span>
          )}
          <div className="error">
            {formErrors.password && <span>{formErrors.password}</span>}
          </div>
        </div>
        <div className="input-container">
          <label htmlFor="confirmPassword">Confirm Password: </label>
          {isEditing ? (
            <input
              className="input"
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              onChange={handleChange}
            />
          ) : (
            <span className="span">****</span>
          )}
          <div className="error">
            {formErrors.confirmPassword && (
              <span>{formErrors.confirmPassword}</span>
            )}
          </div>
        </div>
        <div className="button-container">
          {isEditing ? (
            <div>
              <input type="submit" value="Save" onClick={handleSaveClick} />
              <input type="submit" value="Cancel" onClick={handleCancelClick} />
            </div>
          ) : (
            <input type="submit" value="Edit" onClick={handleEditClick} />
          )}
        </div>
      </form>
    </div>
  );
}
