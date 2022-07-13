import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';

import { IStudent } from '../shared/models/IStudent';
import { newStudent } from '../shared/utils/StudentUtil';
import { addStudent, deleteStudent, getStudent, updateStudent } from '../services/StudentService';
import Form from '../components/landing/Form';
import InputText from '../components/common/InputText';
import Loader from '../components/common/Loader';

function Student() {
  const { idNo } = useParams();
  const navigate = useNavigate();
  const [student, setStudent] = useState<IStudent | null>(null);

  useEffect(() => {
    if (idNo === 'create') setStudent(newStudent());
    else getStudent(idNo!).then(data => setStudent(data));
  }, [idNo]);

  const handleSave = () => {
    console.log(student)
    const savedClazz = idNo !== 'create' ? updateStudent(student!.idNo, student!) : addStudent(student!);
    savedClazz.then(_ => navigate('/students'));
  }

  const handleDelete = () => {
    const isDeleted = window.confirm(`Are you sure you want to delete the Student [${idNo}]`);
    if (isDeleted && idNo) deleteStudent(idNo).then(_ => navigate('/students'));
  }

  if (!student) return <Loader />
  return (
    <Form
      title={`${idNo !== 'create' ? 'Update' : 'Create'} Student`}
      onCancel={() => navigate('/students')}
      disabled={student.idNo === ''}
      onDelete={handleDelete}
      onSubmit={handleSave}
    >
      <>
        <InputText
          label="Id No"
          required={true}
          value={student.idNo}
          disabled={idNo !== 'create'}
          onChange={value => setStudent({ ...student, idNo: value })}
        />
        <InputText
          label="First Name"
          value={student.firstName}
          onChange={value => setStudent({ ...student, firstName: value })}
        />
        <InputText
          label="Last Name"
          value={student.lastName}
          onChange={value => setStudent({ ...student, lastName: value })}
        />
        <label className="block mb-1">Gender</label>
        <label className="inline-flex items-center font-light">
          <input
            type="radio"
            checked={student.gender}
            onChange={() => setStudent({...student, gender: true})}
            />
          <span className="ml-2">Male</span>
        </label>
        <label className="inline-flex items-center ml-4 font-light">
          <input
            type="radio"
            checked={!student.gender}
            onChange={() => setStudent({...student, gender: false})}
          />
          <span className="ml-2">Female</span>
        </label>
      </>
    </Form>
  );
}

export default Student;