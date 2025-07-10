import axios from 'axios';

const addressAPI = 'https://esgoo.net/api-tinhthanh';

export const getCities = async () => {

    try{
        const response  = await axios.get(`${addressAPI}/1/0.htm`)
        return response.data.data
    } catch (error) {
        console.error('Error get cities:', error);
        return []; 
    }
};

export const getDistricts = async (cityId) => {

    try{
        const response = await axios.get(`${addressAPI}/2/${cityId}.htm`)
        return response.data.data
    }catch(error){
        console.error('Error get districts:', error);
    }
};

export const getWards = async (districtId) => {
    try{
        const response = await axios.get(`${addressAPI}/3/${districtId}.htm`)
        return response.data.data
    }catch(error){
        console.error('Error get wards:', error);
        return [];
    }
};
