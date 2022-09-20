export interface IPageable<T> {
  currentPage: number;
  totalPages: number;
  totalItems: number;
  items: T[]
}
