import React, { useEffect, useState } from "react";
import "../css/DisplayFlights.css";
import { Link } from "react-router-dom";

export default function DisplayFlights(props) {
  const { flights } = props;

  // State variables to keep track of sorting preference and sorted flights
  const [sortOption, setSortOption] = useState("");
  const [sortedFlights, setSortedFlights] = useState([]);

  //useEffect hook that resets the sort option whenever new flights data is fetched
  //This hook will run every time the flights state variable changes, which will happen whenever new flights data is fetched.
  useEffect(() => {
    setSortOption("");
  }, [flights]);

  const handleSortOptionChange = (e) => {
    const option = e.target.value;
    setSortOption(option);
    sortFlights(option);
  };

  const sortFlights = (option) => {
    const sorted = [...flights];
    switch (option) {
      case "price":
        sorted.sort((a, b) => a.price - b.price);
        break;
      case "duration":
        sorted.sort((a, b) => a.duration - b.duration);
        break;
      case "ratings":
        sorted.sort((a, b) => b.userRatings - a.userRatings);
        break;
      default:
        break;
    }
    setSortedFlights(sorted);
  };

  // If no sort preference is set, display the best and other flights as usual
  if (sortOption === "") {
    const bestFlights = flights.slice(0, 2);
    const otherFlights = flights.slice(2);

    return (
      <div>
        <div>
          <label htmlFor="sort">Sort By:</label>
          <select
            id="sort"
            name="sort"
            value={sortOption}
            onChange={handleSortOptionChange}
          >
            <option value="">Select an option</option>
            <option value="price">Price (Low to High)</option>
            <option value="duration">Duration (Low to High)</option>
            <option value="ratings">Ratings (High to Low)</option>
          </select>
        </div>
        <div>
          <h3 colSpan="7">Best departing flights</h3>
          <table className="table">
            <thead>
              <tr>
                <th>Flight Number</th>
                <th>Airline</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <th>Duration</th>
                <th>Price</th>
                <th>Ratings</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {bestFlights &&
                bestFlights.map((flight, index) => (
                  <tr key={flight.flightNumber}>
                    <td>{flight.flightNumber} </td>
                    <td>{flight.airline} </td>
                    <td>{flight.departureTime}</td>
                    <td>{flight.arrivalTime}</td>
                    <td>{flight.duration} hours</td>
                    <td>{flight.price} </td>
                    <td>{flight.userRatings}</td>
                    <td>
                      <Link to={`/bookflight/${flight.flightNumber}`}>
                        Book
                      </Link>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
          {otherFlights.length > 0 && (
            <React.Fragment>
              <h3 colSpan="7">Other departing flights</h3>
              <table className="table">
                <thead>
                  <tr>
                    <th>Flight Number</th>
                    <th>Airline</th>
                    <th>Departure Time</th>
                    <th>Arrival Time</th>
                    <th>Duration</th>
                    <th>Price</th>
                    <th>Ratings</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {otherFlights.map((flight, index) => (
                    <React.Fragment key={flight.flightNumber}>
                      <tr>
                        <td>{flight.flightNumber} </td>
                        <td>{flight.airline} </td>
                        <td>{flight.departureTime}</td>
                        <td>{flight.arrivalTime}</td>
                        <td>{flight.duration} hours</td>
                        <td>{flight.price} </td>
                        <td>{flight.userRatings}</td>
                        <td>
                          <Link to={`/bookflight/${flight.flightNumber}`}>
                            Book
                          </Link>
                        </td>
                      </tr>
                    </React.Fragment>
                  ))}
                </tbody>
              </table>
            </React.Fragment>
          )}
        </div>
      </div>
    );
  } else {
    return (
      <div>
        <div>
          <label htmlFor="sort">Sort By:</label>
          <select
            id="sort"
            name="sort"
            value={sortOption}
            onChange={handleSortOptionChange}
          >
            <option value="">Select an option</option>
            <option value="price">Price (Low to High)</option>
            <option value="duration">Duration (Low to High)</option>
            <option value="ratings">Ratings (High to Low)</option>
          </select>
        </div>
        <h3>Showing flights sorted by {sortOption}</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Flight Number</th>
              <th>Airline</th>
              <th>Departure Time</th>
              <th>Arrival Time</th>
              <th>Duration</th>
              <th>Price</th>
              <th>Ratings</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {sortedFlights &&
              sortedFlights.map((flight, index) => (
                <tr key={flight.flightNumber}>
                  <td>{flight.flightNumber} </td>
                  <td>{flight.airline} </td>
                  <td>{flight.departureTime}</td>
                  <td>{flight.arrivalTime}</td>
                  <td>{flight.duration} hours</td>
                  <td>{flight.price} </td>
                  <td>{flight.userRatings}</td>
                  <td>
                    <Link to={`/bookflight/${flight.flightNumber}`}>Book</Link>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    );
  }
}
