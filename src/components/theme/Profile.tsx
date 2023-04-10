import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { PersonFill } from '@loqo71la/react-web-icons';

import { AuthContext } from '../../context/AuthContext';
import PopupMenu from '../common/PopupMenu';
import { api } from '../../config';
import tinycolor from 'tinycolor2';

function Profile() {
  const { user, signOut } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSignout = async () => {
    try {
      signOut();
      navigate('/');
    } catch (error) {
      console.error(error)
    }
  };

  if (!user) return (
    <a href={`${api.auth}&redirect_uri=${window.location.href}`}
      className="py-1.5 px-5 text-sm border border-sky-600 rounded-full text-sky-600 hover:bg-sky-600 hover:text-white"
    >
      Sign in
    </a>
  );

  const options = [
    <div className="p-2 flex gap-2">
      <PersonFill className="w-5 h-5" />
      <p className="font-light">
        {user.username}
        <span className="block text-xs font-medium">{user.email}</span>
      </p>
    </div>,
    <button onClick={handleSignout} className="w-full px-2 py-1.5 text-left text-sm hover:bg-gray-100">
      Sign out
    </button>
  ]

  const guest = user.username.match(/^guest_(.{6})$/);
  if (!guest) return (
    <PopupMenu
      trigger={<img className="inline-block h-8 w-8 rounded-full ring-2 ring-white" src={user.imageUrl} alt="Avatar" />}
      options={options}
      alignment="right"
    />
  );

  const color = tinycolor(`#${guest[1]}`);
  const style = { backgroundColor: color.toHexString() };
  return (
    <PopupMenu
      trigger={<div className={`w-8 h-8 pt-1 rounded-full ${color.isDark() && 'text-white'}`} style={style}>G</div>}
      options={options}
      alignment="right"
    />
  );
}

export default Profile;
