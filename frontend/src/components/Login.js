import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/Login.css";
import axios from "axios";

const Login = () => {
  let navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const userCredentials = {
    username: username,
    password: password,
  };

  const userData = {
    token: "",
    username: "",
    role: "",
    status: "",
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const result = await axios.post(
        "http://localhost:8080/auth",
        userCredentials
      );
      console.log(result);
      userData.token = result.data.token;
      userData.username = result.data.username;
      userData.role = result.data.role;
      userData.status = result.status;
      console.log(userData);

      //Store userData in local storage
      localStorage.setItem("userData", JSON.stringify(userData));

      if (userData.status === 200) {
        navigate("/");
        window.location.reload();
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setErrorMsg("Invalid login details");
      } else {
        setErrorMsg("Service is not available");
      }
    }
  };

  return (
    <div className="login-form">
      <h2>Login</h2>
      <br />
      <div className="error">{errorMsg}</div>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            className="input"
            type="text"
            id="username"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            className="input"
            type="password"
            id="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <input type="submit" value="Login" onClick={handleSubmit} />
      </form>
      <div>
        <span>Don't have an account? </span>
        <Link to="/register">Register here</Link>
      </div>
    </div>
  );
};

export default Login;
