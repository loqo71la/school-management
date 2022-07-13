import { icons } from '../../App.config';
import { IPageable } from '../../shared/models/IPageable';

function Pagination({ pageable, onSelected }: { pageable: IPageable<any>, onSelected: (page: number) => void }) {
  const loadPages = () => Array.from(Array(pageable.totalPage).keys());

  const hasLeft = () => pageable.currentPage > 0;
  const hasRight = () => pageable.currentPage < pageable.totalPage - 1;
  const moveLeft = () => hasLeft() && onSelected(pageable.currentPage - 1);
  const moveRight = () => hasRight() && onSelected(pageable.currentPage + 1);

  return (
    <nav className="mt-4 text-center">
      <ul className="inline-flex items-center bg-white text-gray-500 rounded-lg shadow-md">
        <li
          onClick={moveLeft}
          className={`py-2 px-3 ${hasLeft() && 'hover:bg-gray-100'}`}
        >
          <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <path fillRule="evenodd" d={icons.left}></path>
          </svg>
        </li>
        {loadPages().map(page =>
          <li
            key={page}
            onClick={() => onSelected(page)}
            className={`py-2 px-4 ${page === pageable.currentPage && 'bg-teal-500 text-white'} hover:bg-gray-100 hover:text-gray-500`}>
            {page + 1}
          </li>
        )}
        <li
          onClick={moveRight}
          className={`py-2 px-3 ${hasRight() && 'hover:bg-gray-100'}`}
        >
          <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <path fillRule="evenodd" d={icons.right}></path>
          </svg>
        </li>
      </ul>
    </nav>
  );
}

export default Pagination;
