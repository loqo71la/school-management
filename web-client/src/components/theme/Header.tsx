import { List } from '@loqo71la/react-web-icons';

import Profile from './Profile';

function Header(props: { onClick: () => void }) {
  return (
    <header className="py-3 px-5 bg-white border border-gray-200">
      <nav className="h-8 flex items-center justify-between">
        <button onClick={props.onClick}>
          <List className="w-6 h-6 sm:hidden" />
        </button>
        <Profile />
      </nav>
    </header>
  );
}

export default Header;
