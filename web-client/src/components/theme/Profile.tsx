import { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { PersonFill } from '@loqo71la/react-web-icons';

import { AuthContext } from '../../context/AuthContext';
import PopupMenu from '../common/PopupMenu';

function Profile() {
  const { user, signOut } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSignout = async () => {
    try {
      console.log(user)
      await signOut();
      navigate('/');
    } catch (error) {
      console.error(error)
    }
  };

  if (user) return (
    <PopupMenu
      trigger={
        <img
          className="inline-block h-8 w-8 rounded-full ring-2 ring-white"
          src={user.image}
          alt="Avatar"
        />
      }
      options={[
        <div className="p-2 flex gap-2">
          <PersonFill className="w-5 h-5" />
          <p className="font-light">
            {user.name}
            <span className="block text-xs font-medium">{user.email}</span>
          </p>
        </div>,
        <button
          onClick={handleSignout}
          className="w-full px-2 py-1.5 text-left text-sm hover:bg-gray-100"
        >
          Sign out
        </button>
      ]}
      alignment="right"
    />
  );

  return (
    <Link
      to={`/login`}
      className="py-1 px-5 border border-teal-600 rounded-full text-teal-600 hover:bg-teal-600 hover:text-white"
    >
      Sign in
    </Link>
  );
}

export default Profile;
