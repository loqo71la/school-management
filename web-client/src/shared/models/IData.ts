export interface IRequest<T> {
  isLoading: boolean;
  error?: Error;
  data?: T;
}
