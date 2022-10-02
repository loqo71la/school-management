import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import FormActionDelete from '../components/common/form/FormActionDelete';
import FormActionEdit from '../components/common/form/FormActionEdit';
import FormHeader from '../components/common/form/FormHeader';
import Loader from '../components/common/Loader';
import { AuthContext } from '../context/AuthContext';

import { deleteClazz, getClazz } from '../services/ClazzService';
import { IClazz } from '../shared/models/IClazz';
import { toDate } from '../shared/utils/DateUtil';

function ClazzInfo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const [clazz, setClazz] = useState<IClazz | null>(null);

  useEffect(() => { getClazz(id!).then(setClazz); }, [id]);

  const handleDelete = () => {
    if (!user) {
      navigate('/login');
      return;
    }
    const isDeleted = window.confirm(`Are you sure you want to delete the Class [${clazz?.code}]`);
    if (isDeleted && id) deleteClazz(id, user.accessToken).then(_ => navigate(-1));
  }

  if (!clazz) return <Loader />
  const teacher = clazz.students.find(current => current.id === clazz.teacher);
  return (
    <section className="p-4 bg-white rounded-md">
      <FormHeader
        title="Class Info"
        actions={[
          <FormActionEdit path={`/classes/${clazz.id}/update`} />,
          <FormActionDelete handleDelete={handleDelete} />
        ]}
      />
      <div className="w-fit p-1 bg-sky-50 rounded-md border border-sky-300">
        <div className={clazz.type ? clazz.type : 'w-[48px] h-[48px]'}></div>
      </div>
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-3 px-2 mt-4 text-sm font-medium">
        <label>Code:<span className="block text-base font-light">{clazz.code}</span></label>
        <label>Title:<span className="block text-base font-light">{clazz.title}</span></label>
        <label>Enable:<span className="block text-base font-light">{clazz.enable ? 'True' : 'False'}</span></label>
        <label>Teacher:<span className="block text-base font-light">{`${teacher?.name || ''} ${teacher?.lastName || ''}`}</span></label>
        <label>Created Date:<span className="block text-base font-light">{toDate(clazz.createdAt)}</span></label>
        <label>Last Update:<span className="block text-base font-light">{toDate(clazz.updatedAt)}</span></label>
        <label className="md:col-span-2 lg:col-span-3">Description:<span className="block text-base font-light">{clazz.description}</span></label>
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
                  {`${current?.name || ''} ${current?.lastName || ''}`}
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
