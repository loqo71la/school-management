import { createContext, useEffect, useState } from 'react';
import { GithubAuthProvider, GoogleAuthProvider, onAuthStateChanged, signInWithPopup, signOut, UserCredential, } from 'firebase/auth';

import { auth } from '../firebase';
import { IUser } from '../shared/models/IUser';

interface AuthContextProps {
  user: IUser | null,
  signOut: () => Promise<void>,
  signInWithGitHub: () => Promise<UserCredential>,
  signInWithGoogle: () => Promise<UserCredential>
};

function AuthProvider(props: { children: JSX.Element }) {
  const data = JSON.parse(window.localStorage.getItem('user') || 'null');
  const [user, setUser] = useState<IUser | null>(data);

  useEffect(() => {
    onAuthStateChanged(auth, async authState => {
      if (!authState) return;
      const accessToken = await authState.getIdToken();
      const newUser = { accessToken, email: authState.email || '', image: authState.photoURL || '', name: authState.displayName || '' };
      window.localStorage.setItem('user', JSON.stringify(newUser));
      setUser(newUser);
    })
  }, []);

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
