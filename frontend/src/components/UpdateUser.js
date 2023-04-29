import React, { useEffect, useState } from "react";
import "../css/Profile.css";
import HelperService from "../services/HelperService";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function UpdateUser() {
  let navigate = useNavigate();
  //Everytime profile called making api call to fetch user profile
  useEffect(() => {
    const fetchData = async (url, config) => {
      try {
        console.log(url, config);
        const result = await axios.get(url, config);
        console.log(result);

        //set variables
        setUser({
          ...user,
          username: result.data.username,
          name: result.data.name,
          email: result.data.email,
        });
      } catch (error) {
        console.log(error);
      }
    };
    const user = HelperService.getCurrentUserData();
    let id = user ? user.username : "";
    let token = user ? user.token : "";
    const url = `http://localhost:8080/user/${id}`;
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    fetchData(url, config);
  }, []);

  const [user, setUser] = useState({
    username: "",
    name: "",
    email: "",
  });

  const [formErrors, setFormErrors] = useState({
    username: "",
    name: "",
    email: "",
  });

  let [userRegistrationError, setUserRegistrationError] = useState();

  const validateForm = () => {
    let errors = {};
    const nameRegex = /^[a-zA-Z\s]+$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const usernameregex = /^[a-zA-Z0-9_-]{4,20}$/;

    // Validate username
    if (!user.username.trim()) {
      errors.username = "Username is required";
    } else if (!usernameregex.test(user.username.trim())) {
      errors.username = "Invalid username";
    }

    // Validate name
    if (!user.name.trim()) {
      errors.name = "Name is required";
    } else if (!nameRegex.test(user.name.trim())) {
      errors.name = "Invalid name";
    }

    // Validate email
    if (!user.email.trim()) {
      errors.email = "Email is required";
    } else if (!emailRegex.test(user.email.trim())) {
      errors.email = "Invalid email";
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
      const url = `http://localhost:8080/user/updateUser/${id}`;
      const config = {
        headers: { Authorization: `Bearer ${token}` },
      };
      const body = {
        username: user.username,
        email: user.email,
        name: user.name,
      };
      updateUserProfile(url, body, config);
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const updateUserProfile = async (url, body, config) => {
    try {
      console.log("updateUser " + url + " " + config);
      const response = await axios.put(url, body, config);
      if (response.data.message) {
        console.log(response.data.message);
        setUserRegistrationError(response.data.message);
      } else {
        console.log(response);
        const currentUserData = JSON.parse(localStorage.getItem("userData"));
        const updatedUserData = {
          ...currentUserData,
          username: response.data.username,
          token: response.data.token,
          role: response.data.role,
        };
        localStorage.setItem("userData", JSON.stringify(updatedUserData));
        navigate("/user-update-success");
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="profile-container">
      <h2>Profile Information</h2>
      <form onSubmit={handleSaveClick}>
        <div className="input-container">
          <label htmlFor="username">Username: </label>
          {isEditing ? (
            <input
              className="input"
              type="text"
              id="username"
              name="username"
              value={user.username}
              onChange={handleChange}
            />
          ) : (
            <span className="span">{user.username}</span>
          )}
        </div>
        <p>
          You can use 4 or more characters with a mix of letters, numbers,
          underscores and hyphens{" "}
        </p>
        <div className="error">
          {formErrors.username && <span>{formErrors.username}</span>}
          <br />
          {userRegistrationError && <span>{userRegistrationError}</span>}
        </div>

        <div className="input-container">
          <label htmlFor="name">Name: </label>
          {isEditing ? (
            <input
              className="input"
              type="text"
              id="name"
              name="name"
              value={user.name}
              onChange={handleChange}
            />
          ) : (
            <span className="span">{user.name}</span>
          )}
        </div>
        <div className="error">
          {formErrors.name && <span>{formErrors.name}</span>}
        </div>

        <div className="input-container">
          <label htmlFor="email">Email: </label>
          {isEditing ? (
            <input
              className="input"
              type="text"
              id="email"
              name="email"
              value={user.email}
              onChange={handleChange}
            />
          ) : (
            <span className="span">{user.email}</span>
          )}
        </div>
        <div className="error">
          {formErrors.email && <span>{formErrors.email}</span>}
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
