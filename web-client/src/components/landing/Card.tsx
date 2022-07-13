import { useNavigate } from 'react-router';

import { IClazz } from '../../shared/models/IClazz';

function Card({ clazz }: { clazz: IClazz }) {
  const date = new Date(clazz.modifiedDate ?? clazz.createdDate);
  const navigate = useNavigate();
  return (
    <section
      onClick={() => navigate(`/classes/${clazz.code}`)}
      className="flex flex-col gap-3 py-3 px-4 bg-white rounded-lg shadow-md transition hover:-translate-y-1 hover:bg-gray-50"
    >
      <div className="flex justify-between">
        <h2 className="font-bold text-lg">
          {clazz.title}
          <span className="block text-gray-500 text-xs">@{clazz.code}</span>
        </h2>
        <button
          disabled
          type="button"
          className={`h-5 w-10 ${clazz.enable ? 'bg-teal-600' : 'bg-gray-300'} relative inline-flex  items-center rounded-full`}
        >
          <span className={`h-3 w-3 ${clazz.enable ? 'translate-x-6' : 'translate-x-1' } inline-block transform rounded-full bg-white`}></span>
        </button>
      </div>
      <p className="flex-1 font-light">{clazz.description}</p>
      <p className="text-xs font-semibold">Last update<span className="font-normal ml-1">{date.toDateString()}</span></p>
    </section>
  );
}

export default Card;