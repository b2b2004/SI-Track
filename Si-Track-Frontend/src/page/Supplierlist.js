import { useEffect, useState } from 'react';
import SupplierItem from '../components/SupplierItem';
import './Supplier.css';
export default function Supplierlist(){
    const Authorization = localStorage.getItem('Authorization');
    const [supplier,setSupplier] = useState([]);
    const [supplierDto, setSupplierDto] = useState({
        supplierCode: '',
        supplierName: ''
    });
    const [supplierId,setSupplierId] = useState('');
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


    function registersupplier(e){

        e.preventDefault();
        fetch('http://localhost:8080/admin/register/supplier',{
            method:"POST",
            headers:{
                "content-Type":"application/json; charset=utf-8", Authorization
            },
            body: JSON.stringify(
                supplierDto
            ) 
        })
        .then((res)=>{
            return res.json();
        })
        .then((res)=>{
            console.log(res);
            alert('등록이 완료되었습니다.')
        })
    }

    function change(e) {
        const { name, value } = e.target;

        console.log(e.defaultValue);
    
        setSupplierDto({ ...supplierDto, [name]: value });
    
        if (name === 'supplierCode' || name === 'supplierName') {
          setSupplierId(value);
        } else if (name === 'supplierId') {
          setSupplierId(value);
        }
    
        console.log(supplierDto);
        console.log(supplierId);
      }

    return (
        <div id='admin'>

<div className='supplier-list'>
{supplier.map(supplier =>(
  <SupplierItem key={supplier.supplierId} supplier={supplier} />
  ))}
  </div>
          <button
            onClick={() => (document.querySelector('#supplierregister').style.display = 'block')}
          >
            공급업체 등록
          </button>
          <form id='supplierregister'>
            <input
              type='text'
              name='supplierCode'
              onChange={change}
              placeholder='공급업체 코드'
            ></input>
            <input
              type='text'
              name='supplierName'
              onChange={change}
              placeholder='공급업체명'
            ></input>
            <button onClick={registersupplier}>등록</button>
          </form>
        </div>
      );
    }