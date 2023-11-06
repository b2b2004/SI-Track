import { click } from '@testing-library/user-event/dist/click';
import './Signup.css';
import Post from './Post';
import { useState } from 'react';
export default function Signup(){

    const [modal,setModal] = useState(false);
    const [addressObj,setAddressObj] = useState('');
    const [locationObj,setLocationObj] = useState('');
    const address = addressObj.townAddress;
    return(
        <div className='signup-container'>
        <h1>
            <p>Sign up</p>
        </h1>
        <form method='post' id='indimember'>
        <div className='member'>
            <button type='button' className='indi' >개인회원</button>
            <button type='button' className='business' onClick={()=>{
                setModal(!false);
                if(modal !==false ){
                    document.querySelector('#indimember').style.display='none'
                    document.querySelector('.business-container').style.display='block'
                }
            }}>기업회원</button>
        </div>
        <input type="text" placeholder="아이디"></input>
        <input type="email" placeholder="이메일"></input>
        <input type="password" placeholder="비밀번호"></input>
        <input type="password" placeholder="비밀번호확인"></input>
        <input type="text" placeholder="이름"></input>
        <input type="text" placeholder="휴대폰번호" className='phone'></input>
        <button className='verify'>인증번호 전송</button>
        <input type="text" placeholder="인증번호확인" className='phone' disabled></input>
        <button className='verify'>인증완료</button>
        <input type="submit" value="회원가입 완료"></input>              
        </form>
       
       
        <div className='business-container'>
            <form method='post' id='businessmember'>
        <div className='member'>
            <button type='button' onClick={()=>{
                setModal(false);
                if(modal ===false ){
                    document.querySelector('.business-container').style.display='none'
                    document.querySelector('#indimember').style.display='flex'
                }
            }} >개인회원</button>
            <button type='button' className='business' >기업회원</button>
        </div>
            <input type='text' placeholder='회사/단체명'></input>
            <input type='text' placeholder='담당자 이름'></input>
            <input type='text' placeholder='사업자번호'></input>
            <input type='text'defaultValue={address} placeholder='사업자주소'></input>
            <Post setAddressObj={setAddressObj} setLocationObj={setLocationObj} />  
            <input type='text' placeholder='상세주소'></input>
            <input type='text' placeholder='대표자이름'></input>
            <input type='text' placeholder='연락처'></input>
            <input type="submit" value="회원가입 완료"></input>
            </form>
        </div>
        </div>
    )
}


