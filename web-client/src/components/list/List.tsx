import { IStudent } from "../../shared/models/IStudent";

function List({ student }: { student: IStudent }) {
  return (
    <section>
      <h2>{student.firstName} {student.lastName}</h2>
      <p>{student.idNo}</p>
    </section>
  );
}

export default List;