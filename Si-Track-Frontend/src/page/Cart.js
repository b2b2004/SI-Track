import './Cart.css';
import img from '../assets/no01.png';
import { Link } from 'react-router-dom';
export default function Cart(){
    return(
        <div className="cartwrap">
        <h2>장바구니</h2>
        {/* <div className='none'>장바구니가 비어있습니다.</div> */}
        <div className="cart">      
            <ul>
                    <li >
                        <figure>
                        <img src={img} alt='img'/>
                        <figcaption>
                            <dl>
                                <dt>
                                    상품명
                                </dt>
                                <dd>
                                   상품상세설명  
                                </dd>
                                <dd>
                                    (상품가격) 원
                                </dd>
                            </dl>
                        </figcaption>
                        <p>
                        <button>삭제</button>
                    </p>
                    </figure>
                    </li>
            </ul>
            <p><button>전체삭제</button></p>
            <div>
                <Link to='/pay'>
                <button>주문하기</button>
                </Link>
            </div>
            </div>
       </div>
    )
}