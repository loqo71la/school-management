import { icons } from "../../App.config";

function Loader() {
  return (
    <div className="h-full flex items-center justify-center">
      <svg
        className="animate-spin h-8 w-8 text-teal-600"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        fill="none"
      >
        <circle
          className="opacity-25"
          stroke="currentColor"
          cx="12"
          cy="12"
          r="10"
        />
        <path
          className="opacity-75"
          fill="currentColor"
          d={icons.spinner}
        />
      </svg>
    </div>
  );
}

export default Loader;
