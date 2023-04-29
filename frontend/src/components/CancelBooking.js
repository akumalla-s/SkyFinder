import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import HelperService from "../services/HelperService";
import axios from "axios";

export default function CancelBooking() {
  const { id } = useParams();
  const navigate = useNavigate();
  const userData = HelperService.getCurrentUserData();

  const handleCancel = async () => {
    try {
      const url = `http://localhost:8080/cancelFlightBooking/${id}`;
      const token = userData.token;
      const config = {
        headers: { Authorization: `Bearer ${token}` },
      };
      const response = await axios.delete(url, config);
      console.log(response);
      navigate("/displayflightbooking");
    } catch (error) {
      console.log(error);
    }
  };

  const handleConfirm = () => {
    const confirmed = window.confirm(
      "Are you sure you want to cancel this booking?"
    );
    if (confirmed) {
      handleCancel();
    }
  };

  return (
    <div>
      <h1>Cancel Booking </h1>
      <button onClick={handleConfirm}>Cancel Booking</button>
    </div>
  );
}
