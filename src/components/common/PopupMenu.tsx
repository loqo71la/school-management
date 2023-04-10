import { useEffect, useRef, useState } from 'react';

interface PopupMenuProps {
  trigger: JSX.Element;
  options: JSX.Element[];
  alignment?: 'left' | 'right';
}

function PopupMenu(props: PopupMenuProps) {
  const [isOpen, setIsOpen] = useState(false);
  const div = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const listener = ({ target }: MouseEvent) => {
      const isContained = div.current?.contains(target as Node) || false;
      if (!isContained) setIsOpen(isContained);
    };
    window.addEventListener('click', listener);
    return () => window.removeEventListener('click', listener);
  }, []);

  return (
    <div
      ref={div}
      className="relative"
    >
      <button onClick={() => setIsOpen(!isOpen)}>
        {props.trigger}
      </button>
      {isOpen &&
        <ul className={`absolute w-52 ${props.alignment === 'right' ? 'right-0' : 'left-0'} border border-gray-200 rounded-lg z-50 divide-y divide-slate-200 bg-white shadow-md`}>
          {props.options.map((option, index) =>
            <li key={index}>
              {option}
            </li>
          )}
        </ul>
      }
    </div>
  );
}

export default PopupMenu;
