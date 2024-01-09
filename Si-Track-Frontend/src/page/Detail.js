import './Detail.css';
import img from '../assets/no01.png';
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from 'react';

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

            {/**
             TODO:
             수정 / 삭제는 권한이 있을때만 보이게 해야함.
             **/}
            <Link to={`/product/update/${products.productId}`}>
            <button>수정</button>
            </Link>
            <button onClick={submitDelete}>삭제</button>
            <figure>
                <img src={productImages[0]} alt="상품이미지"></img>
                <dl>
                    <dt>{products.productName}</dt>
                    <dd>{products.supplierCode}</dd>
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
                    {
                        /**
                         * TODO:
                         * 이미지 처리
                         * **/
                    }
                <figure id='detailimg'>

                    <ul>
                        {productImages.map(image => (
                            <li key={image.id}><img src={image} alt="" /></li>
                        ))}
                    </ul>

                </figure>
                <p id='productinfo'>제품안내<br></br> {products.productDetail}</p>
                <p id='buyinfo'>결제안내<br></br> Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</p>
                <p id='deliveryinfo'>배송 및 교환환불 설명<br></br> It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).</p>
                </section>
            </div>
        </div>
    )
}