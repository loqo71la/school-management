import { useNavigate } from 'react-router';

import { IStudent } from '../../shared/models/IStudent';

function List({ student }: { student: IStudent }) {
  const date = new Date(student.modifiedDate ?? student.createdDate);
  const navigate = useNavigate();
  return (
    <section
      onClick={() => navigate(`/students/${student.idNo}`)}
      className="flex py-3 px-4 gap-2 items-center bg-white rounded-lg shadow-md transition hover:-translate-y-1 hover:bg-gray-50"
    >
      <div className={`p-2 rounded-full ${student.gender ? 'bg-sky-100 text-sky-800' : 'bg-pink-100 text-pink-800'}`}>
        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" viewBox="0 0 20 20" fill="currentColor">
          <path fillRule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clipRule="evenodd" />
        </svg>
      </div>
      <p className="flex-1">{student.firstName} {student.lastName}<span className="block text-gray-500 font-bold text-xs">@{student.idNo}</span></p>
      <p className="text-xs text-end font-semibold">Last update<span className="block font-normal">{date.toDateString()}</span></p>
    </section>
  );
}

export default List;