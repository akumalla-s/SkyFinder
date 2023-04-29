import React, { useEffect, useState } from "react";
import HelperService from "../services/HelperService";
import axios from "axios";
import "../css/DisplayFlightBooking.css";
import { Link } from "react-router-dom";

export default function DisplayFlightBooking() {
  const [bookings, setBookings] = useState([]);
  

  useEffect(() => {
    const userData = HelperService.getCurrentUserData();
    if (!userData) return;
    const fetchData = async () => {
      try {
        const url = `http://localhost:8080/displayUserBookings/${userData.username}`;
        const token = userData.token;
        const config = { headers: { Authorization: `Bearer ${token}` } };
        const response = await axios.get(url, config);
        setBookings(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  if (bookings.length === 0) {
    return <div>No bookings found.</div>;
  }

  return (
    <div>
      <h2>Your Flight Bookings</h2>
      <table className="table">
        <thead>
          <tr>
            <th>Flight Number</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure Time</th>
            <th>Arrival Time</th>
            <th>Duration</th>
            <th>Price</th>
            <th>Airline</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {bookings.map((booking) => (
            <tr key={booking.id}>
              <td>{booking.flightNumber}</td>
              <td>{booking.origin}</td>
              <td>{booking.destination}</td>
              <td>{booking.departureTime}</td>
              <td>{booking.arrivalTime}</td>
              <td>{booking.duration}</td>
              <td>{booking.price}</td>
              <td>{booking.airline}</td>
              <td>{booking.status}</td>
              <td>
                <Link to={`/cancelflightbooking/${booking.id}`}>Cancel</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
