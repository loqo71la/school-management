import { useState } from 'react';
import { Outlet } from 'react-router-dom';

import Footer from './Footer';
import Header from './Header';
import Menu from './Menu';

function Layout() {
  const [isOpen, setIsOpen] = useState(false);
  const close = () => setIsOpen(false);

  return (
    <div className="flex grow">
      <Menu className="hidden sm:block" />
      {isOpen &&
        <section className="fixed inset-0 z-50 flex">
          <Menu onClick={close} />
          <div className="bg-gray-600 bg-opacity-50 w-full" onClick={close} />
        </section>
      }
      <div className="flex flex-col bg-gray-100 w-full">
        <Header onClick={() => setIsOpen(true)} />
        <main className="h-full p-4">
          <Outlet />
        </main>
      <Footer />
      </div>
    </div>
  );
}

export default Layout;
