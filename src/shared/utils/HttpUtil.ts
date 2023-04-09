import { api } from '../../config';
import { fromLocal } from './StorageUtil';

export function doRequest(init: RequestInit, path: string) {
  return fetch(`${api.url}${path}`, init)
    .then(response => {
      const data = response.json();
      return response.ok ? data : data.then(error => Promise.reject(error));
    }).catch(error => {
      alert(error.message);
      return Promise.reject(error);
    });
}

export function loadHeaders() {
  const header = { "Content-Type": "application/json" };
  const idToken = fromLocal('idToken');
  return !idToken ? header : { ...header, "Authorization": `Bearer ${idToken}` }
}