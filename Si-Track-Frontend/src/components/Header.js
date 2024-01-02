import './Header.css';
import logo from '../assets/logo.png'
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

export default function Header(){
    localStorage.getItem('Authorization');
    function handlelogout(e){
        e.preventDefault();
        localStorage.removeItem('Authorization');
        alert('로그아웃이 완료되었습니다.');
        window.location.href='/';
    }

    if(localStorage.length===1){
        return(
            <header>
                <div>
                    <h1>
                        <Link to="/">
                            <img src={logo} alt='logo' />
                        </Link>
                    </h1>
                    <nav>
                        <ul>
                            <li onClick={handlelogout}><Link to="/">로그아웃</Link></li>
                            <li><Link to="/mypage">내정보</Link></li>
                        </ul>
                        <ul>
                        <li><Link to="/category1">Category1</Link></li>
                        <li><Link to="/category1">Category2</Link></li>
                        <li><Link to="/category1">Category3</Link></li>
                        <li><Link to="/category1">Category4</Link></li>
                        </ul>
                    </nav>
                </div>
            </header>
        )
    }
     if(localStorage.length!==1){
        return(
            <header>
                <div>
                    <h1>
                        <Link to="">
                            <img src={logo} alt='logo' />
                        </Link>
                    </h1>
                    <nav>
                        <ul>
                            <li><Link to="/login">로그인</Link></li>
                            <li><Link to="/signup">회원가입</Link></li>
                        </ul>
                        <ul>
                        <li><Link to="/category1">Category1</Link></li>
                        <li><Link to="/category1">Category2</Link></li>
                        <li><Link to="/category1">Category3</Link></li>
                        <li><Link to="/category1">Category4</Link></li>
                        </ul>
                    </nav>
                </div>
            </header>
        )
    }

}