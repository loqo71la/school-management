import { IClazz } from "../shared/models/IClazz";

const headers = { "Content-Type": "application/json" }

export function getClazzes() {
  return doRequest({ method: 'GET' });
}

export function getClazz(code: string) {
  return doRequest({ method: 'GET' }, `/${code}`);
}

export function addClazz(clazz: IClazz) {
  return doRequest({ method: 'POST', body: JSON.stringify(clazz), headers })
}

export function updateClazz(code: string, clazz: IClazz) {
  return doRequest({ method: 'PUT', body: JSON.stringify(clazz), headers }, `/${code}`);
}

export function deleteClazz(code: string) {
  return doRequest({ method: 'DELETE' }, `/${code}`);
}

function doRequest(init: RequestInit, path: string = '') {
  return fetch(`${process.env.REACT_APP_SM_API_URL}/api/classes${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => alert(error.message));
}