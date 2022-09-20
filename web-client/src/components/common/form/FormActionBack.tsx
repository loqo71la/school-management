import { ChevronLeft } from '@loqo71la/react-web-icons';
import { useNavigate } from 'react-router-dom';

function FormActionBack() {
  const navigate = useNavigate();

  return (
    <button
      type="button"
      title="Back"
      onClick={() => navigate(-1)}
      className="p-2 rounded-full border border-gray-100 hover:bg-gray-200"
    >
      <ChevronLeft className="w-4 h-4" />
    </button>
  );
}

export default FormActionBack;
