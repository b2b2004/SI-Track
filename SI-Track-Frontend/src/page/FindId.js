import { useState } from 'react';
import './FindId.css';
import { Link } from 'react-router-dom';
import Success from './Success';
export default function FindId(){
    const [toggle,setToggle] = useState('none');
    if(toggle==='form1'){
        document.querySelector('.formcont1').style.display='block'
        document.querySelector('.formcont2').style.display='none'
    }
    else if(toggle==='form2'){
        document.querySelector('.formcont2').style.display='block'
        document.querySelector('.formcont1').style.display='none'
    }
    return(
        <div className="find-container">
            <h1>
                <p>아이디 찾기</p>
            </h1>
            <div className='select1'>
                <input type="radio" name='verify' checked onClick={()=>{
                    setToggle('form1');
                }}/>
                <label>회원정보에 등록한 휴대전화로 인증</label>
                </div>
                <div className='formcont1'>
            <form method="post">
                <p>이름</p>
                <input type="text"></input>
                <p>휴대전화</p>
                <input type="text"></input>
                <button >인증번호받기</button>
                <input type="text" disabled value='인증번호 6자리 숫자 입력'></input>
                <button type='submit' ><Link to='/success'>다음</Link></button>
            </form>
            </div>
            <div className='select2'>
            <input type="radio" name='verify' onClick={()=>{
                    setToggle('form2');
                }}/>
            <label>회원정보에 등록한 이메일로 인증</label>
            </div>
            <div className='formcont2'>
            <form method="post" >
                <p>이름</p>
                <input type="text"></input>
                <p>이메일 주소</p>
                <input type="text"></input>
                <span>@</span>
                <input type="text"></input>
                <button >인증번호받기</button>
                <input type="text" disabled value='인증번호 6자리 숫자 입력'></input>
                <button type='submit' ><Link to='/success'>다음</Link></button>
            </form>
            </div>
        </div>
        // s
    )
}