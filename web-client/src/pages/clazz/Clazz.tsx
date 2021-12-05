import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import './Clazz.css';

import { addClazz, deleteClazz, getClazz, updateClazz } from "../../services/ClazzService";
import { IClazz } from "../../shared/models/IClazz";
import { newClazz } from "../../shared/utils/ClazzUtil";

function Clazz() {
  const { code } = useParams();
  const navigate = useNavigate();
  const [clazz, setClazz] = useState<IClazz>(newClazz());
  const [editable, setEditable] = useState<boolean>(code === undefined);

  useEffect(() => { if (code) getClazz(code).then(data => setClazz(data)); }, [code]);

  const handleChange = (event: any) => {
    const { name, value, checked } = event.target;
    setClazz({ ...clazz, [name]: name === 'enable' ? checked : value } as IClazz);
  }

  const handleSave = () => {
    const savedClazz = code ? updateClazz(code, clazz) : addClazz(clazz);
    savedClazz.then(_ => navigate('/classes'));
  }

  const handleEdit = () => {
    setEditable(true);
  }

  const handleDelete = () => {
    const isDeleted = window.confirm(`Are you sure you want to delete the Class [${code}]`);
    if (isDeleted && code) deleteClazz(code).then(_ => navigate('/classes'));
  }

  if (!clazz) return <div>Loading...</div>
  return (
    <form className="clazzForm" onSubmit={e => e.preventDefault()}>
      {code && (
        <section>
          <input type="submit" value="Edit" className="main" onClick={handleEdit} />
          <input type="submit" value="Delete" className="error" onClick={handleDelete} />
        </section>
      )}
      <label>
        Code:
        <input type="text" name="code" value={clazz.code} onChange={handleChange} disabled={!editable || code !== undefined} />
      </label>
      <label>
        Title:
        <input type="text" name="title" value={clazz.title} onChange={handleChange} disabled={!editable} />
      </label>
      <label>
        Description:
        <textarea name="description" rows={4} cols={50} value={clazz.description} onChange={handleChange} disabled={!editable} />
      </label>
      <label>
        Enable
        <input type="checkbox" name="enable" checked={clazz.enable} onChange={handleChange} disabled={!editable} />
      </label>
      {editable && <input type="submit" value="Save" className="success" onClick={handleSave} />}
    </form>
  );
}

export default Clazz;