import { useEffect, useState } from 'react';
import CategoryItem from '../components/CategoryItem';
import './Cate.css';
export default function Categorylist(){
    const Authorization = localStorage.getItem('Authorization');
    const [category,setCategory] = useState([]);
    const [categoryName,setCategoryName] = useState('');
    useEffect(()=>{
        fetch('http://localhost:8080/admin/allCategory',{
            method:"GET",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            },
        })
        .then((res)=>{
            return res.json();
        })
        .then((res)=>{
            console.log(res)
            setCategory(res);
            console.log(category);
        })
    },[])
    function registercategory(e){
        e.preventDefault();
        fetch('http://localhost:8080/admin/register/category',{
            method:"POST",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            },
            body:JSON.stringify({
                categoryName:categoryName,
            }),
        })
        .then((res)=>{
            return res.json();
        })
        .then((res)=>{
            console.log(res);
        })
    }
        return(
            <div id='admincate'>
                {category.map(category=>(
                    <CategoryItem key={category.categoryId} category={category} />
                ))}
            <div>  
                <button onClick={registercategory} >카테고리 등록</button>
                </div>
                <form id='category-form'>
            <input
              type='text'
              name='supplierName'
              onChange={(e)=>setCategoryName(e.target.value)}
              placeholder='카테고리명'
            ></input>
            <button onClick={registercategory}>등록</button>
          </form>
        </div>    
    )
}