import React, { useState, useEffect } from "react";
import axios from "axios";
import "../../css/DisplayFlights.css";
import HelperService from "../../services/HelperService";
import { Link } from "react-router-dom";

export default function ViewUsers() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const currentUser = HelperService.getCurrentUserData();
    let token = currentUser ? currentUser.token : "";
    axios
      .get("http://localhost:8080/user/getAllusers", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        console.log(response);
        setUsers(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div>
      <h2>All Users</h2>
      <table className="table">
        <thead>
          <tr>
            <th>S.No</th>
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{user.username}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>
                <Link to={`/deleteUser/${user.username}`}>Delete</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
