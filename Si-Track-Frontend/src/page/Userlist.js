import { useEffect, useState } from 'react';
import './Userlist.css';
export default function Userlist(){
    const Authorization = localStorage.getItem('Authorization');
    const [user,setUser] = useState([]);
    // const {userId,userEmail,userPhoneNumber} = user;
    useEffect(()=>{
        fetch('http://localhost:8080/admin/allUser',{
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
            setUser(res.users.content);
            console.log(user);
        })
    },[])


    return(
        <div className='user-list'>
            { user && user.map((user)=>(
                <div key={user.userId}>
                    <div>{user.userId}</div>
                    </div>
            ))}
        </div>    
    )
}