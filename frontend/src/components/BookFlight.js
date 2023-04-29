import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HelperService from "../services/HelperService";
import axios from "axios";

const BookFlight = () => {
  const navigate = useNavigate();
  const { flightnumber } = useParams();
  const userData = HelperService.getCurrentUserData();

  useEffect(() => {
    if (!userData) {
      navigate("/login");
    }
  }, [navigate, userData]);

  const handleSubmit = async () => {
    try {
      const url = "http://localhost:8080/bookFlight";
      const body = {
        username: userData.username,
        flightNumber: flightnumber,
      };
      let token = userData.token;
      const config = {
        headers: { Authorization: `Bearer ${token}` },
      };
      const response = await axios.post(url, body, config);
      navigate("/flightbookingsuccess");
      console.log(response);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      Do you want to continue booking... {flightnumber}
      <br />
      <br />
      <br />
      <div>
        <input type="submit" value="Book Flight" onClick={handleSubmit} />
      </div>
    </div>
  );
};

export default BookFlight;
