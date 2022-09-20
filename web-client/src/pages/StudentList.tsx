import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { getStudents } from '../services/StudentService';
import { IPageable } from '../shared/models/IPageable';
import { IStudent } from '../shared/models/IStudent';
import { IRequest } from '../shared/models/IData';
import List from '../components/common/List';
import Loader from '../components/common/Loader';
import Pagination from '../components/common/Pagination';
import { PencilSquare } from '@loqo71la/react-web-icons';

function StudentList() {
  const [request, setRequest] = useState<IRequest<IPageable<IStudent>>>({ isLoading: true });
  const navigate = useNavigate();

  const loadStudents = (page: number = 1) => {
    setRequest({ isLoading: true });
    getStudents(page).then(data => setRequest({ isLoading: false, data: data }));
  }

  useEffect(() => { loadStudents() }, []);

  if (request.isLoading) return <Loader />
  return (
    <>
      <section className="flex flex-col sm:flex-row justify-between items-center gap-2">
        <p className="font-light">Viewing {request.data?.items.length} from {request.data?.totalItems} Student(s)</p>
        <button
          type="button"
          onClick={() => navigate('/students/create')}
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
        onSelected={loadStudents}
      />
    </>
  );
}

export default StudentList;
