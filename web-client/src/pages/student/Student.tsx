import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import './Student.css';

import { IStudent } from "../../shared/models/IStudent";
import { newStudent } from "../../shared/utils/StudentUtil";
import { addStudent, deleteStudent, getStudent, updateStudent } from "../../services/StudentService";

function Student() {
  const { idNo } = useParams();
  const navigate = useNavigate();
  const [student, setStudent] = useState<IStudent>(newStudent());
  const [editable, setEditable] = useState<boolean>(idNo === undefined);

  useEffect(() => { if (idNo) getStudent(idNo).then(data => setStudent(data)); }, [idNo]);

  const handleChange = (event: any) => {
    const { name, value } = event.target;
    setStudent({ ...student, [name]: name === 'gender' ? value === 'Male' : value } as IStudent);
  }

  const handleSave = () => {
    const savedClazz = idNo ? updateStudent(idNo, student) : addStudent(student);
    savedClazz.then(_ => navigate('/students'));
  }

  const handleEdit = () => {
    setEditable(true);
  }

  const handleDelete = () => {
    const isDeleted = window.confirm(`Are you sure you want to delete the Student [${idNo}]`);
    if (isDeleted && idNo) deleteStudent(idNo).then(_ => navigate('/students'));
  }

  if (!student) return <div>Loading...</div>
  return (
    <form className="studentForm" onSubmit={e => e.preventDefault()}>
      {idNo && (
        <section>
          <input type="submit" value="Edit" className="main" onClick={handleEdit} />
          <input type="submit" value="Delete" className="error" onClick={handleDelete} />
        </section>
      )}
      <label>
        Id No:
        <input type="text" name="idNo" value={student.idNo} onChange={handleChange} disabled={!editable || idNo !== undefined} />
      </label>
      <label>
        First Name:
        <input type="text" name="firstName" value={student.firstName} onChange={handleChange} disabled={!editable} />
      </label>
      <label>
        Last Name:
        <input type="text" name="lastName" value={student.lastName} onChange={handleChange} disabled={!editable} />
      </label>
      <div className="gender">
        Gender:
        <label>
          Male
          <input type="radio" value="Male" name="gender" checked={student.gender} onChange={handleChange} disabled={!editable} />
        </label>
        <label>
          Female
          <input type="radio" value="Female" name="gender" checked={!student.gender} onChange={handleChange} disabled={!editable} />
        </label>
      </div>
      {editable && <input type="submit" value="Save" className="success" onClick={handleSave} />}
    </form>
  );
}

export default Student;