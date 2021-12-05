import { useLocation, Link } from "react-router-dom";
import './Header.css'

function Header() {
  const location = useLocation();
  const getClassName = (path: string) => location.pathname.startsWith(path) ? 'enable' : '';
  return (
    <header>
      <nav className="navbar">
        <ul>
          <li><Link to="/classes" className={getClassName('/classes')}>Classes</Link></li>
          <li><Link to="/students" className={getClassName('/students')}>Students</Link></li>
        </ul>
      </nav>
    </header>
  );
}

export default Header;