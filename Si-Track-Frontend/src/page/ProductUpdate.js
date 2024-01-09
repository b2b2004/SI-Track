import './ProductUpdate.css';
import { useParams } from "react-router-dom";
import { useEffect, useState } from 'react';

export default function ProductUpdate(){
    const Authorization = localStorage.getItem("Authorization");
    const {id} = useParams();
    const [productUpdate, setProductUpdate] = useState({
        categoryName: '',
        supplierCode: '',
        productName: '',
        productDetail:'',
    });
    const [productImages,setProductImages] = useState([]);
    const [productImagesUpdate, setProductImagesUpdate] = useState([]);

    useEffect(() => {

        /**
         * 상품 수정창 정보
         **/
        fetch(`http://localhost:8080/product/admin/update/${id}`,{
            method: "GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            }
        })
            .then((res) =>{
                return res.json();
            })
            .then((res) =>{
                setProductUpdate({
                    categoryName: res.categoryName,
                    supplierCode: res.supplierCode,
                    productName: res.productName,
                    productDetail: res.productDetail,
                });
                setProductImages(res.productImageUrl);
                console.log(res.productImageUrl);
            })

        /**
         * 선택할수 있는 category 정보
         **/
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

        /**
         * 선택할수 있는 supplier 정보
         **/
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
    }, []);

    /**
     * 수정 시
     */
    function submitUpdate(e){
        e.preventDefault();

        const formData = new FormData();
        for(let i = 0; i < productImagesUpdate.length; i++) {
            formData.append("productImages", productImagesUpdate[i]);
        }
        formData.append("productUpdateRequest", new Blob([JSON.stringify(productUpdate)], { type: "application/json" }));

        fetch(`http://localhost:8080/product/admin/update/${id}`, {
            method: 'POST',
            body: formData,
            headers: {
                'Authorization': Authorization,
            }}).then((res)=>{
                return res.text();
        }).then((res)=>{
            if(res == "상품 업데이트 완료"){
                alert("수정 완료");
                window.location.href = "/";
            }
        })
    }

    const saveImg = (e) =>{
        const nowImageList = [...productImagesUpdate];
        const file = e.target.files[0];
        nowImageList.push(file);
        setProductImagesUpdate(nowImageList);
    }

    const changeValue = (e) => {
        setProductUpdate({
            ...productUpdate,
            [e.target.name]: e.target.value,
        });
    };
    return(
        <div className='register-container'>
            <form onSubmit={submitUpdate}>
                <fieldset>
                    <ul>
                        <li><label htmlFor="productName">상품명</label>
                            <input type="text"
                                   name="productName"
                                   id="productName"
                                   defaultValue={productUpdate.productName}
                                   onChange={changeValue}
                                   placeholder='상품명을 입력해주세요.'/></li>

                        {
                            /**
                             * TODO
                             * supplier
                             * category
                             * 셀렉으로 변경
                             * **/
                        }
                        <li><label htmlFor="categoryName">카테고리명</label>
                            <input type="text"
                                   name="categoryName"
                                   id="categoryName"
                                   defaultValue={productUpdate.categoryName}
                                   onChange={changeValue}
                                   placeholder='카테고리명을 입력해주세요.'/></li>
                        <li><label htmlFor="supplierCode">코드번호</label>
                            <input type="text"
                                   name="supplierCode"
                                   id="supplierCode"
                                   defaultValue={productUpdate.supplierCode}
                                   onChange={changeValue}
                                   placeholder='코드번호를 입력해주세요.'/></li>
                        <li><label htmlFor='productDetail'>상품설명</label>
                            <textarea name="productDetail"
                                      id="productDetail"
                                      defaultValue={productUpdate.productDetail}
                                      onChange={changeValue}
                                      cols="30"
                                      rows="10"
                                      placeholder='상품설명을 입력해주세요.'></textarea></li>
                        <li><label htmlFor='p_img'>상품이미지
                            <input accept='image/*' id='productImages' multiple="multiple" type='file' onChange={saveImg} /></label>

                            {
                                /**
                                 * TODO:
                                 * 이미지처리 필요
                                 * **/
                            }
                            <ul>
                                {productImages.map(image => (
                                    <li key={image.id}><img src={image} alt="" /></li>
                                ))}
                            </ul>

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
                    <p><input type="submit" value="수정"/></p>
                    <p><input type="submit" value="취소"/></p>
                </fieldset>
            </form>
        </div>
    )
}