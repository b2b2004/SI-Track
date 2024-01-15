import { useEffect, useState } from 'react';
import './Register.css';
export default function Register(){
    const Authorization = localStorage.getItem("Authorization");
    const [productRequest, setProductRequest] = useState({
        categoryName: '',
        supplierCode: '',
        productName: '',
        productCost: '',
        productPrice: '',
        productDetail:'',
        productStockQuantity: '',
        productSalesQuantity: ''
    });
    const [productImages,setProductImages] = useState([]);


    useEffect(()=>{

        fetch("http://localhost:8080/admin/allCategory",{
            method: "GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            }
        })
            .then((res) =>{
                return res.json();
            })
            .then((res) =>{
                console.log(res);
            })

        fetch("http://localhost:8080/admin/allSupplier",{
            method: "GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            }
        })
            .then((res) =>{
                return res.json();
            })
            .then((res) =>{
                console.log(res);
            })

    },[])

    function productRegist(e){
        e.preventDefault();

        const formData = new FormData();
        for(let i = 0; i < productImages.length; i++) {
            formData.append("productImages", productImages[i]);
        }

         formData.append("productRequest", new Blob([JSON.stringify(productRequest)], { type: "application/json" }));

        // for (let productImages of formData.keys()) {
        //     console.log(productImages, ":", formData.get(productImages));
        // }
        //
        // for (let productRequest of formData.keys()) {
        //     console.log(productRequest, ":", formData.get(productRequest));
        // }

        fetch('http://localhost:8080/product/admin/register', {
            method: 'POST',
            body: formData,
            headers: {
                // 'Content-Type': 'multipart/form-data',
                'Authorization': Authorization,
            }}).then((res)=>{
                return res.text();
        }).then((res)=>{
            if(res == "상품 등록 완료"){
                alert("등록 완료");
                window.location.href = "/";
            }
        })

    }
    const saveImg = (e) =>{
        const nowImageList = [...productImages];
        const file = e.target.files[0];
        nowImageList.push(file);
        setProductImages(nowImageList);
    }

    const changeValue = (e) => {
        setProductRequest({
            ...productRequest,
            [e.target.name]: e.target.value,
        });
    };

    return(
        <div className='register-container'>
            <form onSubmit={productRegist}>
                <fieldset>
                    <ul>
                        <li><label htmlFor="productName">상품명</label>
                            <input type="text" name="productName" id="productName" onChange={changeValue} placeholder='상품명을 입력해주세요.'/></li>
                        {
                            /**
                             * TODO
                             * supplier
                             * category
                             * 셀렉으로 변경
                             * **/
                        }
                        <li><label htmlFor="categoryName">카테고리명</label>
                            <input type="text" name="categoryName" id="categoryName" onChange={changeValue} placeholder='카테고리명을 입력해주세요.'/></li>
                        <li><label htmlFor="supplierCode">코드번호</label>
                            <input type="text" name="supplierCode" id="supplierCode" onChange={changeValue} placeholder='코드번호를 입력해주세요.'/></li>
                        <li><label htmlFor="productStockQuantity">재고량</label>
                            <input type="text" name="productStockQuantity" onChange={changeValue} id="productStockQuantity" placeholder='재고량을 입력해주세요'/></li>
                        <li><label htmlFor="productSalesQuantity">판매량</label>
                            <input type="text" name="productSalesQuantity" onChange={changeValue} id="productSalesQuantity" placeholder='판매량을 입력해주세요'/></li>
                        <li><label htmlFor="productCost">구매가</label>
                            <input type="text" name="productCost" id="productCost" onChange={changeValue} placeholder='구매가를 입력해주세요.'/></li>
                        <li><label htmlFor="productPrice">판매가</label>
                            <input type='text' name='productPrice' id='productPrice' onChange={changeValue} placeholder='판매가를 입력해주세요.'/></li>
                        <li><label htmlFor='productDetail'>상품설명</label>
                            <textarea name="productDetail" id="productDetail" onChange={changeValue} cols="30" rows="10" placeholder='상품설명을 입력해주세요.'></textarea></li>
                        <li><label htmlFor='p_img'>상품이미지
                            <input accept='image/*' id='productImages' multiple="multiple" type='file' onChange={saveImg} /></label>

                            {
                                /**
                                 * TODO:
                                 * 이미지 처리
                                 * **/
                            }
                            
                            {/*<ul className='imgcont'>*/}
                            {/*    {productImages.map((image, id) => (*/}
                            {/*        <li>*/}
                            {/*            <img src={image} alt='이미지'key={id} />*/}
                            {/*            <button onClick={(id)=>{*/}
                            {/*                setProductImages(productImages.filter((_,index)=>index !== id));*/}
                            {/*            }}>삭제</button>*/}
                            {/*        </li>*/}
                            {/*    ))}*/}
                            {/*</ul>*/}
                        </li>
                    </ul>
                    <p><input type="submit" value="등록"/></p>
                    <p><input type="submit" value="취소"/></p>
                </fieldset>
            </form>
        </div>
    )
}