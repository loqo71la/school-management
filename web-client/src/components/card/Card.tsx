import { useNavigate } from "react-router";
import './Card.css';

import { IClazz } from "../../shared/models/IClazz";

function Card({ clazz }: { clazz: IClazz }) {
  const navigate = useNavigate();
  return (
    <section className="card" onClick={() => navigate(`/classes/${clazz.code}`)}>
      <h2>{clazz.title} <span>{clazz.code}</span></h2>
      <p>{clazz.description}</p>
    </section>
  );
}

export default Card;