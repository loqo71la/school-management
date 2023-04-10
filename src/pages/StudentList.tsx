import { PencilSquare, SortDown } from '@loqo71la/react-web-icons';
import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { api } from '../config';
import { AuthContext } from '../context/AuthContext';
import { getStudents } from '../services/StudentService';
import { IPageable } from '../shared/models/IPageable';
import { IStudent } from '../shared/models/IStudent';
import { IRequest } from '../shared/models/IData';
import List from '../components/common/List';
import Loader from '../components/common/Loader';
import Pagination from '../components/common/Pagination';
import { newPage } from '../shared/utils/ModelUtil';

function StudentList() {
  const [request, setRequest] = useState<IRequest<IPageable<IStudent>>>({ isLoading: true });
  const [sortBy, setSortBy] = useState(api.sortBy);
  const { authorized } = useContext(AuthContext);
  const [page, setPage] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    setRequest({ isLoading: true });

    let data = newPage<IStudent>();
    getStudents(page, api.size, sortBy)
      .then(studentPage => data = studentPage)
      .finally(() => setRequest({isLoading: false, data}));
  }, [page, sortBy]);

  const handleCreate = () => authorized(() => navigate('/students/create'));

  if (request.isLoading) return <Loader />
  return (
    <>
      <section className="flex flex-col sm:flex-row justify-between items-center gap-2">
        <div>
          <p className="font-light">Viewing {request.data?.items.length} from {request.data?.totalItems} Student(s)</p>
          <div className="inline-flex items-center gap-1 text-sm font-light">
            <SortDown className="w-4 h-4" />
            <span>Sort by</span>
            <select
              value={sortBy}
              onChange={event => setSortBy(event.target.value)}
              className="ml-1.5 py-1 px-2 bg-gray-100 text-sm border border-gray-400 rounded-full focus:outline-none"
            >
              <option value="date">Last Update</option>
              <option value="name">Name A-Z</option>
            </select>
          </div>
        </div>
        <button
          type="button"
          onClick={handleCreate}
          className="flex gap-3 items-center px-5 py-2 text-sm text-white bg-gradient-to-r from-sky-500 to-sky-700 hover:bg-gradient-to-br focus:outline-none rounded-full"
        >
          <PencilSquare className="w-5 h-5" />
          Add Student
        </button>
      </section>
      <section className="mt-4 grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-3">
        {request.data?.items.map(student =>
          <List
            key={student.idNo}
            student={student}
          />)
        }
      </section>
      <Pagination
        className="text-center mt-2"
        pageable={request.data!}
        onSelected={setPage}
      />
    </>
  );
}

export default StudentList;
