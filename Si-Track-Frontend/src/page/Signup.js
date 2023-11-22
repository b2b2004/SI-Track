import { click } from '@testing-library/user-event/dist/click';
import './Signup.css';
import Post from './Post';
import { useState } from 'react';
export default function Signup(){

    const [modal,setModal] = useState(false);
    const [addressObj,setAddressObj] = useState('');
    const [locationObj,setLocationObj] = useState('');
    const address = addressObj.townAddress;
    const [id,setId] = useState('');
    const [email,setEmail] = useState('');
    const [pw,setPw] = useState('');
    const [pwcheck,setPwCheck] = useState('');
    const [name,setName] = useState('');
    const [phonenum,setPhonenum] = useState('');
    function handlesignup(e){
        if(id===''){
            alert('아이디를 입력해주세요.');
            return false;
        }
        else if(pw===''){
            alert('비밀번호를 입력해주세요.');
            return false;
        }
        else if(pwcheck===''){
            alert('비밀번호 확인란을 입력해주세요.');
            return false;
        }
        else if(pw!==pwcheck){
            alert('비밀번호확인란을 다시 입력해주세요');
            return false;
        }
        else if(email===''){
            alert('이메일을 입력해주세요.');
            return false;
        }
        else if(name===''){
            alert('이름을 입력해주세요.');
            return false;
        }
        else if(phonenum===''){
            alert('전화번호를 입력해주세요.');
            return false;
        }
        e.preventDefault();
        fetch("http://localhost:8080/account/user/signup",{
            method:"POST",
            headers:{
                "content-Type":"application/json; charset=utf-8"
            },
            body:JSON.stringify({
                userId:id,
                userPassword:pw,
                userName:name,
                userEmail:email,
                userPhoneNumber:phonenum,
            }),
        })
        .then((e)=>{
            if(e.status===200){
                alert("회원가입이 완료되었습니다.");
                window.location.href("/");
            }
        })
    }
    return(
        <div className='signup-container'>
        <h1>
            <p>Sign up</p>
        </h1>
        <form id='indimember'>
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
        <input type="text" onChange={(e)=>setId(e.target.value)} placeholder="아이디"></input>
        <input type="email" id='email' onChange={(e)=>setEmail(e.target.value)} placeholder="이메일"></input>
        <input type="password" id='pw' onChange={(e)=>setPw(e.target.value)} placeholder="비밀번호"></input>
        <input type="password" id='pwcheck' onChange={(e)=>setPwCheck(e.target.value)} placeholder="비밀번호확인"></input>
        <input type="text" id='name' onChange={(e)=>setName(e.target.value)} placeholder="이름"></input>
        <input type="text" id='phonenum' onChange={(e)=>setPhonenum(e.target.value)} placeholder="휴대폰번호" className='phone'></input>
        <button className='verify'>인증번호 전송</button>
        <input type="text" placeholder="인증번호확인" className='phone' disabled></input>
        <button className='verify'>인증완료</button>
        <input type="submit" onClick={handlesignup} value="회원가입 완료"></input>              
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


