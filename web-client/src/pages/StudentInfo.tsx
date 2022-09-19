import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';

import { IStudent } from '../shared/models/IStudent';
import { deleteStudent, getStudent } from '../services/StudentService';
import Loader from '../components/common/Loader';
import { genders } from '../App.config';
import { toDate } from '../shared/utils/DateUtil';
import FormHeader from '../components/common/form/FormHeader';
import FormActionEdit from '../components/common/form/FormActionEdit';
import FormActionDelete from '../components/common/form/FormActionDelete';
import { AuthContext } from '../context/AuthContext';

function StudentInfo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const [student, setStudent] = useState<IStudent | null>(null);

  useEffect(() => { getStudent(id!).then(setStudent); }, [id]);

  const handleDelete = () => {
    const token = user?.accessToken || '';
    const isDeleted = window.confirm(`Are you sure you want to delete the Student [${student?.idNo}]`);
    if (isDeleted && id) deleteStudent(id, token).then(_ => navigate(-1));
  }

  if (!student) return <Loader />
  return (
    <section className="p-4 bg-white rounded-lg">
      <FormHeader
        title="Student Info"
        actions={[
          <FormActionEdit path={`/students/${student.id}/update`} />,
          <FormActionDelete handleDelete={handleDelete} />
        ]}
      />
      <div className="w-fit p-1 bg-sky-50 rounded-md border border-sky-300">
        <div className={student.type ? student.type : 'w-[48px] h-[48px]'}></div>
      </div>
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-1 px-2 mt-4 text-sm font-medium">
        <label>Id No:<span className="block text-base font-light">{student.idNo}</span></label>
        <label>First Name:<span className="block text-base font-light">{student.name}</span></label>
        <label>Last Name:<span className="block text-base font-light">{student.lastName}</span></label>
        <label>Gender:<span className="block text-base font-light">{genders[student.genderId - 1]}</span></label>
        <label>Created Date:<span className="block text-base font-light">{toDate(student.createdAt)}</span></label>
        <label>Last Update:<span className="block text-base font-light">{toDate(student.updatedAt)}</span></label>
      </section>
    </section>
  );
}

export default StudentInfo;