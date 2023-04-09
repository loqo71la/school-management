import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import 'leaflet/dist/leaflet.css';
import './index.css';

import Layout from './components/theme/Layout';
import AuthProvider from './context/AuthContext';
import ClazzForm from './pages/ClazzForm';
import ClazzInfo from './pages/ClazzInfo';
import ClazzList from './pages/ClazzList';
import StudentForm from './pages/StudentForm';
import StudentInfo from './pages/StudentInfo';
import StudentList from './pages/StudentList';


ReactDOM.createRoot(document.getElementById('root') as HTMLElement)
  .render(
    <React.StrictMode>
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            <Route element={<Layout />}>
              <Route path="/" element={<Navigate replace to="/classes" />} />
              <Route path="/classes" element={<ClazzList />} />
              <Route path="/classes/:id" element={<ClazzInfo />} />
              <Route path="/students" element={<StudentList />} />
              <Route path="/students/:id" element={<StudentInfo />} />
              <Route path="/classes/create" element={<ClazzForm />} />
              <Route path="/classes/:id/update" element={<ClazzForm />} />
              <Route path="/students/create" element={<StudentForm />} />
              <Route path="/students/:id/update" element={<StudentForm />} />
            </Route>
          </Routes>
        </AuthProvider>
      </BrowserRouter>
    </React.StrictMode>
  );
