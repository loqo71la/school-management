export interface IPageable<T> {
  currentPage: number;
  totalPage: number;
  totalItem: number;
  items: T[]
}