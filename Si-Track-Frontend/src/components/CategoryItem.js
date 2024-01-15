import { useEffect, useState } from "react"

export default function CategoryItem(category){
    
const[categoryItem,setCategoryItem] = useState({
    categoryId:"",
    categoryName:"",
});
const [disabled, setdisabled] = useState(true);
const [button, setButton] = useState(true);
const Authorization = localStorage.getItem('Authorization');
useEffect(()=>{
    console.log(category);
    setCategoryItem(category.category);
})

function modifycategory(e){
    e.preventDefault();
    console.log(categoryItem);
      fetch('http://localhost:8080/admin/update/category', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          Authorization,
        },
        body: JSON.stringify(categoryItem),
      })
        .then((res) => res.text())
        .then((res) => {
            if(res == "카테고리 정보 수정 완료"){
                alert("카테고리 수정 완료");
                setdisabled(true);
                setButton(true);
            }
          console.log(res);
        })
}
    return(
        <div className="category-item">
                <div key={category.categoryId}>
                    <div>카테고리명 
                    <input type='text' name="categoryName" onChange= {(e) => {
                        setCategoryItem({...categoryItem,[e.target.name]:e.target.value,}
                        )}} disabled={
                            disabled?true:false
                        } defaultValue={categoryItem.categoryName}></input>
                        <div className="button-container">
     {button ? 
        <button onClick={()=>{
        setButton(false);
        setdisabled(false);
        }}>카테고리 수정</button>
     : 
        <button onClick={modifycategory}>수정완료</button>}
        </div>
                    </div>
                    </div>
        </div>
    )
}