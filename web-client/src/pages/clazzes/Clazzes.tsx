import { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { IClazz } from "../../shared/models/IClazz";
import { IPageable } from "../../shared/models/IPageable";

function Clazzes() {
  const [clazzPage, setClazzPage] = useState<IPageable<IClazz> | null>(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/classes')
      .then(response => response.json())
      .then(data => setClazzPage(data))
  }, []);

  if (!clazzPage) return <div>Loading...</div>
  return (
    <div>
      {clazzPage.items.map(clazz => <Card key={clazz.code} clazz={clazz} />)}
    </div>
  );
}

export default Clazzes;