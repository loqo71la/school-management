import { Trash2Fill } from '@loqo71la/react-web-icons';

function FormActionDelete(props: { handleDelete: () => void }) {
  return (
    <button
      type="button"
      title="Delete"
      onClick={props.handleDelete}
      className="p-2 rounded-full text-red-600 bg-red-100 border border-red-600 hover:text-white hover:bg-red-600"
    >
      <Trash2Fill className="w-4 h-4" />
    </button>
  );
}

export default FormActionDelete;
