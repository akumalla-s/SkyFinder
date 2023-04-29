import React from "react";
import UpdateUser from "./UpdateUser";
import UpdatePassword from "./UpdatePassword";

export default function Profile() {
  return (
    <div>
      <UpdateUser />
      <br />
      <br />
      <br />
      <UpdatePassword />
    </div>
  );
}
