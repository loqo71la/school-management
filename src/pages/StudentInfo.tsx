import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';

import { AuthContext } from '../context/AuthContext';
import { IStudent } from '../shared/models/IStudent';
import { deleteStudent, getStudent } from '../services/StudentService';
import { genders } from '../config';
import { toDate } from '../shared/utils/DateUtil';
import FormHeader from '../components/common/form/FormHeader';
import Loader from '../components/common/Loader';
import { DeleteBtn, EditBtn } from '../components/common/form/FormBtn';

function StudentInfo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { authorized } = useContext(AuthContext);
  const [student, setStudent] = useState<IStudent | null>(null);

  useEffect(() => { getStudent(id!).then(setStudent); }, [id]);

  const handleEdit = () => {
    authorized(() => navigate(`/students/${student?.id}/update`));
  }

  const handleDelete = () => {
    authorized(() => {
      const isDeleted = window.confirm(`Are you sure you want to delete the Student [${student?.idNo}]`);
      if (isDeleted && id) deleteStudent(id).then(_ => navigate(-1));
    });
  }

  if (!student) return <Loader />
  return (
    <section className="p-4 bg-white rounded-lg">
      <FormHeader
        title="Student Info"
        actions={[
          <EditBtn onClick={handleEdit} />,
          <DeleteBtn onClick={handleDelete} />
        ]}
      />
      <div className="w-fit p-1 bg-sky-50 rounded-md border border-sky-300">
        <div className={student.type ? student.type : 'w-[48px] h-[48px]'}></div>
      </div>
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-1 px-2 mt-4 text-sm font-medium">
        <label>Id No:<span className="block text-base font-light">{student.idNo}</span></label>
        <label>First Name:<span className="block text-base font-light">{student.firstName}</span></label>
        <label>Last Name:<span className="block text-base font-light">{student.lastName}</span></label>
        <label>Gender:<span className="block text-base font-light">{genders[student.gender - 1]}</span></label>
        <label>Latitude:<span className="block text-base font-light">{student.latitude}</span></label>
        <label>Longitude:<span className="block text-base font-light">{student.longitude}</span></label>
      </section>
      <section className="grid md:grid-cols-2 gap-1 px-2 py-6 text-sm font-medium">
        <label>Created By:<span className="block text-base font-light">@{student.createdBy}</span></label>
        <label>Updated By:<span className="block text-base font-light">@{student.updatedBy}</span></label>
        <label>Created At:<span className="block text-base font-light">{toDate(student.createdAt)}</span></label>
        <label>Updated At:<span className="block text-base font-light">{toDate(student.updatedAt)}</span></label>
      </section>
    </section>
  );
}

export default StudentInfo;