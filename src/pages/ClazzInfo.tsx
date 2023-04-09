import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import { DeleteBtn, EditBtn } from '../components/common/form/FormBtn';

import FormHeader from '../components/common/form/FormHeader';
import Loader from '../components/common/Loader';
import { AuthContext } from '../context/AuthContext';

import { deleteClazz, getClazz, getStudentsByClazz } from '../services/ClazzService';
import { IClazz } from '../shared/models/IClazz';
import { toDate } from '../shared/utils/DateUtil';

function ClazzInfo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { authorized } = useContext(AuthContext);
  const [clazz, setClazz] = useState<IClazz | null>(null);

  useEffect(() => {
    getClazz(id!).then((clazz: IClazz) => {
      getStudentsByClazz(id!)
        .then(page => clazz.students = page.items)
        .finally(() => setClazz(clazz));
    });
  }, [id]);

  const handleEdit = () => {
    authorized(() => navigate(`/classes/${clazz?.id}/update`));
  }

  const handleDelete = () => {
    authorized(() => {
      const isDeleted = window.confirm(`Are you sure you want to delete the Class [${clazz?.code}]`);
      if (isDeleted && id) deleteClazz(id).then(_ => navigate(-1));
    });
  }

  if (!clazz) return <Loader />
  const teacher = clazz.students.find(current => current.id === clazz.teacherId);
  return (
    <section className="p-4 bg-white rounded-md">
      <FormHeader
        title="Class Info"
        actions={[
          <EditBtn onClick={handleEdit} />,
          <DeleteBtn onClick={handleDelete} />
        ]}
      />
      <div className="w-fit p-1 bg-sky-50 rounded-md border border-sky-300">
        <div className={clazz.type ? clazz.type : 'w-[48px] h-[48px]'}></div>
      </div>
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-3 px-2 my-3 text-sm font-medium">
        <label>Code:<span className="block text-base font-light">{clazz.code}</span></label>
        <label>Title:<span className="block text-base font-light">{clazz.title}</span></label>
        <label>Enable:<span className="block text-base font-light">{clazz.enable ? 'True' : 'False'}</span></label>
        <label>Teacher:<span className="block text-base font-light">{`${teacher?.firstName || ''} ${teacher?.lastName || ''}`}</span></label>
        <label>Latitude:<span className="block text-base font-light">{clazz.latitude}</span></label>
        <label>Longitude:<span className="block text-base font-light">{clazz.longitude}</span></label>
      </section>
      <label className="px-2">Description:<span className="px-2 block text-base font-light">{clazz.description}</span></label>
      <section className="grid md:grid-cols-2 gap-1 px-2 py-4 text-sm font-medium">
        <label>Created By:<span className="block text-base font-light">@{clazz.createdBy}</span></label>
        <label>Updated By:<span className="block text-base font-light">@{clazz.updatedBy}</span></label>
        <label>Created At:<span className="block text-base font-light">{toDate(clazz.createdAt)}</span></label>
        <label>Updated At:<span className="block text-base font-light">{toDate(clazz.updatedAt)}</span></label>
      </section>
      {clazz.students.length > 0 &&
        <section className="mt-2 px-2">
          <h4 className="text-sm font-medium">Assigned Students:</h4>
          <section className="flex flex-wrap gap-2 mt-1">
            {clazz.students.map((current, index) =>
              <div
                key={index}
                className="inline-flex gap-1 pl-1 pr-2 items-center rounded-md border border-sky-300 bg-sky-50"
              >
                <div className={current.type} />
                <label className="font-light">
                  {`${current?.firstName || ''} ${current?.lastName || ''}`}
                  <span className="block text-xs font-medium text-gray-600">@{current.idNo}</span>
                </label>
              </div>
            )}
          </section>
        </section>
      }
    </section>
  );
}

export default ClazzInfo;
