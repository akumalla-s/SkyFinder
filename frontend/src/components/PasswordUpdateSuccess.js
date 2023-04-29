import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function PasswordUpdateSuccess() {
  let navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/logout");
    }, 3000);

    return () => clearTimeout(timer);
  });
  return (
    <div>
      <h1>Password update Successful!</h1>
      <p>You will be redirected to the Home page shortly.</p>
    </div>
  );
}
