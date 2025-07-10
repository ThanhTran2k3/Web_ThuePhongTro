import React from 'react';

const AddressSelect = ({
  cities,
  districts,
  wards,
  selectedCity,
  selectedDistrict,
  selectedWard,
  setSelectedCity,
  setSelectedDistrict,
  setSelectedWard
}) => {
  return (
    <>
        <div>
            <label>Thành phố/Tỉnh</label>
            <select value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
                <option value="">-- Chọn tỉnh --</option>
                {cities.map((city) => (
                <option key={city.id} value={city.id}>{city.full_name}</option>
                ))}
            </select>
        </div>
     
        <div>
            <label>Quận/Huyện</label>
            <select 
                value={selectedDistrict} 
                onChange={(e) => setSelectedDistrict(e.target.value)} 
                disabled={!selectedCity}
            >
                <option value="">-- Chọn quận --</option>
                {districts.map((district) => (
                    <option key={district.id} value={district.id}>{district.full_name}</option>
                ))}
            </select>
        </div>
      
        <div>
            <label>Phường/Xã</label>
            <select
                value={selectedWard}
                onChange={(e) => setSelectedWard(e.target.value)}
                disabled={!selectedDistrict}
            >
                <option value="">-- Chọn phường --</option>
                {wards.map((ward) => (
                <option key={ward.id} value={ward.id}>{ward.full_name}</option>
                ))}
            </select>
        </div>
      
    </>
  );
};

export default AddressSelect;
