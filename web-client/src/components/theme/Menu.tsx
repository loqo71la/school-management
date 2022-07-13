import { useLocation } from 'react-router-dom';

import MenuItem from './MenuItem';

function Menu() {
  const location = useLocation();
  const isSelected = (path: string) => location.pathname.startsWith(path);

  return (
    <aside className="w-72 h-full py-4 hidden sm:block text-white font-medium bg-gradient-to-t from-teal-400 via-teal-600 to-teal-800">
      <img
        alt="Logo"
        src="/logo192.png"
        className="w-20 h-20 mx-auto mb-8"
      />
      <MenuItem
        name="Classes"
        isSelected={isSelected('/classes')}
      />
      <MenuItem
        name="Students"
        isSelected={isSelected('/students')}
      />
    </aside>
  );
}

export default Menu;
