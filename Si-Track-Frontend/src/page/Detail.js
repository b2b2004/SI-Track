import './Detail.css';
import img from '../assets/no01.png';
import detailimg1 from '../assets/no10.png';
import detailimg2 from '../assets/no11.png';
import detailimg3 from '../assets/no12.png';
import { Link } from "react-router-dom";
import { useEffect, useState, useParams } from "react";
export default function Detail(){
    const Authorization = localStorage.getItem("Authorization");
    const {id} = useParams();
    const [products, setProducts] = useState({
        productId: '',
        supplierCode: '',
        productName: '',
        productCost: '',
        productDetail: '',
    });
    const [productImages,setProductImages] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/product/${id}`,{
            method:"GET",
            headers:{
                "content-Type":"application/json; charset=utf-8"
            },
        })
        .then(data=> {
            return data.json();
        })
        .then((data) => {
            setProducts(data);
            setProductImages(data.productImagesUrl);
        });
    }, []);

    function submitDelete(e){
        e.preventDefault();
        if(!window.confirm("정말 삭제할까요?")){
            return;
        }
            fetch(`http://localhost:8080/product/admin/${id}`,{
                method: "DELETE",
                headers:{
                    "content-Type":"application/json; charset=utf-8", Authorization
                }
            })
                .then((res) =>{
                    return res.text();
                })
                .then((res) =>{
                    if(res == "상품 삭제 완료"){
                        alert("삭제 완료");
                        window.location.href = "/";
                    }
                })
    }
    return(
        <div className="detailcontainer">
            <figure>
                <img src={img} alt="상품이미지"></img>
                <dl>
                    <dt>상품명</dt>
                    <dd>코드번호</dd>
                    <form action="post">
                       <label>구매수량
                       <input type="text"/></label>
                       <label>구매단가
                       <input type="text"disabled  defaultValue={products.productCost}/>
                       </label>
                       <label>구매금액
                       <input type="text" disabled />
                       </label>
                       <label>총금액
                       <input type="text" disabled/>
                       </label>
                    </form>
                    <button type="submit"><Link to='/pay'>결제하기</Link></button>
                    <button type="submit"><Link to='/cart'>장바구니</Link></button>
                    <button>견적서출력</button>
                </dl>
            </figure>
            <div>
                <button className="btn1"><a href='#detailimg'>상품상세정보</a></button>
                <button className="btn2"><a href='#productinfo'>제품안내</a></button>
                <button className="btn3"><a href='#buyinfo'>결제안내</a></button>
                <button className="btn4"><a href='#deliveryinfo'>배송 및 교환환불</a></button>
                <section>
                <figure id='detailimg'>
                    <img src={detailimg1} alt="상세이미지1"></img>
                    <img src={detailimg2} alt="상세이미지2"></img>
                    <img src={detailimg3} alt="상세이미지3"></img>
                </figure>
                <p id='productinfo'>제품안내</p>
                <p id='buyinfo'>결제안내</p>
                <p id='deliveryinfo'>배송 및 교환환불 설명</p>
                </section>
            </div>
        </div>
    )
}