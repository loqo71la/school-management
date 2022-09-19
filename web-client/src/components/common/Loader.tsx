import { Spinner } from '@loqo71la/react-web-icons';

function Loader() {
  return (
    <div className="h-full flex items-center justify-center">
      <Spinner className="animate-spin h-8 w-8 text-sky-600" />
    </div>
  );
}

export default Loader;
