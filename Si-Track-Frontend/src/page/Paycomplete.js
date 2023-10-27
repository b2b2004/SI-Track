import {BsFillClipboardCheckFill} from 'react-icons/bs';
import { Link } from 'react-router-dom';
import './Paycomplete.css';
export default function Paycomplete(){
    return(
        <div className="completecotainer">
            <h1>결제완료</h1>
            <figure><BsFillClipboardCheckFill size='100px'/></figure>
            결제가 완료되었습니다.
            <Link to='/'><input type="submit" value="메인으로"></input></Link>
            </div>
    )
}