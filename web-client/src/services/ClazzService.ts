import { api } from '../App.config';
import { IClazz } from '../shared/models/IClazz';
import { IPageable } from '../shared/models/IPageable';

export function getClazzes(page: number = 0, limit: number = api.size, sort: string = api.sortBy): Promise<IPageable<IClazz>> {
  return doRequest({ method: 'GET' }, `?limit=${limit}&page=${page}&sort=${sort}`);
}

export function getClazz(code: string) {
  return doRequest({ method: 'GET' }, `/${code}`);
}

export function addClazz(clazz: IClazz, token: string) {
  return doRequest({ method: 'POST', body: JSON.stringify(clazz), headers: loadHeaders(token) });
}

export function updateClazz(code: string, clazz: IClazz, token: string) {
  return doRequest({ method: 'PUT', body: JSON.stringify(clazz), headers: loadHeaders(token) }, `/${code}`);
}

export function deleteClazz(code: string, token: string) {
  return doRequest({ method: 'DELETE', headers: loadHeaders(token) }, `/${code}`);
}

export function assignStudents(code: string, students: string[], teacher: string | undefined, token: string) {
  return doRequest({ method: 'POST', body: JSON.stringify({ teacher, students }), headers: loadHeaders(token) }, `/${code}/assign`)
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${api.url}/clazz${path}`, init)
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