import { AiOutlineClose } from 'react-icons/ai'
import { BiCart } from "react-icons/bi";
import { Link } from 'react-router-dom';
import './Modal.css'

export default function Modal() {
    
    return (
        <div className="modalwrap">
            <div className="modal">
                <h2>장바구니 담기</h2>
                <p><AiOutlineClose size='30px' onClick={()=>document.querySelector('.modalwrap').style.display='none'}/></p>
                <figure>
                <BiCart size='100px' />
                    <figcaption>
                        상품이 장바구니에 담겼습니다.<br></br>
                        바로 확인하시겠습니까?
                    </figcaption>
                    <button onClick={()=>document.querySelector('.modalwrap').style.display='none'}>취소</button>
                    <button><Link to='/cart'>확인</Link></button>
                </figure>
            </div>
        </div>
    )
}