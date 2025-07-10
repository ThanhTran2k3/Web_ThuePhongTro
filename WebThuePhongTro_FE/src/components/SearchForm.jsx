import React from 'react';
import { useAddress } from '../features/address/useAddress';
import AddressSelect from './AddressSelect';
import RoomTypeSelect from './RoomTypeSelect';

const SearchForm = () => {
    const {
        cities,
        districts,
        wards,
        selectedCity,
        selectedDistrict,
        selectedWard,
        setSelectedCity,
        setSelectedDistrict,
        setSelectedWard,
    } = useAddress();
    return (
        <div>
            <form className="search-form" aria-label="Form tìm kiếm cho thuê xe">
                 <AddressSelect
                  cities={cities}
                  districts={districts}
                  wards={wards}
                  selectedCity={selectedCity}
                  selectedDistrict={selectedDistrict}
                  selectedWard={selectedWard}
                  setSelectedCity={setSelectedCity}
                  setSelectedDistrict={setSelectedDistrict}
                  setSelectedWard={setSelectedWard}
                />
                <RoomTypeSelect />
                <button type="submit" aria-label="Tìm xe">Tìm xe</button>
              </form>
        </div>
    );
};

export default SearchForm;