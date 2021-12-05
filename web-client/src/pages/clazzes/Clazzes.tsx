import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import './Clazzes.css';

import Card from "../../components/card/Card";
import { IClazz } from "../../shared/models/IClazz";
import { IPageable } from "../../shared/models/IPageable";
import { getClazzes } from "../../services/ClazzService";

function Clazzes() {
  const [clazzPage, setClazzPage] = useState<IPageable<IClazz> | null>(null);
  const navigate = useNavigate();

  useEffect(() => { getClazzes().then(data => setClazzPage(data)) }, []);

  if (!clazzPage) return <div>Loading...</div>
  return (
    <>
      <section className="clazz-header">
        Viewing {clazzPage.totalItem / clazzPage.totalPage} from {clazzPage.totalItem} Class(es)
        <input type="button" value="Add" className="main" onClick={() => navigate('/classes/newClass')} />
      </section>
      <section className="clazz-container">
        {clazzPage.items.map(clazz => <Card key={clazz.code} clazz={clazz} />)}
      </section>
    </>
  );
}

export default Clazzes;