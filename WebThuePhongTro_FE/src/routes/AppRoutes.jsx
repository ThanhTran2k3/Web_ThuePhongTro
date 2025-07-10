import { Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import PrivateRoute from './PrivateRoute';
import OAuthSuccess from '../pages/OAuthSuccess';
import Account from '../pages/Account';

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/oauth2/success" element={<OAuthSuccess />} />
      <Route path="/account" element={<PrivateRoute><Account /></PrivateRoute>} />
    </Routes>
  );
}

export default AppRoutes;
