
import { useEffect, useState } from 'react';
import { getCities, getDistricts, getWards } from './addressAPI';


export const useAddress = () => {
  const [cities, setCities] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);

  const [selectedCity, setSelectedCity] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedWard, setSelectedWard] = useState('');


  useEffect(() => {
    const fetchCities = async () => {
      const res = await getCities();
      setCities(res || []);
    };
    fetchCities();
  }, []);

  useEffect(() => {
    const fetchDistricts = async () => {
      if (selectedCity) {
        const res = await getDistricts(selectedCity);
        setDistricts(res || []);
        setSelectedDistrict('');
        setWards([]);
        setSelectedWard('');
      } else {
        setDistricts([]);
        setSelectedDistrict('');
        setWards([]);
        setSelectedWard('');
      }
    };
    fetchDistricts();
  }, [selectedCity]);

  useEffect(() => {
    const fetchWards = async () => {
      if (selectedDistrict) {
        const res = await getWards(selectedDistrict);
        setWards(res || []);
        setSelectedWard('');
      } else {
        setWards([]);
        setSelectedWard('');
      }
    };
    fetchWards();
  }, [selectedDistrict]);

  return {
    cities,
    districts,
    wards,
    selectedCity,
    selectedDistrict,
    selectedWard,
    setSelectedCity,
    setSelectedDistrict,
    setSelectedWard,
  };
};

