import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';

// Utils & Models
import { addStudent, deleteStudent, getStudent, updateStudent } from '../services/StudentService';
import { AuthContext } from '../context/AuthContext';
import { genders } from '../App.config';
import { IStudent } from '../shared/models/IStudent';

// Components
import InputText from '../components/common/InputText';
import FormActionDelete from '../components/common/form/FormActionDelete';
import FormCancel from '../components/common/form/FormCancel';
import FormHeader from '../components/common/form/FormHeader';
import FormSave from '../components/common/form/FormSave';
import Loader from '../components/common/Loader';
import { newStudent } from '../shared/utils/ModelUtil';

const types = Array.from(Array(8).keys()).map(key => `st${key + 1}`);

function StudentForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const [student, setStudent] = useState<IStudent | null>(null);

  useEffect(() => {
    if (id) getStudent(id).then(setStudent);
    else setStudent(newStudent());
  }, [id]);

  const handleSave = () => {
    const token = user?.accessToken || '';
    const savedClazz = id ? updateStudent(id, student!, token) : addStudent(student!, token);
    savedClazz.then(r => {console.log(r);navigate('/students')}).catch(console.error);
  }

  const handleDelete = () => {
    const token = user?.accessToken || '';
    const isDeleted = window.confirm(`Are you sure you want to delete the Student [${id}]`);
    if (isDeleted && id) deleteStudent(id, token).then(_ => navigate('/students'));
  }

  if (!student) return <Loader />
  return (
    <section className="p-4 bg-white rounded-lg">
      <FormHeader
        title={id ? 'Update Student' : 'Create Student'}
        actions={id ? [<FormActionDelete handleDelete={handleDelete} />] : []}
      />
      <section className="md:px-2">
        <InputText
          label="Id No"
          required={true}
          value={student.idNo}
          onChange={value => setStudent({ ...student, idNo: value })}
        />
        <InputText
          label="First Name"
          value={student.name}
          onChange={value => setStudent({ ...student, name: value })}
        />
        <InputText
          label="Last Name"
          value={student.lastName}
          onChange={value => setStudent({ ...student, lastName: value })}
        />
        <section className="flex flex-wrap gap-2">
          {types.map(type =>
            <button
              key={type}
              onClick={() => setStudent({ ...student, type })}
              className={`p-1 rounded-md border border-sky-400 ${type === student.type ? 'bg-sky-200' : 'hover:bg-sky-100 '}`}
            >
              <div className={type} />
            </button>
          )}
        </section>
        <section className="flex flex-col">
          <label className="mt-4 mb-1 text-sm">Gender *</label>
          {genders.map((gender, index) =>
            <label
              key={index}
              className={`inline-flex gap-2 font-light py-0.5 px-2`}
            >
              <input
                type="radio"
                className="accent-sky-600"
                checked={student.genderId === index + 1}
                onChange={() => setStudent({ ...student, genderId: index + 1 })}
              />
              {gender}
            </label>
          )}
        </section>
        <section className="flex justify-center gap-2 mt-4 pt-4 border-t border-gray-200">
          <FormSave
            handleSave={handleSave}
            isDisabled={student.id === ''}
          />
          <FormCancel />
        </section >
      </section>
    </section>
  );
}

export default StudentForm;
