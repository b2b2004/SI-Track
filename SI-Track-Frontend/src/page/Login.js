import { Link } from 'react-router-dom';
import './Login.css';
export default function Login(){
    return(
        <div className='login-container'>
        <h1>
            <p>Log in</p>
        </h1>
        <form method='post'>
        <input type="text" placeholder="아이디"></input>
        <input type="password" placeholder="비밀번호"></input>
        <input type="submit" value="로그인"></input>
        <input type="submit" value="카카오로그인"></input>
        <input type="submit" value="네이버로그인"></input>
        <div className='findcont'>
        <Link to="/signup">회원가입</Link>
        <Link to="/findid">아이디 / 비밀번호찾기</Link>
        </div>
        </form>
        </div>
    )
}