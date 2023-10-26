import './Pay.css';
import { useState } from 'react';
import Post from './Post';
import Modal from '../components/Modal';

export default function Pay(){
    const [modal,setModal] = useState(false);
    const [addressObj,setAddressObj] = useState('');
    const [locationObj,setLocationObj] = useState('');
    return(
        <div className='paycontainer'>
    <form>
    <ul>
        <li><label>주문고객:</label> 권용호(010-1234-5678-0000)</li>
        <li>
            <ul>
                <li>직접수령</li>
                <li>기본배송지</li>
            {/* 클릭시 데이터베이스에서 사용자가 입력한 주소를 가져옴 */}
                <li>배송지 입력</li>
                </ul>
                </li>
                <li>
                <input type='text' disabled placeholder='지역주소'></input>
                <Post setAddressObj={setAddressObj} setLocationObj={setLocationObj} />  
                <ul>
        <li><input type='text' placeholder='우편번호'></input></li>
        <li><input type='text' placeholder='주소'></input></li>
        <li><input type='text' placeholder='상세주소'></input></li>
        <li><input type='text' placeholder='요청사항'></input></li>
                </ul>
        </li>
        <p><input  value="등록"  onClick={() => {
            setModal(!false);
            if(modal !==false){
                document.querySelector('.modalwrap').style.display = 'block';
            }
            }}/></p>
<Modal />
        <p><input type="submit" value="취소"/></p>
    </ul>
</form>
</div>
);
              
}