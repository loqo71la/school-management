import { useState } from 'react';
import { IInput } from '../../shared/models/IInput';

function InputText(props: IInput) {
  const [touched, setTouched] = useState(false);
  const isInvalid = () => props.required && !props.value && touched;
  return (
    <label className="block mb-3 text-sm">
      {props.label}{props.required && ' *'}
      <input
        type="text"
        autoComplete="off"
        value={props.value}
        disabled={props.disabled}
        onBlur={() => setTouched(true)}
        onChange={event => props.onChange(event.target.value)}
        className={`w-full py-1 px-2 text-base font-light rounded-md border ${isInvalid() ? 'border-red-600' : 'border-gray-300'} text-gray-700 focus:outline-sky-400`}
      />
      {isInvalid() &&
        <span className="text-sm text-red-600">Please provide a valid value</span>
      }
    </label>
  );
}

export default InputText;
