import { useState } from 'react';
import { useLocation } from 'react-router-dom';

import { icons } from '../../App.config';
import MenuItem from './MenuItem';

function Header() {
  const location = useLocation();
  const [open, setOpen] = useState(false);
  const isSelected = (path: string) => location.pathname.startsWith(path);

  return (
    <header className="py-3 px-5 bg-white border border-gray-200">
      <nav className="h-8 flex items-center">
        <div className="mt-1">
          <button>
            <svg
              className="w-5 h-5 sm:hidden"
              viewBox="0 0 1792 1792"
              onClick={() => setOpen(!open)}
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d={icons.hamburger} />
            </svg>
          </button>
          {open &&
            <ul
              onBlur={() => setOpen(false)}
              className="absolute w-60 z-50 bg-gray-50 shadow-md"
            >
              <li onClick={() => setOpen(false)}>
                <MenuItem
                  name="Classes"
                  isSelected={isSelected('/classes')}
                />
              </li>
              <li onClick={() => setOpen(false)}>
                <MenuItem
                  name="Students"
                  isSelected={isSelected('/students')}
                />
              </li>
            </ul>
          }
        </div>
        <img
          alt="Logo"
          src="/logo192.png"
          className="w-6 h-6 ml-4 sm:hidden"
        />


      </nav>

    </header >
  );
}

export default Header;
