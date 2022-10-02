import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

import { AuthContext } from '../../context/AuthContext';
import Loader from './Loader';

export default function ProtectedRoute() {
  const { user, isLoading } = useContext(AuthContext);

  if (isLoading) return <Loader />
  if (!user) return <Navigate to="/login" />;
  return <Outlet />;
}
