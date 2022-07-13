import { Link } from 'react-router-dom';

import { icons } from '../../App.config';

function MenuItem({ name, isSelected }: { name: string, isSelected: boolean }) {
  const path = name.toLocaleLowerCase();
  return (
    <>
      <Link
        to={`/${path}`}
        className={`flex p-2 hover:bg-teal-600 ${isSelected && 'bg-teal-900 text-teal-100'}`}
      >
        <svg
          className="h-5 w-5 ml-2"
          viewBox="0 0 20 20"
          fill="currentColor"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d={icons[path as keyof typeof icons]} />
        </svg>
        <span className="flex-1 ml-3">{name}</span>
      </Link>
    </>
  );
}

export default MenuItem;
