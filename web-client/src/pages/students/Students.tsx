import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import './Students.css';

import { getStudents } from "../../services/StudentService";
import { IPageable } from "../../shared/models/IPageable";
import { IStudent } from "../../shared/models/IStudent";
import List from "../../components/list/List";

function Students() {
  const [studentPage, setStudentPage] = useState<IPageable<IStudent> | null>(null);
  const navigate = useNavigate();

  useEffect(() => { getStudents().then(data => setStudentPage(data)) }, []);

  if (!studentPage) return <div>Loading...</div>
  return (
    <>
      <section className="student-header">
        Viewing {studentPage.totalItem / studentPage.totalPage} from {studentPage.totalItem} Student(s)
        <input type="button" value="Add" className="main" onClick={() => navigate('/students/newStudent')} />
      </section>
      <section className="student-container">
        {studentPage.items.map(student => <List key={student.idNo} student={student} />)}
      </section>
    </>
  );
}

export default Students;