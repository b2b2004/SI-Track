import './Header.css';
import logo from '../assets/logo.png'
import { Link } from 'react-router-dom';

export default function Header(){
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