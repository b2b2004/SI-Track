import { useEffect, useState } from 'react';
export default function Supplierlist(){
    const Authorization = localStorage.getItem('Authorization');
    const [supplier,setSupplier] = useState([]);
    useEffect(()=>{
        fetch('http://localhost:8080/admin/allSupplier',{
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
            setSupplier(res.suppliers.content);
            console.log(supplier);
        })
    },[])
        return(
            <div>
            { supplier && supplier.map((supplier)=>(
                <div key={supplier.supplierId}>
                    <ul>
                        <li>공급업체 코드: {supplier.supplierCode} 공급업체명: {supplier.supplierName} 공급업체 id: {supplier.supplierId} <button>공급업체 수정</button></li>
                    </ul>
                    </div>
            ))}
            <button>공급업체 등록</button>
        </div>    
    )
}