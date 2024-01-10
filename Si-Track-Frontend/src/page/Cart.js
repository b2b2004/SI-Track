import './Cart.css';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { cartItemsState } from '../recoil/cartItemsState';

export default function Cart(){
    const Authorization = localStorage.getItem("Authorization");
    const [cartItems, setCartItems] = useState([]);
    const [selectedItems, setSelectedItems] = useRecoilState(cartItemsState);
    const selectedProducts = useRecoilValue(cartItemsState);

    useEffect(()=>{
        /**
         * 카트 리스트 받아옴
         * **/
        fetch("http://localhost:8080/cart/list",{
            method: "GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            }
        })
            .then((res) =>{
                return res.json();
            })
            .then((res) =>{
                setCartItems(res);
            })

    },[])

    // 체크박스 변경 핸들러
    const handleCheckboxChange = (cartItemId) => {
        setCartItems(prevItems =>
            prevItems.map(item =>
                item.cartItemId === cartItemId ? { ...item, checked: !item.checked } : item
            )
        );
    };

    // 선택된 상품들을 배열로 추출
    const getSelectedProducts = () => {
        return cartItems.filter(item => item.checked);
    };

    function orderPage(e){
        e.preventDefault();
        const selectedProducts = getSelectedProducts();
        setSelectedItems(selectedProducts);
        window.location.href = "/pay";
    }

    function cartItemDelete(productId){
        console.log(productId);
        if(!window.confirm("해당 상품을 장바구니에서 삭제하시겠습니까?")){
            return;
        }
            fetch(`http://localhost:8080/cart/${productId}`,{
                method: "DELETE",
                headers:{
                    "content-Type":"application/json; charset=utf-8", Authorization
                }
            })
                .then((res) =>{
                    if(res.status == 200){
                        alert("해당 장바구니 상품 삭제 완료");
                        window.location.reload();
                    }
                })
    }

    return(
        /**
         * TODO:
         * 1. 수량 변할 수 있게 해야됨
         * 2. 장바구니 총 가격 보이게
         * **/

        <div className="cartwrap">
        <h2>장바구니</h2>
            {cartItems.length === 0 ? (<div className='none'>장바구니가 비어있습니다.</div>) : (<div></div>)}
        <div className="cart">
            <ul>
                    {cartItems.map((item) => (
                    <li key={item.cartItemId}>
                        <figure>
                            <input
                                type="checkbox"
                                checked={item.checked}
                                onChange={() => handleCheckboxChange(item.cartItemId)}
                            />
                        <img src={item.productImagesUrl} alt='img'/>
                        <figcaption>
                            <dl>
                                {item.amount}
                                <dt>
                                    {item.productName}
                                </dt>
                                <dd>
                                    {item.productDetail}
                                </dd>
                                <dd>
                                    {item.productPrice}원
                                </dd>
                            </dl>
                        </figcaption>
                        <p>
                        <button onClick={()=>{cartItemDelete(item.cartItemId)}} >삭제</button>
                    </p>
                    </figure>
                    </li>
                    ))}
            </ul>
            <p><button>전체삭제</button></p>
            <div>
                <Link to='/pay'>
                <button onClick={orderPage}>주문하기</button>
                </Link>
            </div>
            </div>
       </div>
    )
}