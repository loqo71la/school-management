import { api } from '../config';
import { IPageable } from '../shared/models/IPageable';
import { IStudent } from '../shared/models/IStudent';
import { doRequest, loadHeaders } from '../shared/utils/HttpUtil';

export function getStudents(page: number = 1, limit: number = api.size, sortBy: string = api.sortBy): Promise<IPageable<IStudent>> {
  return doRequest({ method: 'GET' }, `/student?limit=${limit}&page=${page}&sort=${sortBy}`);
}

export function getStudent(idNo: string) {
  return doRequest({ method: 'GET' }, `/student/${idNo}`);
}

export function addStudent(student: IStudent) {
  return doRequest({ method: 'POST', body: JSON.stringify(student), headers: loadHeaders() }, '/student');
}

export function updateStudent(idNo: string, student: IStudent) {
  return doRequest({ method: 'PUT', body: JSON.stringify(student), headers: loadHeaders() }, `/student/${idNo}`);
}

export function deleteStudent(idNo: string) {
  return doRequest({ method: 'DELETE', headers: loadHeaders() }, `/student/${idNo}`);
}
