import { IClazz } from "../../shared/models/IClazz";

function Card({ clazz }: { clazz: IClazz }) {
  return (
    <section>
      <h2>{clazz.title} <span>{clazz.code}</span></h2>
      <p>{clazz.description}</p>
    </section>
  );
}

export default Card;