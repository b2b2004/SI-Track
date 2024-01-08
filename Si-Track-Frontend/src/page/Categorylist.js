import { useEffect, useState } from 'react';
export default function Categorylist(){
    const Authorization = localStorage.getItem('Authorization');
    const [categorylist,setCategorylist] = useState([]);
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
            setCategorylist(res);
            console.log(categorylist);
        })
    },[])
        return(
            <div>
            { categorylist && categorylist.map((categorylist)=>(
                <div key={categorylist.categoryId}>
                    <div>카테고리명 : {categorylist.categoryName}
                    <button>카테고리 수정</button>
                    </div>
                    </div>
            ))}
            <div><button>카테고리 등록</button></div>
        </div>    
    )
}