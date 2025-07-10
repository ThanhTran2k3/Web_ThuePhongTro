import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const auth = useSelector(state => state.auth); 

  return auth.isAuthenticated ? children : <Navigate to="/" replace />;
};

export default PrivateRoute;
