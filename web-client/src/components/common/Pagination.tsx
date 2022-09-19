import { ChevronLeft, ChevronRight } from '@loqo71la/react-web-icons';
import { IPageable } from '../../shared/models/IPageable';

interface PaginationProps {
  className?: string;
  pageable: IPageable<any>;
  onSelected: (page: number) => void;
}

function Pagination({ className, pageable, onSelected }: PaginationProps) {
  const pages = Array.from(Array(pageable.totalPages).keys()).map(key => key + 1);

  const hasLeft = () => pageable.currentPage > 1;
  const hasRight = () => pageable.currentPage < pageable.totalPages;
  const moveLeft = () => hasLeft() && onSelected(pageable.currentPage - 1);
  const moveRight = () => hasRight() && onSelected(pageable.currentPage + 1);

  return (
    <nav className={className}>
      <ul className="inline-flex bg-white border border-sky-400 rounded-lg">
        <li
          onClick={moveLeft}
          className={`w-8 h-8 p-2 rounded-l-lg ${hasLeft() ? 'hover:bg-sky-100' : 'text-gray-400'}`}
        >
          <ChevronLeft className="w-4 h-4" />
        </li>
        {pages.map(page =>
          <li
            key={page}
            onClick={() => pageable.currentPage !== page && onSelected(page)}
            className={`w-8 h-8 py-1.5 text-sm text-center ${page === pageable.currentPage ? 'bg-sky-500 text-white' : 'hover:bg-sky-100'}`}
          >
            {page}
          </li>
        )}
        <li
          onClick={moveRight}
          className={`w-8 h-8 p-2 rounded-r-lg ${hasRight() ? 'hover:bg-sky-100' : 'text-gray-400'}`}
        >
          <ChevronRight className="w-4 h-4" />
        </li>
      </ul>
    </nav>
  );
}

export default Pagination;
