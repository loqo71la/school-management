import { XCircleFill } from '@loqo71la/react-web-icons';
import { useNavigate } from 'react-router-dom';

function FormCancel() {
  const navigate = useNavigate();

  return (
    <button
      type="button"
      title="Cancel"
      onClick={() => navigate(-1)}
      className="flex items-center px-5 py-2.5 rounded-full font-medium text-sm text-white bg-gradient-to-r from-red-500 to-red-600 hover:bg-gradient-to-br"
    >
      <XCircleFill className="w-4 h-4" />
      <span className="ml-2">Cancel</span>
    </button>
  );
}

export default FormCancel;
