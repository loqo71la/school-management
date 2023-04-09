import { location } from '../../config';
import { IClazz } from '../models/IClazz';
import { IPageable } from '../models/IPageable';
import { IStudent } from '../models/IStudent';

const { latitude, longitude } = location;

export function newClazz(): IClazz {
  return { id: '', code: '', title: '', description: '', enable: true, students: [] as IStudent[], latitude, longitude } as IClazz;
}

export function newStudent(): IStudent {
  return { idNo: '', firstName: '', lastName: '', gender: 0, latitude, longitude } as IStudent;
}

export function newPage<T>(): IPageable<T> {
  return { currentPage: 1, totalPages: 1, totalItems: 0, items: [] };
}
