import { useEffect, useState } from 'react';
import './Change.css'
export default function Change(){
    const Authorization = localStorage.getItem('Authorization');
    const [info , setInfo] = useState([]);
    const [userEmail,setUserEmail] = useState('');
    const [userPhoneNumber,setUserPhoneNumber] = useState('');
    function modifinumber(data){
        data.preventDefault();
        fetch("http://localhost:8080/account/login/changeInfo",{
            method: "POST",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            },
            body:JSON.stringify({
                userEmail:userEmail,
                userPhoneNumber:userPhoneNumber,
            }),
        })
        .then((data) =>{
            return data.json();
        })
        .then((data)=>{
            console.log(data);
            console.log(data.userPhoneNumber);
        })
        document.querySelector('#phonenum').disabled=true;
        document.querySelector('#originalphonenum').style.display ='block';
        document.querySelector('#finishnum').style.display ='none';
    }
    function modifiemail(data){
        data.preventDefault();
        fetch("http://localhost:8080/account/login/changeInfo",{
            method: "POST",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            },
            body:JSON.stringify({
                userEmail:userEmail,
                userPhoneNumber:userPhoneNumber,
            }),
        })
        .then((data) =>{
            return data.json();
        })
        .then((data)=>{
            console.log(data);
            console.log(data.userPhoneNumber);
        })
        document.querySelector('#phonenum').disabled=true;
        document.querySelector('#originalphonenum').style.display ='block';
        document.querySelector('#finishnum').style.display ='none';
    }
    useEffect(()=>{
        
        fetch("http://localhost:8080/account/login/info",{
            method: "GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            }
        })
        .then((res) =>{
            return res.json();

        })
        .then((res)=>{
            setInfo(res);
            setUserEmail(info.userEmail);
            setUserPhoneNumber(info.userPhoneNumber);
        })
    },[])
    return(
        <div className="changecontainer">
            <h1>내정보</h1>
            <form>
            <table>
                <tbody>
                <tr>
                        <th>아이디</th>
                        <td>{info.userId}</td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td><input id='name' defaultValue={info.userName} disabled='true'></input></td>
                        <td><button id='originalname' onClick={(e)=>{
                            const namenum = 1;
                            e.preventDefault(); 
                            if(namenum===1){
                                document.querySelector('#name').disabled=false;
                            }
                            else if(namenum!==1){
                                document.querySelector('#name').disabled=true;
                            }
                        }}>이름 변경</button>
                        </td>
                    </tr>

                    <tr>
                        <th>비밀번호</th>
                        <td><input id='password'  type='password' defaultValue='*********' disabled></input></td>
                        <td><button onClick={(e)=>{
                            const passnum = 1;
                            e.preventDefault(); 
                            if(passnum===1){
                                document.querySelector('#password').disabled=false;
                            }
                            else if(passnum!==1){
                                document.querySelector('#password').disabled=true;
                            }
                        }}>비밀번호 변경</button></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><input id='email' onChange={(e)=>setUserEmail(e.target.value)} defaultValue={info.userEmail}disabled></input></td>
                        <td><button id='originalemail' onClick={(e)=>{
                            const emailnum = 1;
                            e.preventDefault(); 
                            if(emailnum===1){
                              document.querySelector('#email').disabled=false;
                              document.querySelector('#originalemail').style.display ='none';
                              document.querySelector('#finishemail').style.display ='block';
                                }
                                else if(emailnum!==1){
                                document.querySelector('#email').disabled=true;
                                }
                        }}>이메일 수정</button>
                        <button id='finishemail' onClick={modifiemail}>변경완료</button></td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td><input id='phonenum' onChange={(e)=>setUserPhoneNumber(e.target.value)} defaultValue={info.userPhoneNumber} disabled></input></td>
                        <td><button id='originalphonenum' onClick={(e)=>{
                           const phone = 1;
                           e.preventDefault(); 
                           if(phone===1){
                             document.querySelector('#phonenum').disabled=false;
                             document.querySelector('#originalphonenum').style.display ='none';
                             document.querySelector('#finishnum').style.display ='block';
                               }
                               else if(phone!==1){
                               document.querySelector('#phonenum').disabled=true;
                               }
                        }}>전화번호 변경</button>
                        <button id='finishnum' onClick={modifinumber}>변경완료</button></td>
                    </tr>
                </tbody>
            </table>
            </form>
        </div>
    )
                    }