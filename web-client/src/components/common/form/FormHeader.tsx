import { cloneElement } from 'react';
import FormActionBack from './FormActionBack';

export default function FormHeader(props: { title: string, actions: JSX.Element[] }) {
  return (
    <section className="flex items-center gap-2 pb-4 mb-4 border-b border-gray-200">
      <FormActionBack />
      <h2 className="flex-1 text-xl ">{props.title}</h2>
      {props.actions.map((action, index) => cloneElement(action, { key: index }))}
    </section>
  );
}
