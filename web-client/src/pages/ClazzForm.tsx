import { ToggleOff, ToggleOn } from '@loqo71la/react-web-icons';
import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import FormActionDelete from '../components/common/form/FormActionDelete';
import FormCancel from '../components/common/form/FormCancel';
import FormHeader from '../components/common/form/FormHeader';
import FormSave from '../components/common/form/FormSave';
import InputText from '../components/common/InputText';
import Loader from '../components/common/Loader';
import Pagination from '../components/common/Pagination';
import { AuthContext } from '../context/AuthContext';

import { addClazz, assignStudents, deleteClazz, getClazz, updateClazz } from '../services/ClazzService';
import { getStudents } from '../services/StudentService';
import { IClazz } from '../shared/models/IClazz';
import { IPageable } from '../shared/models/IPageable';
import { newClazz, newPage } from '../shared/utils/ModelUtil';

const types = Array.from(Array(32).keys()).map(key => `cz${key + 1}`);

function ClazzForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const [clazz, setClazz] = useState<IClazz | null>(null);
  const [studentPage, setStudentPage] = useState<IPageable<any>>(newPage<any>());

  useEffect(() => {
    if (id !== undefined) {
      getClazz(id).then((saved: IClazz) => {
        const ids = saved.students.map(({ id }) => id)
        getStudents().then(target => {
          target.items = target.items.map(item => ({ ...item, isSelected: ids.includes(item.id) }));
          setStudentPage(target);
        });
        setClazz(saved);
      });
    } else {
      getStudents().then(setStudentPage);
      setClazz(newClazz());
    }
  }, [id]);

  const handleSave = () => {
    const token = user?.accessToken || '';
    const savedClazz = id ? updateClazz(id, clazz!, token) : addClazz(clazz!, token);
    savedClazz.then(res => {
      assignStudents(res.id || id, clazz?.students.map(current => current.id) || [], clazz?.teacher, token)
        .then(_ => navigate('/classes'));
    });
  };

  const handleDelete = () => {
    const token = user?.accessToken || '';
    const isDeleted = window.confirm(`Are you sure you want to delete the Class [${clazz?.code}]`);
    if (isDeleted && id) deleteClazz(id, token).then(_ => navigate('/classes'));
  };

  const loadStudents = (page = 1) => getStudents(page).then(current => {
    const ids = clazz?.students.map(current => current.id) || [];
    current.items = current.items.map(item => ({ ...item, isSelected: ids.includes(item.id) }));
    setStudentPage(current);
  });

  const selectStudent = (index: number) => {
    if (!clazz) return;

    const current = studentPage.items[index];
    if (current.isSelected) {
      const index = clazz.students.findIndex(target => target.id === current.id);
      if (clazz.teacher === current.id) delete clazz.teacher;
      if (index >= 0) clazz.students.splice(index, 1);
    } else {
      clazz.students.push(current);
    }
    setClazz({ ...clazz });
    current.isSelected = !current.isSelected;
    setStudentPage({ ...studentPage });
  }

  if (!clazz) return <Loader />
  return (
    <section className="p-4 bg-white rounded-lg">
      <FormHeader
        title={id ? 'Update Clazz' : 'Create Clazz'}
        actions={id ? [<FormActionDelete handleDelete={handleDelete} />] : []}
      />
      <section className="md:px-2">
        <InputText
          label="Code"
          required={true}
          value={clazz.code}
          onChange={value => setClazz({ ...clazz, code: value })}
        />
        <InputText
          label="Title"
          value={clazz.title}
          onChange={value => setClazz({ ...clazz, title: value })}
        />
        <section className="flex flex-wrap gap-2">
          {types.map(type =>
            <button
              key={type}
              onClick={() => setClazz({ ...clazz, type })}
              className={`p-1 rounded-md border border-sky-400 ${type === clazz.type ? 'bg-sky-200' : 'hover:bg-sky-100 '}`}
            >
              <div className={type} />
            </button>
          )}
        </section>
        <section className="flex items-center gap-4 mt-4 mb-2">
          <label className="text-sm p-0 m-0">Enable</label>
          <button
            type="button"
            onClick={() => setClazz({ ...clazz, enable: !clazz.enable })}
          >
            {clazz.enable ? <ToggleOn className="w-7 h-7 fill-sky-600" /> : <ToggleOff className="w-7 h-7 fill-sky-600" />}
          </button>
        </section>
        <label className="text-sm">
          Description
          <textarea
            rows={4}
            cols={50}
            value={clazz.description}
            onChange={event => setClazz({ ...clazz, description: event.target.value })}
            className="block w-full mb-2 p-1 text-base font-light border border-gray-300 text-gray-700 focus:outline-sky-400"
          />
        </label>
        <label className="text-sm block">Teacher</label>
        <select
          value={clazz.teacher}
          onChange={event => setClazz({ ...clazz, teacher: event.target.value })}
          className="w-full py-1.5 px-2 text-base font-light rounded-md border border-gray-300 text-gray-700 focus:outline-sky-400"
        >
          <option value={undefined}>Select an assigned Student</option>
          {clazz.students.length > 0 &&
            clazz.students.map((current, index) =>
              <option key={index} value={current.id}>{current.name} {current.lastName}</option>
            )
          }
        </select>
        {studentPage.items.length > 0 &&
          <section className="mt-3">
            <h4 className="text-sm">Students:</h4>
            <section className="flex flex-wrap gap-2 mt-1">
              {studentPage.items.map((student: any, index: number) =>
                <button
                  key={index}
                  onClick={() => selectStudent(index)}
                  className={`inline-flex gap-1 pr-1.5 items-center rounded-md border border-sky-400 ${student.isSelected ? 'bg-sky-200' : 'hover:bg-sky-100 '}`}
                >
                  <div className={student.type} />
                  <label className="font-light">
                    {`${student.name || ''} ${student.lastName || ''}`}
                    <span className="block text-left text-xs font-medium text-gray-600">@{student.idNo}</span>
                  </label>
                </button>
              )}
            </section>
            <Pagination
              className="mt-2"
              pageable={studentPage}
              onSelected={loadStudents}
            />
          </section>
        }
      </section>
      <section className="flex justify-center gap-2 mt-4 pt-4 border-t border-gray-200">
        <FormSave
          handleSave={handleSave}
          isDisabled={clazz.code === ''}
        />
        <FormCancel />
      </section >
    </section>
  );
}

export default ClazzForm;
