import { useNavigate } from "react-router";
import { IStudent } from "../../shared/models/IStudent";

function List({ student }: { student: IStudent }) {
  let navigate = useNavigate();

  return (
    <section onClick={() => navigate(`/students/${student.idNo}`)}>
      <h2>{student.firstName} {student.lastName}</h2>
      <p>{student.idNo}</p>
    </section>
  );
}

export default List;