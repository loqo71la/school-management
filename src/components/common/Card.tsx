import { PersonFill, ToggleOff, ToggleOn } from '@loqo71la/react-web-icons';
import { useNavigate } from 'react-router';

import { IClazz } from '../../shared/models/IClazz';
import { toDate } from '../../shared/utils/DateUtil';

function Card({ clazz }: { clazz: IClazz }) {
  const navigate = useNavigate();

  return (
    <section
      onClick={() => navigate(`/classes/${clazz.id}`)}
      className="flex flex-col gap-3 py-3 px-4 bg-white rounded-lg hover:shadow-md hover:bg-gray-50"
    >
      <div className="flex justify-between">
        <section className="flex gap-2">
          <div className="p-1 bg-sky-100 rounded-md border border-sky-300">
            <div className={clazz.type ? clazz.type : 'w-[48px] h-[48px]'} />
          </div>
          <h2 className="font-medium text-lg">
            {clazz.title}
            <span className="block text-xs text-gray-600">@{clazz.code}</span>
          </h2>
        </section>
        <div className="flex items-start gap-2">
          {clazz.enable ? <ToggleOn className="w-7 h-7 fill-sky-600" /> : <ToggleOff className="w-7 h-7 fill-sky-600" />}
          <div className="flex gap-0.5 font-light mt-1">
            <PersonFill className="w-5 h-5 fill-gray-600" />
            {clazz.totalAssigned}
          </div>
        </div>

      </div>
      <p className="flex-1 font-light">{clazz.description}</p>
      <p className="text-sm font-semibold">Last update<span className="font-normal ml-1">{toDate(clazz.updatedAt)}</span></p>
    </section>
  );
}

export default Card;