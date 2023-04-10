import { Link, useLocation } from 'react-router-dom';
import { menus } from '../../config';

function Menu(props: { className?: string, onClick?: () => void }) {
  const location = useLocation();
  const isSelected = (path: string) => location.pathname.startsWith(path);

  return (
    <aside className={`flex-none w-64 h-full bg-[#434958] ${props.className || ''}`}>
      <img
        alt="Logo"
        src="/logo192.png"
        className="w-20 h-20 mx-auto my-8 rounded-full bg-white"
      />
      {menus.map((menu, index) => {
        const selected = isSelected(menu.path);
        return (
          <Link
            className={`py-2 px-5 flex items-center gap-3 text-white ${selected ? 'bg-sky-500' : 'hover:bg-sky-800'}`}
            onClick={props.onClick}
            to={menu.path}
            key={index}
          >
            {selected ?
              <menu.selectedIcon className="w-4 h-4" /> :
              <menu.icon className="w-4 h-4" />
            }
            {menu.name}
          </Link>
        );
      })}
    </aside>
  );
}

export default Menu;
