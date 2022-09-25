import { GitHub, Google } from '@loqo71la/react-web-icons';
import { UserCredential } from 'firebase/auth';
import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function Login() {
  const { signInWithGitHub, signInWithGoogle } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSignin = async (signInProvider: () => Promise<UserCredential>) => {
    try {
      await signInProvider();
      navigate('/');
    } catch (error) {
      console.log(error)
    }
  };

  return (
    <section className="flex h-full justify-center items-center bg-gray-100">
      <div className="flex flex-col gap-2 w-96 m-2 p-6 bg-white rounded-lg shadow-lg">
        <h2 className="mx-auto text-xl font-light">Welcome to School Management</h2>
        <img src="logo512.png" className="w-32 mx-auto my-8" alt="School Management" />
        <button
          onClick={() => handleSignin(signInWithGoogle)}
          className="flex justify-center gap-2 bg-blue-600 text-white font-light hover:shadow-lg rounded-full p-2"
        >
          <Google className="w-6 h-6 fill-white" />
          Sing in with Google
        </button>
        <button
          onClick={() => handleSignin(signInWithGitHub)}
          className="flex justify-center gap-2 bg-gray-600 text-white font-light hover:shadow-lg rounded-full p-2"
        >
          <GitHub className="w-6 h-6 fill-white" />
          Sing in with GitHub
        </button>
      </div>
    </section>
  );
}

export default Login;
