import { Link } from 'react-router-dom';
import './Login.css';
import { useState } from 'react';
export default function Login(){
    const [id,setId] = useState("");
    const [pw,setPw] = useState("");

    function handlelogin(data){
        data.preventDefault();
        console.log(data);

        if(id===''){
            alert('아이디를 입력해주세요.');
            return false;
        }
        if(pw===''){
            alert('비밀번호를 입력해주세요.');
            return false;
        }
        fetch("http://localhost:8080/login",{
            method:"POST",
            headers:{
                "content-Type":"application/json; charset=utf-8"
            },
            body:JSON.stringify({
                userId:id,
                userPassword:pw,
            }),
        })
        .then((data)=>{
            let jwtToken = data.headers.get("Authorization");
            console.log(jwtToken);
            if(jwtToken!==null){
                localStorage.setItem('Authorization',jwtToken);
                alert("로그인이 완료되었습니다.");
                window.location.href="/";
            }
            else if(jwtToken===null){
                alert('아이디, 비밀번호를 확인해주세요.')
            }
        })
    }
    return(
        <div className='login-container'>
        <h1>
            <p>Log in</p>
        </h1>
        <form>
        <input type="text" onChange={(e)=>setId(e.target.value)} placeholder="아이디"></input>
        <input type="password" onChange={(e)=>setPw(e.target.value)} placeholder="비밀번호"></input>
        <input type="submit" onClick={handlelogin} value="로그인"></input>
        <div className='findcont'>
        <Link to="/signup">회원가입</Link>
        <Link to="/findid">아이디 / 비밀번호찾기</Link>
        </div>
        </form>
        </div>
    )
}