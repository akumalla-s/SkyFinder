import React, { useState, useEffect } from "react";
import "../css/SearchBar.css";
import axios from "axios";
import DisplayFlights from "./DisplayFlights";

const cities = ["Toronto", "Massachusetts", "New York"];

export default function SearchBar() {
  const [fromCity, setFromCity] = useState(
    sessionStorage.getItem("fromCity") || ""
  );
  const [toCity, setToCity] = useState(sessionStorage.getItem("toCity") || "");
  const [startDate, setStartDate] = useState(
    sessionStorage.getItem("startDate") || ""
  );
  const [formErrors, setFormErrors] = useState({
    fromCity: "",
    toCity: "",
    startDate: "",
  });
  const [flights, setFlights] = useState(
    JSON.parse(sessionStorage.getItem("flights")) || []
  );
  const [noFlightsFound, setNoFlightsFound] = useState(false);

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    sessionStorage.setItem("fromCity", fromCity);
    sessionStorage.setItem("toCity", toCity);
    sessionStorage.setItem("startDate", startDate);
    sessionStorage.setItem("flights", JSON.stringify(flights));
  }, [fromCity, toCity, startDate, flights]);

  useEffect(() => {
    sessionStorage.clear();
  }, []);

  const validateForm = () => {
    let errors = {};

    //Validate fromCity
    if (!fromCity || fromCity.trim() === "") {
      errors.fromCity = "From City is required";
    }

    //Validate toCity
    if (!toCity || toCity.trim() === "") {
      errors.toCity = "To City is required";
    }

    if (fromCity === toCity) {
      errors.toCity = "To City should not be the same as From City";
    }

    //Validate startDate
    if (!startDate || startDate.trim() === "") {
      errors.startDate = "Start date is required";
    } else if (startDate.trim()) {
      const currentDate = new Date();
      console.log(startDate);
      const selectedDate = new Date(startDate + "T00:00:00");

      // Check if selected date is more than a year from the current date
      const oneYearFromNow = new Date();
      oneYearFromNow.setFullYear(currentDate.getFullYear() + 1);
      if (selectedDate > oneYearFromNow) {
        errors.startDate = "Start date should be within a year from now";
      }

      if (selectedDate < currentDate.setHours(0, 0, 0, 0)) {
        console.log(selectedDate);
        console.log(currentDate);
        errors.startDate = "Start date should be current or future date";
      }
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleFromCityChange = (event) => {
    const { name, value } = event.target;
    setFromCity(event.target.value);
    setFormErrors({ ...formErrors, [name]: value });
  };

  const handleToCityChange = (event) => {
    const { name, value } = event.target;
    setToCity(event.target.value);
    setFormErrors({ ...formErrors, [name]: value });
  };

  const handleStartDateChange = (event) => {
    const { name, value } = event.target;
    setStartDate(event.target.value);
    setFormErrors({ ...formErrors, [name]: value });
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (validateForm()) {
      console.log(
        `From City: ${fromCity}, To City: ${toCity}, Start Date: ${startDate}`
      );
      const data = {
        origin: fromCity,
        destination: toCity,
        date: startDate,
      };
      axios
        .post("http://localhost:8080/displayFlightsBasedOnInput", data)
        .then((response) => {
          setFlights(response.data);
          console.log(response.data); // do something with the response data
          setNoFlightsFound(response.data.length === 0);
        })
        .catch((error) => {
          console.error(error); // handle the error
        })
        .finally(() => {
          setLoading(false); // set the loading state back to false when the request completes
        });
    }
  };

  return (
    <div>
      <div className="errors">
        <div>
          {formErrors.fromCity && (
            <span className="error">{formErrors.fromCity}</span>
          )}
        </div>
        <div>
          {formErrors.toCity && (
            <span className="error">{formErrors.toCity}</span>
          )}
        </div>
        <div>
          {formErrors.startDate && (
            <span className="error">{formErrors.startDate}</span>
          )}
        </div>
      </div>
      <div className="search-bar">
        <form onSubmit={handleSearch}>
          <label htmlFor="origin"></label>
          <select
            className="select"
            value={fromCity}
            onChange={handleFromCityChange}
          >
            <option value="">From City*</option>
            {cities.map((city) => (
              <option key={city} value={city}>
                {city}
              </option>
            ))}
          </select>

          <label htmlFor="destination"></label>
          <select
            className="select"
            value={toCity}
            onChange={handleToCityChange}
          >
            <option value="">To City*</option>
            {cities.map((city) => (
              <option key={city} value={city}>
                {city}
              </option>
            ))}
          </select>

          <label htmlFor="startDate"></label>
          <input
            className="date-picker"
            type="date"
            value={startDate}
            onChange={handleStartDateChange}
          />
          <input
            className="search-btn"
            type="submit"
            value={loading ? "Loading..." : "Search"}
            onClick={handleSearch}
            disabled={loading} // disable the button while the request is pending
          />
        </form>
      </div>
      <div>
        <br></br>
      </div>
      {/* conditional rendering of the loading spinner */}
      {loading && <div className="loading-spinner">Loading...</div>}
      {noFlightsFound && (
        <p>
          Sorry, we couldn't find any flights that match your search criteria.
          Please try again with different dates, destinations, or other search
          options.
        </p>
      )}
      {flights.length > 0 && <DisplayFlights flights={flights} />}
    </div>
  );
}
