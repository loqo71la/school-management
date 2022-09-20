import { CloudCheckFill } from '@loqo71la/react-web-icons';

function FormSave(props: { handleSave: () => void, isDisabled: boolean }) {
  return (
    <button
      type="button"
      title="Save"
      disabled={props.isDisabled}
      onClick={props.handleSave}
      className={`flex items-center px-5 py-2.5 rounded-full font-medium text-sm text-white ${props.isDisabled ? 'bg-sky-400' : 'bg-gradient-to-r from-sky-500 to-sky-600 hover:bg-gradient-to-br'}`}
    >
      <CloudCheckFill className="w-5 h-5" />
      <span className="ml-2">Save</span>
    </button>
  );
}

export default FormSave;
