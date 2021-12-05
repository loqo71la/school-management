import { IClazz } from "../shared/models/IClazz";
import * as config from '../App.config.json';

export function getClazzes() {
  return doRequest({ method: 'GET' });
}

export function getClazz(code: string) {
  return doRequest({ method: 'GET' }, `/${code}`);
}

export function addClazz(clazz: IClazz) {
  return doRequest({ method: 'POST', body: JSON.stringify(clazz), headers: config.headers })
}

export function updateClazz(code: string, clazz: IClazz) {
  return doRequest({ method: 'PUT', body: JSON.stringify(clazz), headers: config.headers }, `/${code}`);
}

export function deleteClazz(code: string) {
  return doRequest({ method: 'DELETE' }, `/${code}`);
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${config.baseUrl}/api/classes${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => alert(error.message));
}