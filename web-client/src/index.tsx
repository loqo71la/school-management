import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import Clazzes from './pages/clazzes/Clazzes';
import Clazz from './pages/clazz/Clazz';
import Students from './pages/students/Students';
import Student from './pages/student/Student';

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route element={<App />}>
          <Route path="/" element={<Navigate replace to="/classes" />} />
          <Route path="/classes" element={<Clazzes />} />
          <Route path="/classes/:code" element={<Clazz />} />
          <Route path="/classes/newClass" element={<Clazz />} />
          <Route path="/students" element={<Students />} />
          <Route path="/students/:idNo" element={<Student />} />
          <Route path="/students/newStudent" element={<Student />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
