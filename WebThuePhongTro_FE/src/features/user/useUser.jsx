import Swal from 'sweetalert2';
import { editUserAPI } from './userAPI';
import { useDispatch, useSelector } from 'react-redux';
import { updateUserSuccess } from '../auth/authSlice';
export const useUser= () => {
    const auth = useSelector(state => state.auth); 
    const dispatch = useDispatch();

    
    const updateUser = async (editData) => {
        const response = await editUserAPI(auth.token,editData); 
        if (!response) {
            return;
        }
        dispatch(updateUserSuccess({
            user: response,
        }));
    };


    return { 
        updateUser,
    };
}
