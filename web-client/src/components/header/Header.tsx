import { Link } from "react-router-dom";

function Header() {
  return (
    <header>
      <nav>
        <ul>
          <li><Link to="/classes">Classes</Link></li>
          <li><Link to="/students">Students</Link></li>
        </ul>
      </nav>
    </header>
  );
}

export default Header;