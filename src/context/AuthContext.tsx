import { createContext, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import jwt_decode from 'jwt-decode';

import { api } from '../config';
import { fromLocal, toLocal } from '../shared/utils/StorageUtil';
import { IUser } from '../shared/models/IUser';

interface AuthContextProps {
  user: IUser | null;
  authorized: (action: () => void) => void;
  signOut: () => void;
}

function AuthProvider(props: { children: JSX.Element }) {
  const [searchParams] = useSearchParams();

  const [user, setUser] = useState<IUser | null>(() => {
    const token = searchParams.get('id_token') ?? fromLocal('idToken');
    if (!token) return null

    if (searchParams.has('id_token')) toLocal('idToken', token);
    const data: any = jwt_decode(token);
    return { username: data.username, imageUrl: data.imageUrl, email: data.email };
  });

  const onSignOut = () => {
    toLocal('idToken', null);
    setUser(null);
  }

  const onAuthorized = (action: () => void) => {
    const authUrl = `${api.auth}&redirect_uri=${window.location.href}`;
    if (user == null) window.location.replace(authUrl);
    else action();
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        authorized: onAuthorized,
        signOut: onSignOut
      }}
    >
      {props.children}
    </AuthContext.Provider >
  );
}

export default AuthProvider;

export const AuthContext = createContext<AuthContextProps>({} as AuthContextProps);
