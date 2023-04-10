import { api } from '../config';
import { IClazz } from '../shared/models/IClazz';
import { IPageable } from '../shared/models/IPageable';
import { IStudent } from '../shared/models/IStudent';
import { doRequest, loadHeaders } from '../shared/utils/HttpUtil';

export function getClazzes(page: number = 1, limit: number = api.size, sort: string = api.sortBy): Promise<IPageable<IClazz>> {
  return doRequest({ method: 'GET' }, `/clazz?limit=${limit}&page=${page}&sort=${sort}`);
}

export function getStudentsByClazz(code: string, page: number = 1, limit: number = api.size, sort: string = api.sortBy): Promise<IPageable<IStudent>> {
  return doRequest({ method: 'GET' }, `/clazz/${code}/student?limit=${limit}&page=${page}&sort=${sort}`);
}

export function getClazz(code: string) {
  return doRequest({ method: 'GET' }, `/clazz/${code}`);
}

export function addClazz(clazz: IClazz) {
  return doRequest({ method: 'POST', body: JSON.stringify(clazz), headers: loadHeaders() }, '/clazz');
}

export function updateClazz(code: string, clazz: IClazz) {
  return doRequest({ method: 'PUT', body: JSON.stringify(clazz), headers: loadHeaders() }, `/clazz/${code}`);
}

export function deleteClazz(code: string) {
  return doRequest({ method: 'DELETE', headers: loadHeaders() }, `/clazz/${code}`);
}

export function assignStudents(code: string, studentIds: string[], teacherId?: string) {
  return doRequest({ method: 'POST', body: JSON.stringify({ teacherId, studentIds }), headers: loadHeaders() }, `/clazz/${code}/assign`)
}
