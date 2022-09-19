import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { IClazz } from '../shared/models/IClazz';
import { IPageable } from '../shared/models/IPageable';
import { IRequest } from '../shared/models/IData';
import { getClazzes } from '../services/ClazzService';
import Card from '../components/common/Card';
import Loader from '../components/common/Loader';
import Pagination from '../components/common/Pagination';
import { PencilSquare } from '@loqo71la/react-web-icons';

function ClazzList() {
  const [request, setRequest] = useState<IRequest<IPageable<IClazz>>>({ isLoading: true });
  const navigate = useNavigate();

  const loadClazzes = (page: number = 1) => {
    setRequest({ isLoading: true });
    getClazzes(page).then(data => setRequest({ isLoading: false, data: data }));
  }

  useEffect(() => { loadClazzes() }, []);

  if (request.isLoading) return <Loader />
  return (
    <>
      <section className="flex flex-col sm:flex-row justify-between items-center gap-2">
        <p className="font-light">Viewing {request.data?.items.length} from {request.data?.totalItems} Class(es)</p>
        <button
          type="button"
          onClick={() => navigate('/classes/create')}
          className="flex gap-3 items-center px-5 py-2 text-sm text-white bg-gradient-to-r from-sky-500 to-sky-700 hover:bg-gradient-to-br focus:outline-none rounded-full"
        >
          <PencilSquare className="w-4 h-4" />
          Add Class
        </button>
      </section>
      <section className="mt-4 grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-3">
        {request.data?.items.map(clazz =>
          <Card
            key={clazz.code}
            clazz={clazz}
          />)
        }
      </section>
      <Pagination
        className="text-center mt-2"
        pageable={request.data!}
        onSelected={loadClazzes}
      />
    </>
  );
}

export default ClazzList;
