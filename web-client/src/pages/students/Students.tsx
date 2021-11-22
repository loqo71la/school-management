import { useEffect, useState } from "react";
import List from "../../components/list/List";
import { IPageable } from "../../shared/models/IPageable";
import { IStudent } from "../../shared/models/IStudent";

function Students() {
  const [studentPage, setStudentPage] = useState<IPageable<IStudent> | null>(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/students')
      .then(response => response.json())
      .then(data => setStudentPage(data))
  }, []);

  if (!studentPage) return <div>Loading...</div>
  return (
    <div>
      {studentPage.items.map(student => <List key={student.idNo} student={student} />)}
    </div>
  );
}

export default Students;