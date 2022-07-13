import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import './index.css';
import App from './App';
import Clazz from './pages/Clazz';
import Clazzes from './pages/Clazzes';
import Student from './pages/Student';
import Students from './pages/Students';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route element={<App />}>
          <Route path="/" element={<Navigate replace to="/classes" />} />
          <Route path="/classes" element={<Clazzes />} />
          <Route path="/classes/:code" element={<Clazz />} />
          <Route path="/students" element={<Students />} />
          <Route path="/students/:idNo" element={<Student />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
