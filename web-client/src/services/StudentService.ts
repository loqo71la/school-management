import { api } from '../App.config';
import { IPageable } from '../shared/models/IPageable';
import { IStudent } from '../shared/models/IStudent';

export function getStudents(page: number = 0, limit: number = api.size): Promise<IPageable<IStudent>> {
  return doRequest({ method: 'GET' }, `?limit=${limit}&page=${page}`);
}

export function getStudent(idNo: string) {
  return doRequest({ method: 'GET' }, `/${idNo}`);
}

export function addStudent(student: IStudent, token: string) {
  return doRequest({ method: 'POST', body: JSON.stringify(student), headers: loadHeaders(token) });
}

export function updateStudent(idNo: string, student: IStudent, token: string) {
  return doRequest({ method: 'PUT', body: JSON.stringify(student), headers: loadHeaders(token) }, `/${idNo}`);
}

export function deleteStudent(idNo: string, token: string) {
  return doRequest({ method: 'DELETE', headers: loadHeaders(token) }, `/${idNo}`);
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${api.url}/student${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => {
      alert(error.message);
      return Promise.reject(error);
    });
}

function loadHeaders(token: string) {
  return {
    "Authorization": `Bearer ${token}`,
    "Content-Type": "application/json"
  };
}
