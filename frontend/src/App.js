import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Body from "./components/Body";
import Footer from "./components/Footer";
import Register from "./components/RegistrationForm";
import Login from "./components/Login";
import RegistrationSuccess from "./components/RegistrationSuccess";
import About from "./components/About";
import DisplayFlights from "./components/DisplayFlights";
import Logout from "./components/Logout";
import Profile from "./components/Profile";
import PasswordUpdateSuccess from "./components/PasswordUpdateSuccess";
import BookFlight from "./components/BookFlight";
import FlightBookingSuccess from "./components/FlightBookingSuccess";
import DisplayFlightBooking from "./components/DisplayFlightBooking";
import CancelBooking from "./components/CancelBooking";
import UpdateUserSuccess from "./components/UpdateUserSuccess";
import ViewUsers from "./components/ADMIN/ViewUsers";
import DeleteUser from "./components/ADMIN/DeleteUser";
import UpdateUser from "./components/UpdateUser";

export default function App() {
  return (
    <Router>
      <div className="container">
        <Header />
        <div className="main">
          <Routes>
            <Route exact path="/" element={<Body />} />
            <Route exact path="/register" element={<Register />} />
            <Route
              exact
              path="/registration-success"
              element={<RegistrationSuccess />}
            />
            <Route
              exact
              path="/password-update-success"
              element={<PasswordUpdateSuccess />}
            />

            <Route
              exact
              path="/user-update-success"
              element={<UpdateUserSuccess />}
            />
            <Route exact path="/login" element={<Login />} />
            <Route exact path="/logout" element={<Logout />} />
            <Route exact path="/about" element={<About />} />
            <Route exact path="/displayFlights" element={<DisplayFlights />} />
            <Route exact path="/profile" element={<Profile />} />
            <Route exact path="/updateUser" element={<UpdateUser />} />
            <Route
              exact
              path="/bookflight/:flightnumber"
              element={<BookFlight />}
            />
            <Route
              exact
              path="/flightbookingsuccess"
              element={<FlightBookingSuccess />}
            />
            <Route
              exact
              path="/displayflightbooking"
              element={<DisplayFlightBooking />}
            />
            <Route
              exact
              path="/cancelflightbooking/:id"
              element={<CancelBooking />}
            />
            <Route exact path="/displayAllUsers" element={<ViewUsers />} />
            <Route
              exact
              path="/deleteUser/:username"
              element={<DeleteUser />}
            />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}
