import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../features/auth/authSlice';

import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from 'redux-persist';

import { CookieStorage } from 'redux-persist-cookie-storage';
import Cookies from 'js-cookie';

const cookieStorage = new CookieStorage(Cookies, {
  expiration: {
    default: 7 * 24 * 60 * 60, // 7 ngày (giây)
  },
  path: '/',
  secure: false, // đổi thành true nếu dùng HTTPS
  sameSite: 'Lax',
});

const persistConfig = {
  key: 'auth',
  storage: cookieStorage,
};

const persistedAuthReducer = persistReducer(persistConfig, authReducer);

export const store = configureStore({
  reducer: {
    auth: persistedAuthReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }),
});

export const persistor = persistStore(store);
