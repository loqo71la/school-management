import { IStudent } from '../shared/models/IStudent';

const headers = { "Content-Type": "application/json" };

export function getStudents() {
  return doRequest({ method: 'GET' });
}

export function getStudent(idNo: string) {
  return doRequest({ method: 'GET' }, `/${idNo}`);
}

export function addStudent(student: IStudent) {
  return doRequest({ method: 'POST', body: JSON.stringify(student), headers })
}

export function updateStudent(idNo: string, student: IStudent) {
  return doRequest({ method: 'PUT', body: JSON.stringify(student), headers }, `/${idNo}`);
}

export function deleteStudent(idNo: string) {
  return doRequest({ method: 'DELETE' }, `/${idNo}`);
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${process.env.REACT_APP_SM_API_URL}/api/students${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => alert(error.message));
}