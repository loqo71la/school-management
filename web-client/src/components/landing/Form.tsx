import { icons } from '../../App.config';
import { IForm } from '../../shared/models/IForm';

function Form(props: IForm) {
  return (
    <form
      onSubmit={e => e.preventDefault()}
      className="p-4 bg-white rounded-lg"
    >
      <section className="flex items-center gap-2 mb-4">
        <button
          type="button"
          onClick={props.onCancel}
          className="p-2 rounded-full border border-gray-100 hover:bg-gray-200"
        >
          <svg
            className="w-4 h-4"
            fill="currentColor"
            viewBox="0 0 20 20"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              d={icons.left}
            />
          </svg>
        </button>
        <h2 className="flex-1 text-xl ">{props.title}</h2>
        <button
          type="button"
          onClick={props.onDelete}
          className="p-2 rounded-full text-red-600 bg-red-100 border border-red-600 hover:text-white hover:bg-red-600"
        >
          <svg
            className="w-4 h-4"
            viewBox="0 0 16 16"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d={icons.trash} />
          </svg>
        </button>
      </section>
      {props.children}
      <hr className="my-4" />
      <div className="flex justify-center gap-2">
        <button
          type="button"
          disabled={props.disabled}
          onClick={props.onSubmit}
          className={`flex items-center px-5 py-2.5 rounded-full font-medium text-sm text-white ${props.disabled ? 'bg-teal-400' : 'bg-gradient-to-r from-teal-500 via-teal-600 to-teal-700 hover:bg-gradient-to-br'}`}
        >
          <svg
            className="w-5 h-5"
            viewBox="0 0 16 16"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d={icons.save} />
          </svg>
          <span className="ml-2">Save</span>
        </button>
        <button
          type="button"
          onClick={props.onCancel}
          className="flex items-center px-5 py-2.5 rounded-full font-medium text-sm text-white bg-gradient-to-r from-red-500 via-red-600 to-red-700 hover:bg-gradient-to-br"
        >
          <svg
            className="w-4 h-4"
            viewBox="0 0 16 16"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d={icons.cancel} />
          </svg>
          <span className="ml-2">Cancel</span>
        </button>
      </div >
    </form >
  );
}

export default Form;
