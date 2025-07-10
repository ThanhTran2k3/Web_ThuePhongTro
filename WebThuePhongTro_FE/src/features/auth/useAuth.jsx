import { useDispatch } from 'react-redux';
import { loginSuccess, logoutSuccess, updateAccountSuccess } from './authSlice';
import { changePassAPI, editAccountAPI, getAccountAPI, loginAPI, registerAPI } from './authAPI';
import { meAPI } from '../user/userAPI';

export const useAuth = () => {
    const dispatch = useDispatch();


    const login = async (loginData) => {
        const result = await loginAPI(loginData);
        const user = await meAPI(result.accessToken);
        const account = await getAccountAPI(result.accessToken);
        dispatch(loginSuccess({
            token: result.accessToken,
            user: user,
            account: account
        }));
    };

    const register = async (registerData) => {
        await registerAPI(registerData);
    };

     const authWithGoogle = async (token) => {
        const user = await meAPI(token);
        const account = await getAccountAPI(token);
        dispatch(loginSuccess({
            token: token,
            user: user,
            account: account
        }));
    };

    const changePass = async (token, changePassData) => {
        await changePassAPI(token, changePassData);
    };

    const editAccount = async (token, editData) => {
        const response = await editAccountAPI(token,editData); 
        if (!response) {
            return;
        }
        dispatch(updateAccountSuccess({
            account: response
        }));
    };

    const logout = () => {
        dispatch(logoutSuccess());
    };

    return { 
        login, 
        register, 
        logout,
        authWithGoogle,
        changePass,
        editAccount
    };
}
