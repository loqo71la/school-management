import { createContext, useEffect, useState } from 'react';
import { GithubAuthProvider, GoogleAuthProvider, onAuthStateChanged, signInWithPopup, signOut, UserCredential } from 'firebase/auth';

import { auth } from '../firebase';
import { IUser } from '../shared/models/IUser';
import { api } from '../App.config';

interface AuthContextProps {
  user: IUser | null,
  signOut: () => Promise<void>,
  signInWithGitHub: () => Promise<UserCredential>,
  signInWithGoogle: () => Promise<UserCredential>
};

function AuthProvider(props: { children: JSX.Element }) {
  const [user, setUser] = useState<IUser | null>(null);
  const [count, setCount] = useState<number>(0);
  const [limit, setLimit] = useState<number>(0);

  useEffect(() => {
    const calculate = (time: string) => {
      const expiration = (new Date(time)).getTime();
      const current = (new Date()).getTime();
      const delta = expiration - current - api.expirationTime;
      return delta > 0 ? delta : 0;
    };

    onAuthStateChanged(auth, async authState => {
      if (!authState) return;
      const { token, expirationTime } = await authState.getIdTokenResult(count > 0);
      setLimit(calculate(expirationTime));
      setUser({
        accessToken: token,
        email: authState.email || '',
        image: authState.photoURL || '',
        name: authState.displayName || ''
      });
    });
  }, [count]);

  useEffect(() => {
    if (!limit) return;
    const timer = setTimeout(() => setCount(prev => prev + 1), limit);
    return () => clearTimeout(timer);
  }, [limit]);

  const onSignOut = async () => {
    window.localStorage.setItem('user', JSON.stringify(null));
    signOut(auth);
    setUser(null);
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        signOut: onSignOut,
        signInWithGitHub: () => signInWithPopup(auth, new GithubAuthProvider()),
        signInWithGoogle: () => signInWithPopup(auth, new GoogleAuthProvider()),
      }}
    >
      {props.children}
    </AuthContext.Provider >
  );
}

export default AuthProvider;

export const AuthContext = createContext<AuthContextProps>({} as AuthContextProps);
