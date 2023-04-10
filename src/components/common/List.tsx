import { useNavigate } from 'react-router';

import { IStudent } from '../../shared/models/IStudent';

function List({ student }: { student: IStudent }) {
  const date = new Date(student.updatedAt);
  const navigate = useNavigate();
  return (
    <section
      onClick={() => navigate(`/students/${student.id}`)}
      className="flex py-3 px-4 gap-2 items-center bg-white rounded-lg transition hover:-translate-y-1 hover:shadow-md"
    >
      <div className="p-1 bg-sky-50 rounded-md border border-sky-300">
        <div className={student.type ? student.type : 'w-[48px] h-[48px]'} />
      </div>
      <p className="flex-1">{student.firstName} {student.lastName}<span className="block text-gray-600 font-medium text-xs">@{student.idNo}</span></p>
      <p className="text-sm text-end font-medium">Last update<span className="block font-normal">{date.toDateString()}</span></p>
    </section>
  );
}

export default List;