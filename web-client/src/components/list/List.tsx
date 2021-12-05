import { useNavigate } from "react-router";
import './List.css';

import { IStudent } from "../../shared/models/IStudent";

function List({ student }: { student: IStudent }) {
  let navigate = useNavigate();
  return (
    <section className="list" onClick={() => navigate(`/students/${student.idNo}`)}>
      <h2><span>{student.idNo}</span> {student.firstName} {student.lastName}</h2>
    </section>
  );
}

export default List;