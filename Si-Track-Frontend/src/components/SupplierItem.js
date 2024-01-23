import { useEffect, useState } from "react";

export default function SupplierItem(props){

    const [supplierItem, setSupplierItem] = useState({
        supplierId: "",
        supplierName: "",
        supplierCode: "",
    });
    const [disabled, setdisabled] = useState(true);
    const [button, setButton] = useState(true);
    const Authorization = localStorage.getItem('Authorization');
    useEffect(()=>{
        console.log(props);
        setSupplierItem(props.supplier);
    })

    function modifysupplier(e){
        e.preventDefault();
        console.log(supplierItem);
          fetch('http://localhost:8080/admin/update/supplier', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json; charset=utf-8',
              Authorization,
            },
            body: JSON.stringify(supplierItem),
          })
            .then((res) => res.text())
            .then((res) => {
                if(res == "공급업체 정보 수정 완료"){
                    alert("공급업체 수정 완료");
                    setdisabled(true);
                    setButton(true);
                }
              console.log(res);
            })
}

function deletesupplier(){
  fetch(`http://localhost:8080/admin/delete/supplier/${supplierItem.supplierId}`,{
    method:'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=utf-8',
      Authorization,
    },
    body: JSON.stringify(supplierItem.supplierId),
  })
  .then((res)=>res.text())
  .then((res)=>{
    console.log(res);
  })
}

    const changeValue = (e) => {
        setSupplierItem({
            ...supplierItem,
            [e.target.name]: e.target.value,
        });
    };

    return (
              <div className="admin-panel">
                <h2 className="subtitle">공급업체 정보</h2>
                <ul>
                  <li>
                    <label htmlFor='supplierCode'>공급업체 코드</label>
                    <input
                      name='supplierCode'
                      id='supplierCode'
                      onChange={changeValue}
                      disabled={
                        disabled ? true : false
                      }
                      defaultValue={supplierItem.supplierCode}
                      placeholder='공급업체 코드'
                    ></input>
                  </li>
                  <li>
                    <label htmlFor='supplierName'>공급업체명</label>
                    <input
                      name='supplierName'
                      id='supplierName'
                      onChange={changeValue}
                      disabled={
                        disabled ? disabled : ''
                      }
                      defaultValue={supplierItem.supplierName}
                      placeholder='공급업체 코드'
                    ></input>
                  </li>
                  <li>
                    <label htmlFor='supplierId'>공급업체 Id</label>
                    <input
                      name='supplierId'
                      disabled
                      defaultValue={supplierItem.supplierId}
                    ></input>
                  </li>
<li className="button-container">
    {button ? 
        <button onClick={()=>{
        setButton(false);
        setdisabled(false);
        }}>공급업체 수정</button>
     : 
        <button className="save-button" onClick={modifysupplier}>수정완료</button>}
        <button className="delete-button" onClick={deletesupplier} >삭제</button>
        </li>
                </ul>
              </div>
    );

}