import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import HelperService from "../services/HelperService";

export default function Logout() {
  const user = HelperService.getCurrentUserData();
  console.log(user);
  let navigate = useNavigate();

  useEffect(() => {
    if (user) {
      localStorage.removeItem("userData");
      navigate("/");
      window.location.reload();
    }
  }, [navigate, user]);

  return null;
}
