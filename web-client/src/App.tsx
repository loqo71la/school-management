import { Outlet } from 'react-router-dom';

import Footer from './components/theme/Footer';
import Header from './components/theme/Header';
import Menu from './components/theme/Menu';

function App() {
  return (
    <div className="flex h-screen">
      <Menu />
      <div className="flex flex-col w-full bg-gray-100">
        <Header />
        <main className="h-full p-4">
          <Outlet />
        </main>
        <Footer />
      </div>
    </div>
  );
}

export default App;
