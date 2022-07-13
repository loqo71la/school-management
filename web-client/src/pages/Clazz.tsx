import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import Form from '../components/landing/Form';
import InputText from '../components/common/InputText';
import Loader from '../components/common/Loader';

import { addClazz, deleteClazz, getClazz, updateClazz } from '../services/ClazzService';
import { IClazz } from '../shared/models/IClazz';
import { newClazz } from '../shared/utils/ClazzUtil';

function Clazz() {
  const { code } = useParams();
  const navigate = useNavigate();
  const [clazz, setClazz] = useState<IClazz | null>(null);

  useEffect(() => {
    if (code === 'create') setClazz(newClazz());
    else getClazz(code!).then(data => setClazz(data));
  }, [code]);

  const handleSave = () => {
    const savedClazz = code !== 'create' ? updateClazz(clazz!.code, clazz!) : addClazz(clazz!);
    savedClazz.then(_ => navigate('/classes'));
  }

  const handleDelete = () => {
    const isDeleted = window.confirm(`Are you sure you want to delete the Class [${code}]`);
    if (isDeleted && code) deleteClazz(code).then(_ => navigate('/classes'));
  }

  if (!clazz) return <Loader />
  return (
    <Form
      title={`${code !== 'create' ? 'Update' : 'Create'} Class`}
      onCancel={() => navigate('/classes')}
      disabled={clazz.code === ''}
      onDelete={handleDelete}
      onSubmit={handleSave}
    >
      <>
        <InputText
          label="Code"
          required={true}
          value={clazz.code}
          disabled={code !== 'create'}
          onChange={value => setClazz({ ...clazz, code: value })}
        />
        <InputText
          label="Title"
          value={clazz.title}
          onChange={value => setClazz({ ...clazz, title: value })}
        />
        <label>
          Description
          <textarea
            rows={4}
            cols={50}
            value={clazz.description}
            onChange={event => setClazz({ ...clazz, description: event.target.value })}
            className="block w-full mb-2 p-1 text-md border border-gray-300 text-gray-700 focus:outline-teal-400"
          />
        </label>
        <label>
          Enable
          <button
            type="button"
            onClick={() => setClazz({ ...clazz, enable: !clazz.enable })}
            className={`h-5 w-10 ml-3 ${clazz.enable ? 'bg-teal-600' : 'bg-gray-300'} relative inline-flex  items-center rounded-full`}
          >
            <span className={`h-3 w-3 ${clazz.enable ? 'translate-x-6' : 'translate-x-1'} inline-block transform rounded-full bg-white`}></span>
          </button>
        </label>
      </>
    </Form>
  );
}

export default Clazz;
