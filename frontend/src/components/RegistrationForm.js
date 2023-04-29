import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/RegistrationForm.css";

export default function RegistrationForm() {
  let navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [formErrors, setFormErrors] = useState({
    name: "",
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  let [userRegistrationError, setUserRegistrationError] = useState();

  const validateForm = () => {
    let errors = {};
    const nameRegex = /^[a-zA-Z\s]+$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const usernameregex = /^[a-zA-Z0-9_-]{4,20}$/;

    // Validate name
    if (!formData.name.trim()) {
      errors.name = "Name is required";
    } else if (!nameRegex.test(formData.name.trim())) {
      errors.name = "Invalid name";
    }

    // Validate username
    if (!formData.username.trim()) {
      errors.username = "Username is required";
    } else if (!usernameregex.test(formData.username.trim())) {
      errors.username = "Invalid username";
    }

    // Validate email
    if (!formData.email.trim()) {
      errors.email = "Email is required";
    } else if (!emailRegex.test(formData.email.trim())) {
      errors.email = "Invalid email";
    }

    // Validate password
    if (!formData.password.trim()) {
      errors.password = "Password is required";
    } else if (formData.password.length < 8) {
      errors.password = "Password must be at least 8 characters long";
    }

    // Validate confirm password
    if (!formData.confirmPassword.trim()) {
      errors.confirmPassword = "Confirm password is required";
    } else if (formData.confirmPassword !== formData.password) {
      errors.confirmPassword = "Passwords do not match";
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
    setFormErrors({ ...formErrors, [name]: "" });
  };

  const user = {
    name: formData.name,
    username: formData.username,
    email: formData.email,
    password: formData.password,
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (validateForm()) {
      console.log(user);
      var response = await axios.post(
        "http://localhost:8080/user/createUser",
        user
      );
      console.log(response);

      if (response.data.message) {
        console.log(response.data.message);
        setUserRegistrationError(response.data.message);
      } else {
        navigate("/registration-success");
      }
    }
  };

  return (
    <div className="registration-form">
      <h2 className="registration-title">Registration Form</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            className="input"
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          <div className="error">
            {formErrors.name && <span>{formErrors.name}</span>}
          </div>
        </div>
        <div>
          <label htmlFor="username">Username:</label>
          <p>
            You can use 4 or more characters with a mix of letters, numbers,
            underscores and hyphens{" "}
          </p>

          <input
            className="input"
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
          <div className="error">
            {formErrors.username && <span>{formErrors.username}</span>}
            <br />
            {userRegistrationError && <span>{userRegistrationError}</span>}
          </div>
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            className="input"
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          <div className="error">
            {formErrors.email && <span>{formErrors.email}</span>}
          </div>
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            className="input"
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          <div className="error">
            {formErrors.password && <span>{formErrors.password}</span>}
          </div>
        </div>
        <div>
          <label htmlFor="confirmPassword">Confirm Password:</label>
          <input
            className="input"
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
          <div className="error">
            {formErrors.confirmPassword && (
              <span>{formErrors.confirmPassword}</span>
            )}
          </div>
        </div>
        <input type="submit" value="Submit" onClick={handleSubmit} />
      </form>
    </div>
  );
}
