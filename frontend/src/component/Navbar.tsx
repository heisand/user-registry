import { Link } from "react-router-dom";
import "../App.css";

export function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/">Cancer Registry</Link>
      </div>
      <ul className="navbar-links">
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/users">Users</Link>
        </li>
        <li>
          <Link to="/units">Units</Link>
        </li>
        <li>
          <Link to="/roles">Roles</Link>
        </li>
        <li>
          <Link to="/user-roles">User Roles</Link>
        </li>
      </ul>
    </nav>
  );
}
