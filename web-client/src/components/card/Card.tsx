import { useNavigate } from "react-router";
import { IClazz } from "../../shared/models/IClazz";

function Card({ clazz }: { clazz: IClazz }) {
  let navigate = useNavigate();
  return (
    <section onClick={() => navigate(`/classes/${clazz.code}`)}>
      <h2>{clazz.title} <span>{clazz.code}</span></h2>
      <p>{clazz.description}</p>
    </section>
  );
}

export default Card;