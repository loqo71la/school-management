import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import './index.css';
import AuthProvider from './context/AuthContext';
import ClazzForm from './pages/ClazzForm';
import ClazzInfo from './pages/ClazzInfo';
import ClazzList from './pages/ClazzList';
import Layout from './components/theme/Layout';
import Login from './pages/Login';
import ProtectedRoute from './components/common/ProtectedRoute';
import StudentForm from './pages/StudentForm';
import StudentInfo from './pages/StudentInfo';
import StudentList from './pages/StudentList';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<Layout />}>
            <Route path="/" element={<Navigate replace to="/classes" />} />
            <Route path="/classes" element={<ClazzList />} />
            <Route path="/classes/:id" element={<ClazzInfo />} />
            <Route path="/students" element={<StudentList />} />
            <Route path="/students/:id" element={<StudentInfo />} />
            <Route element={<ProtectedRoute />}>
              <Route path="/classes/create" element={<ClazzForm />} />
              <Route path="/classes/:id/update" element={<ClazzForm />} />
              <Route path="/students/create" element={<StudentForm />} />
              <Route path="/students/:id/update" element={<StudentForm />} />
            </Route>
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
