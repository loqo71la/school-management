import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { IClazz } from "../../shared/models/IClazz";

function Clazz() {
  const params = useParams();
  const [clazz, setClazz] = useState<IClazz | null>(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/classes/${params.code}`)
      .then(response => response.json())
      .then(data => setClazz(data))
  }, [params]);

  const handleChange = (event: any) => {
    const { name, value, checked } = event.target;
    setClazz({ ...clazz, [name]: name === 'enable' ? checked : value } as IClazz);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    console.log(clazz);
  }

  if (!clazz) return <div>Loading...</div>
  return (
    <form onSubmit={handleSubmit}>
      <label>
        Title:
        <input type="text" name="title" value={clazz.title} onChange={handleChange} />
      </label>
      <label>
        Description:
        <textarea name="description" value={clazz.description} onChange={handleChange} />
      </label>
      <label>
        Male
        <input type="checkbox" name="enable" checked={clazz.enable} onChange={handleChange} />
      </label>
      <input type="submit" value="Submit" />
    </form>
  );
}

export default Clazz;