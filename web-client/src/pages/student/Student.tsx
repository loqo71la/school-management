import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { IStudent } from "../../shared/models/IStudent";

function Student() {
  const params = useParams();
  const [student, setStudent] = useState<IStudent | null>(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/students/${params.noId}`)
      .then(response => response.json())
      .then(data => setStudent(data))
  }, [params]);

  const handleChange = (event: any) => {
    const { name, value } = event.target;
    setStudent({ ...student, [name]: name === 'gender' ? value === 'Male' : value } as IStudent);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    console.log(student);
  }

  if (!student) return <div>Loading...</div>
  return (
    <form onSubmit={handleSubmit}>
      <label>
        First Name:
        <input type="text" name="firstName" value={student.firstName} onChange={handleChange} />
      </label>
      <label>
        Last Name:
        <input type="text" name="lastName" value={student.lastName} onChange={handleChange} />
      </label>
      <section>
        Gender:
        <label>
          Male
          <input type="radio" value="Male" name="gender" checked={student.gender} onChange={handleChange} />
        </label>
        <label>
          Female
          <input type="radio" value="Female" name="gender" checked={!student.gender} onChange={handleChange} />
        </label>
      </section>
      <input type="submit" value="Submit" />
    </form>
  );
}

export default Student;