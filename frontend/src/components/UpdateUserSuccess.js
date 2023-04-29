import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function UpdateUserSuccess() {
  let navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/profile");
    }, 500);

    return () => clearTimeout(timer);
  });
  return (
    <div>
      <div>
        <h1>User update Successful!</h1>
      </div>
    </div>
  );
}
