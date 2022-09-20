export interface IForm {
  children: JSX.Element | JSX.Element[];
  onDelete: () => void;
  onSubmit: () => void;
  onCancel: () => void;
  disabled: boolean;
  title: string;
}