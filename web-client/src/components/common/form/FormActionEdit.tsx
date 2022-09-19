import { PencilFill } from '@loqo71la/react-web-icons';
import { useNavigate } from 'react-router-dom';

function FormActionEdit(props: {path: string}) {
  const navigate = useNavigate();

  return (
    <button
      type="button"
      title="Edit"
      onClick={() => navigate(props.path)}
      className="p-2 rounded-full text-sky-600 bg-sky-100 border border-sky-600 hover:text-white hover:bg-sky-600"
    >
      <PencilFill className="w-4 h-4" />
    </button>
  );
}

export default FormActionEdit;
