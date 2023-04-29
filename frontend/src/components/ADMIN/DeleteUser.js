import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import HelperService from "../../services/HelperService";

export default function DeleteUser() {
  const { username } = useParams();
  const navigate = useNavigate();

  const handleDelete = async () => {
    try {
      const url = `http://localhost:8080/user/${username}`;
      const currentUser = HelperService.getCurrentUserData();
      let token = currentUser ? currentUser.token : "";
      const config = {
        headers: { Authorization: `Bearer ${token}` },
      };
      const response = await axios.delete(url, config);
      console.log(response);
      navigate("/displayAllUsers");
    } catch (error) {
      console.log(error);
    }
  };

  const handleConfirm = () => {
    const confirmed = window.confirm(
      "Are you sure you want to cancel this booking?"
    );
    if (confirmed) {
      handleDelete();
    }
  };

  return (
    <div>
      <h1>Delete User {username} </h1>
      <button onClick={handleConfirm}>Delete {username}</button>
    </div>
  );
}
