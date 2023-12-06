import ProductItem from "../components/ProductItem";
import './Productlist.css';
import { useEffect, useState } from "react";

export default function Productlist(){
    const [posts, setPosts] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/product/list",{
            method:"GET",
            headers:{
                "content-Type":"application/json; charset=utf-8"
            },
        })
        .then(data=> {
            return data.json();
        })
        .then((data) => {
            setPosts(data.products.content);
            console.log(data.products.content);
            console.log(posts);
        });
    }, []);
    return(
        <div id="product">
            {console.log(posts)}
        <h1>title</h1>
        <form>
        <input type="text" placeholder="검색어를 입력해주세요"></input>
        <button type="submit">검색</button>
        </form>
            <div className="container">
            <p>총 
                개가 검색되었습니다.</p>
            <div>
                {posts.map(post => (
                    <ProductItem post={post} key={post.supplierCode}/>
                ))}
            </div>
            </div>
        </div>
    )
}