import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { IClazz } from '../shared/models/IClazz';
import { IPageable } from '../shared/models/IPageable';
import { IRequest } from '../shared/models/IData';
import { getClazzes } from '../services/ClazzService';
import Card from '../components/landing/Card';
import Loader from '../components/common/Loader';
import Pagination from '../components/common/Pagination';
import { icons } from '../App.config';

function Clazzes() {
  const [request, setRequest] = useState<IRequest<IPageable<IClazz>>>({ isLoading: true });
  const navigate = useNavigate();

  const loadClazzes = (page: number = 0) => {
    setRequest({ isLoading: true });
    getClazzes(page).then(data => setRequest({ isLoading: false, data: data }));
  }

  useEffect(() => { loadClazzes() }, []);

  if (request.isLoading) return <Loader />
  return (
    <>
      <section className="flex flex-col sm:flex-row justify-between items-center gap-2">
        <p className="font-light text-lg">Viewing {request.data?.items.length} from {request.data?.totalItem} Class(es)</p>
        <button
          type="button"
          onClick={() => navigate('/classes/create')}
          className="flex px-5 py-2.5 font-medium text-sm text-white bg-gradient-to-r from-teal-500 via-teal-600 to-teal-700 hover:bg-gradient-to-br focus:outline-none rounded-full"
        >
          <svg
            className="h-5 w-5 mr-2"
            viewBox="0 0 20 20"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d={icons.pencil} />
            <path d={icons.add} />
          </svg>
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
        pageable={request.data!}
        onSelected={loadClazzes}
      />
    </>
  );
}

export default Clazzes;
