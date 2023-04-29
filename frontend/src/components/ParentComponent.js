import React, { useState } from "react";
import SearchBar from "./SearchBar";
import DisplayFlights from "./DisplayFlights";

export default function ParentComponent() {
  const [flights, setFlights] = useState([]);

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
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  return (
    <div>
      <div>
        <SearchBar setFlights={setFlights} handleSearch={handleSearch} />
      </div>
      <div>
        <br></br>
      </div>
      <div>
        <DisplayFlights flights={flights} />
      </div>
    </div>
  );
}
