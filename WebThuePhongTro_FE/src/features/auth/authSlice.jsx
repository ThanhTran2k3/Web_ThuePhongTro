import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    user: null,
    account: null,
    token: null,
    isAuthenticated: false,
};

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        loginSuccess: (state, action) => {
            state.user = action.payload.user;
            state.account = action.payload.account;
            state.token = action.payload.token;
            state.isAuthenticated = true;
        },
        logoutSuccess: (state) => {
            state.user = null;
            state.account = null;
            state.token = null;
            state.isAuthenticated = false;
        },
        updateUserSuccess: (state, action) => {
            state.user = action.payload.user;
        },
        updateAccountSuccess: (state, action) => {
            state.account = action.payload.account;
        },
    },
});

export const { loginSuccess, logoutSuccess, updateUserSuccess, updateAccountSuccess } = authSlice.actions;
export default authSlice.reducer;
