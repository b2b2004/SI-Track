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
            alert('비밀번호가 일치하지 않습니다.');
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
                window.location.href="/";
            }
        })
    }
    return(
        <div className='signup-container'>
        <h1>
            <p>Sign up</p>
        </h1>
        <form id='indimember'>
        <input type="text" onChange={(e)=>setId(e.target.value)} placeholder="아이디"></input>
        <div className='idreg'></div>
        <input type="email" id='email' onChange={(e)=>{
            const regemail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
            if(regemail.test(e.target.value)){
                document.querySelector('.emailreg').style.display='none';
                setEmail(e.target.value)
            }
            else{
                document.querySelector('.emailreg').style.display='block';
            }
            }} placeholder="이메일"></input>
        <div className='emailreg'>형식에 맞게 입력해주세요.</div>
        <input type="password" id='pw' onChange={(e)=>setPw(e.target.value)} placeholder="비밀번호"></input>
        <div className='passreg'></div>
        <input type="password" id='pwcheck' onChange={(e)=>setPwCheck(e.target.value)} placeholder="비밀번호확인"></input>
        <div className='pwcheckreg'></div>
        <input type="text" id='name' onChange={(e)=>setName(e.target.value)} placeholder="이름"></input>
        <input type="text" id='phonenum' onChange={(e)=>{
            const regex = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/
            if(regex.test(e.target.value)){
                document.querySelector('#numreg').style.display='none';
                setPhonenum(e.target.value)}
                else{
                    document.querySelector('#numreg').style.display='block';
                }
            }
           } placeholder="-를 제외하고 입력해주세요." className='phone'></input>
        <div id='numreg'>형식에 맞게 입력해주세요.</div>
        <input type="submit" id='signupfinish' onClick={handlesignup} value="회원가입 완료"></input>              
        </form>
        </div>
    )
}


