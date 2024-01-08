import { useEffect, useState } from 'react';
export default function Orderlist(){
    const Authorization = localStorage.getItem('Authorization');
    const [order,setOrder] = useState([]);
    useEffect(()=>{
        fetch('http://localhost:8080/admin/allOrder',{
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
            setOrder(res.orders.content);
            console.log(order);
        })
    },[])

    if(order===''){
        return(
            <div>주문이 존재하지 않습니다.</div>
            )
    }
    else if(order!==null){
        return(
            <div>
            { order && order.map((order)=>(
                <div key={order.orderId}>
                    <div>{order.orderId}</div>
                    </div>
            ))}
        </div>    
    )
}
}