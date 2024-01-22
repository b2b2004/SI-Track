import './Pay.css';
import { useEffect, useState } from 'react';
import Post from './Post';
import { Link } from 'react-router-dom';
import img from '../assets/no01.png';
import { useRecoilValue } from 'recoil';
import { cartItemsState } from '../recoil/cartItemsState';

export default function Pay(){
    const Authorization = localStorage.getItem("Authorization");
    const [addressObj,setAddressObj] = useState('');
    const [locationObj,setLocationObj] = useState('');
    const address = addressObj.townAddress;
    const postcode = addressObj.postcode;
    const [orderItems, setOrderItems] = useState([]);
    // recoil 활용 값 받아옴
    const selectedProducts = useRecoilValue(cartItemsState);
    const [orderRequest, setOrderRequest] = useState({
        totalAmount: '', // 총 가격
        orderAddress: '', // 주소
        orderRq: '', // 요구 사항
        recipient: '', // 수령인
        phoneNumber: '', // 수령인 핸드폰 번호
    })
    const [detailaddress, setDetailaddress] = useState('');
    const [orderItemDto, setOrderItemDto] = useState([]);

    useEffect(()=>{
        setOrderItems(selectedProducts);
        orderRequest.totalAmount = selectedProducts.reduce((acc, item) => acc + item.productPrice * item.amount, 0);
        const newArray = selectedProducts.map(item => ({
                price: item.productPrice,
                productId: item.productId,
                quantity: item.amount
            }));
        orderRequest.orderItemDtos = newArray;
    },[])

    const changeValue = (e) => {
        setOrderRequest({
            ...orderRequest,
            [e.target.name]: e.target.value,
        });
    };

    const changeAddress = (e) => {
        setDetailaddress(e.target.value);
    };

    function orderRegist(e){
        e.preventDefault();
        orderRequest.orderAddress = addressObj.areaAddress + addressObj.townAddress + ' ' + detailaddress;
        console.log(orderRequest);
        console.log(orderItemDto);


        fetch("http://localhost:8080/order/page", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8', Authorization
                },
                body: JSON.stringify(orderRequest),
            }
        ).then((res)=>{
                if(res.code === "OUT_OF_STOCK"){
                    alert("상품 재고가 부족합니다. 관리자에게 문의해주세요.")
                    window.location.href = "/cart"
                }
                if(res.status === 200){
                    alert("주문 완료")
                    window.location.href = "/"
                }else{
                    alert("주문 실패. 주문 정보 확인")
                }
            });

    }


    return(


        <div className='paycontainer'>
    <form>
    <ul>
        <li><label>주문고객:</label> 권용호(010-1234-5678-0000)</li>
        <li>
            <ul>
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'none';
                    document.querySelector('.direct').style.display = 'block';
                    document.querySelector('.database').style.display = 'none';
                }}>직접수령</li>
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'none';
                    document.querySelector('.direct').style.display = 'none';
                    document.querySelector('.database').style.display = 'block';
                }}>기본배송지</li>
            {/* 클릭시 데이터베이스에서 사용자가 입력한 주소를 가져옴 */}
                <li onClick={()=>{
                    document.querySelector('.daumapi').style.display = 'block';
                    document.querySelector('.direct').style.display = 'none';
                    document.querySelector('.database').style.display = 'none';
                }}>배송지 입력</li>
                </ul>
                </li>
                <li className='daumapi'>
                <Post setAddressObj={setAddressObj} setLocationObj={setLocationObj} />  
                <ul>
                    <li><input type='text' defaultValue={postcode} placeholder='우편번호'></input></li>
                    <li><input type='text' defaultValue={address} placeholder='주소'></input></li>
                    <li><input type='text' name="orderAddress" id="orderAddress" onChange={changeAddress} placeholder='상세주소'></input></li>
                    <li><input type='text' name="orderRq" id="orderRq" onChange={changeValue} placeholder='요청사항'></input></li>
                    <li><input type='text' name="recipient" id="recipient" onChange={changeValue} placeholder='수령인'></input></li>
                    <li><input type='text' name="phoneNumber" id="phoneNumber" onChange={changeValue} placeholder='수려인핸드폰번호'></input></li>
                </ul>
        </li>
        <li className='direct'>
            직접수령
        </li>
        <li className='database'>
            데이터베이스에 입력된 사용자 주소
        </li>
        <li>

            {orderItems.map((item) => (
        <ul>
            <li>
                <figure>
                    <img src={item.productImagesUrl} alt="상품이미지"></img>
                </figure>
                <dl>
                    <dt>상품명</dt>
                    <dd>{item.productName}</dd>
                </dl>
                <dl>
                    <dt>상품설명</dt>
                    <dd>{item.productDetail}</dd>
                </dl>
                <dl>
                    <dt>주문수량</dt>
                    <dd>{item.amount}</dd>
                </dl>
                <dl>
                    <dt>주문가격</dt>
                    <dd>{item.productPrice * item.amount}</dd>
                </dl>
            </li>
        </ul>
            ))}
            
            <div>
                <a>총가격 : {orderRequest.totalAmount}</a>
            </div>
                    
        </li>
        <p><input defaultValue="결제" onClick={orderRegist}/></p>
        <p><input defaultValue="취소"/></p>
    </ul>
</form>
</div>
);
              
}