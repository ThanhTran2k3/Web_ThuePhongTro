import React from 'react'

const RoomTypeSelect = () => {
  return (
    <div>
        <label for="car-type">Loại xe</label>
        <select id="car-type" name="car-type">
        <option value="">Tất cả</option>
        <option value="4-chose">4 chỗ</option>
        <option value="7-chose">7 chỗ</option>
        </select>
    </div>
  )
}

export default RoomTypeSelect