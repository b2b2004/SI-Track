import { Link } from "react-router-dom";
import './Mypage.css'
import img from '../assets/no01.png'
import { useEffect, useState } from "react";
export default function Mypage(){
    const Authorization = localStorage.getItem('Authorization');
    const [info , setInfo] = useState([]);
    // const [order , setOrder] = useState([]);
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
            console.log(res)
        })


    },[])
    return(
        <div className="mypagecontainer">
            <div className="group">
            <h1>{info.userName}님의 주문내역</h1>
            <button><Link to='/change'>회원정보 변경</Link></button>
            </div>
        <ul>
            <li>
                <figure>
                    <img src={img} alt="상품이미지"></img>
                </figure>
                <dl>
                    <dt>상품명</dt>
                    <dd>상품설명</dd>
                </dl>
                <dl>
                    <dt>주문가격</dt>
                    <dd>7000원</dd>
                </dl>
                <dl>
                    <dt>주문번호</dt>
                    <dd>91616080</dd>
                </dl>
                <dl>
                    <dt>주문일자</dt>
                    <dd>2023.10.10</dd>
                </dl>
                <dl>
                    <dt>주문상태</dt>
                    <dd>배송중</dd>
                </dl>
            </li>
            
        </ul>
        <ul>
            <li>
                <figure>
                    <img src={img} alt="상품이미지"></img>
                </figure>
                <dl>
                    <dt>상품명</dt>
                    <dd>상품설명</dd>
                </dl>
                <dl>
                    <dt>주문가격</dt>
                    <dd>7000원</dd>
                </dl>
                <dl>
                    <dt>주문번호</dt>
                    <dd>91616080</dd>
                </dl>
                <dl>
                    <dt>주문일자</dt>
                    <dd>2023.10.10</dd>
                </dl>
                <dl>
                    <dt>주문상태</dt>
                    <dd>배송중</dd>
                </dl>
            </li>
            
        </ul>
        </div>
    )
}