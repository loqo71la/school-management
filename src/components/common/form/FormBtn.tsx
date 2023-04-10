import { ReactNode } from 'react';
import { useNavigate } from 'react-router-dom';
import { ChevronLeft, CloudCheckFill, PencilFill, Trash2Fill, XCircleFill } from '@loqo71la/react-web-icons';

const style = {
  circle: 'p-2 rounded-full border',
  back: 'border-gray-100 hover:bg-gray-200',
  delete: 'text-red-600 bg-red-100 border-red-600 hover:text-white hover:bg-red-600',
  edit: 'text-sky-600 bg-sky-100 border-sky-600 hover:text-white hover:bg-sky-600',
  text: 'flex items-center px-5 py-2.5 rounded-full font-medium text-sm',
  cancel: 'text-white bg-gradient-to-r from-red-500 to-red-600 hover:bg-gradient-to-br',
  save: 'text-white bg-gradient-to-r from-sky-500 to-sky-600 hover:bg-gradient-to-br'
}

type BtnProps = {
  title: string;
  style: string;
  icon: ReactNode;
  onClick: () => void;
}

function BackBtn() {
  const navigate = useNavigate();
  return CircleBtn({ title: 'Back', style: style.back, onClick: () => navigate(-1), icon: <ChevronLeft className="w-4 h-4" /> })
}

function DeleteBtn(props: { onClick: () => void }) {
  return CircleBtn({ title: 'Delete', style: style.delete, onClick: props.onClick, icon: <Trash2Fill className="w-4 h-4" /> })
}

function EditBtn(props: { onClick: () => void }) {
  return CircleBtn({ title: 'Edit', style: style.edit, onClick: props.onClick, icon: <PencilFill className="w-4 h-4" /> })
}

function CancelBtn() {
  const navigate = useNavigate();
  return TextBtn({ title: 'Cancel', style: style.cancel, onClick: () => navigate(-1), icon: <XCircleFill className="w-4 h-4" /> })
}

function SaveBtn(props: { onClick: () => void, isDisabled: boolean }) {
  return TextBtn({ title: 'Save', style: style.save, onClick: props.onClick, isDisabled: props.isDisabled, icon: <CloudCheckFill className="w-5 h-5" /> })
}

function TextBtn(props: BtnProps & { isDisabled?: boolean }) {
  return (
    <button
      type="button"
      title={props.title}
      onClick={props.onClick}
      disabled={props.isDisabled}
      className={`${style.text} ${props.style} ${props.isDisabled ? 'opacity-75' : ''}`}
    >
      {props.icon}
      <span className="ml-2">{props.title}</span>
    </button>
  );
}

function CircleBtn(props: BtnProps) {
  return (
    <button
      type="button"
      title={props.title}
      onClick={props.onClick}
      className={`${style.circle} ${props.style}`}
    >
      {props.icon}
    </button>
  );
}

export { BackBtn, DeleteBtn, EditBtn, CancelBtn, SaveBtn };