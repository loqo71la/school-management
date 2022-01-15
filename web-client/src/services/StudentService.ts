import * as config from '../App.config.json';
import { IStudent } from '../shared/models/IStudent';

export function getStudents() {
  return doRequest({ method: 'GET' });
}

export function getStudent(idNo: string) {
  return doRequest({ method: 'GET' }, `/${idNo}`);
}

export function addStudent(student: IStudent) {
  return doRequest({ method: 'POST', body: JSON.stringify(student), headers: config.headers })
}

export function updateStudent(idNo: string, student: IStudent) {
  return doRequest({ method: 'PUT', body: JSON.stringify(student), headers: config.headers }, `/${idNo}`);
}

export function deleteStudent(idNo: string) {
  return doRequest({ method: 'DELETE' }, `/${idNo}`);
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${config.baseUrl}/api/students${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => alert(error.message));
}