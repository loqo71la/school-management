import { IClazz } from '../models/IClazz';
import { IPageable } from '../models/IPageable';
import { IStudent } from '../models/IStudent';

export function newClazz(): IClazz {
  return { id: '', code: '', title: '', description: '', enable: true, students: [] as IStudent[] } as IClazz;
}

export function newStudent(): IStudent {
  return { idNo: '', name: '', lastName: '', genderId: 0 } as IStudent;
}

export function newPage<T>(): IPageable<T> {
  return { currentPage: 1, totalPages: 1, totalItems: 0, items: [] };
}
