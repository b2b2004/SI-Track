import './Pay.css';
import { useState } from 'react';
import Post from './Post';
import { Link } from 'react-router-dom';

export default function Pay(){
    const [addressObj,setAddressObj] = useState('');
    const [locationObj,setLocationObj] = useState('');
    return(

        <div className='paycontainer'>
    <form>
    <ul>
        <li><label>주문고객:</label> 권용호(010-1234-5678-0000)</li>
        <li>
            <ul>
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'none';
                    document.querySelector('.direct').style.display = 'block';
                    document.querySelector('.database').style.display = 'none';
                }}>직접수령</li>
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'none';
                    document.querySelector('.direct').style.display = 'none';
                    document.querySelector('.database').style.display = 'block';
                }}>기본배송지</li>
            {/* 클릭시 데이터베이스에서 사용자가 입력한 주소를 가져옴 */}
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'block';
                    document.querySelector('.direct').style.display = 'none';
                    document.querySelector('.database').style.display = 'none';
                }}>배송지 입력</li>
                </ul>
                </li>
                <li className='daumapi'>
                <input type='text' disabled placeholder='지역주소'></input>
                <Post setAddressObj={setAddressObj} setLocationObj={setLocationObj} />  
                <ul>
        <li><input type='text' placeholder='우편번호'></input></li>
        <li><input type='text' placeholder='주소'></input></li>
        <li><input type='text' placeholder='상세주소'></input></li>
        <li><input type='text' placeholder='요청사항'></input></li>
                </ul>
        </li>
        <li className='direct'>
            직접수령
        </li>
        <li className='database'>
            데이터베이스에 입력된 사용자 주소
        </li>
        <p><Link to='/paycomplete'><input defaultValue="결제"/></Link></p>
        <p><input defaultValue="취소"/></p>
    </ul>
</form>
</div>
);
              
}